package zedis

import scala.collection.JavaConversions._

import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.slf4j.LoggerFactory

import redis.clients.jedis.{HostAndPort, Jedis, JedisSlotBasedConnectionHandler, Pipeline}
import redis.clients.jedis.exceptions.{JedisException, JedisMovedDataException}
import redis.clients.util.JedisClusterCRC16

abstract class Accessor {

  private[this] lazy val logger = LoggerFactory.getLogger(classOf[Accessor])

 // logging用途
  protected[this] val _tag: String

  protected[this] val _nodes: Set[(String, Int)]

  protected[this] val _config: GenericObjectPoolConfig =
    new GenericObjectPoolConfig() // default

  // milli seconds
  protected[this] val _timeout: Int


  def getNodes: Set[(String, Int)] =
    _nodes

  def getTimeout: Int =
    _timeout


  private[this] lazy val hostAndPorts = _nodes.map { case (host, port) =>
    new HostAndPort(host, port)
  }

  private[this] lazy val handler =
    new JedisSlotBasedConnectionHandler(hostAndPorts, _config, _timeout)

  protected def getConnectionFromSlot(key: String): Jedis = {
    val slot = JedisClusterCRC16.getSlot(key)
    handler.getConnectionFromSlot(slot)
  }

  //
  //
  //
  def using[A](slotKey: String)(f: Jedis => A): A = {
    try {
      val jedis = getConnectionFromSlot(slotKey)
      try {
        f(jedis)
      } finally {
        try {
          jedis.close()
        } catch {
          case e: JedisMovedDataException =>
            // JedisException を握りつぶすようにしているが、その子孫クラスの JedisMovedDataException は外側の catch で拾いたいので投げ直す。
            throw e
          case e: JedisException =>
            // タイムアウト経過後など、JedisException: Could not return the resource to the pool が発生する場合があるが、
            // f で渡された処理は完了しており、コネクションをプールに戻す際に発生したエラーのため無視しても構わないと判断。
            logger.warn("Jedis.close() failed.", e)
        }
      }
    } catch {
      case e: JedisMovedDataException =>
        handler.renewSlotCache()
        throw e
    }
  }

  //
  //
  //
  def pipelined[A](slotKey: String)(f: Pipeline => A): A =
    using(slotKey) { jedis =>
      val pipeline = jedis.pipelined()
      f(pipeline)
    }
}

abstract class StandaloneAccessor extends Accessor {

  protected override def getConnectionFromSlot(slotKey: String): Jedis = {
    val (host, port) = _nodes.head
    new Jedis(host, port, _timeout)
  }
}


object Accessor {
  import scala.concurrent.duration._
  import zedis.config.AccessorConfig

  // Accessor生成時にRedisとのコネクションエラーは拾わない

  def standalone(host: String = "localhost", port: Int = 6379): Accessor =
    new StandaloneAccessor {
      val _tag     = "accessor: standalone"
      val _nodes   = Set((host, port))
      val _timeout = 60 * 1000 // 60秒。適当な値。
    }

  def single(host: String, port: Int, timeout: FiniteDuration = 60.seconds): Accessor =
    new Accessor {
      val _tag     = "accessor: single"
      val _nodes   = Set((host, port))
      val _timeout = timeout.toMillis.toInt
    }

  def fromConfig(): Accessor = {
    new Accessor {
      val _tag     = "accessor: from config file"
      val _nodes   = AccessorConfig.nodes
      val _timeout = AccessorConfig.timeout
    }
  }

}
