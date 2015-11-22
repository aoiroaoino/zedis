package zedis
package accessor

import scala.collection.JavaConversions._

import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import redis.clients.jedis.{HostAndPort, Jedis, JedisSlotBasedConnectionHandler, Pipeline}
import redis.clients.jedis.exceptions.{JedisException, JedisMovedDataException}
import redis.clients.util.JedisClusterCRC16

abstract class ClasterAccessor extends Accessor {

  protected[this] val _nodes: Set[(String, Int)]

  protected[this] val _config: GenericObjectPoolConfig =
    new GenericObjectPoolConfig() // default

  private[this] lazy val hostAndPorts = _nodes.map { case (host, port) =>
    new HostAndPort(host, port)
  }

  private[this] lazy val handler =
    new JedisSlotBasedConnectionHandler(hostAndPorts, _config, _timeout)

  protected def getConnectionFromSlot(key: String): Jedis = {
    val slot = JedisClusterCRC16.getSlot(key)
    handler.getConnectionFromSlot(slot)
  }

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
        }
      }
    } catch {
      case e: JedisMovedDataException =>
        handler.renewSlotCache()
        throw e
    }
  }

  def pipelined[A](slotKey: String)(f: Pipeline => A): A =
    using(slotKey) { jedis =>
      val pipeline = jedis.pipelined()
      f(pipeline)
    }
}
