package zedis
package accessor

import redis.clients.jedis.{HostAndPort, Jedis, JedisSlotBasedConnectionHandler, Pipeline}

abstract class StandaloneAccessor extends Accessor {

  protected[this] val _host: String
  protected[this] val _port: Int

  def using[A](f: Jedis => A): A = {
    val jedis = new Jedis(_host, _port)
    try {
      f(jedis)
    } finally {
      jedis.close()
    }
  }

  def pipelined[A](f: Pipeline => A): A =
    using{ jedis =>
      val pipeline = jedis.pipelined()
      f(pipeline)
    }

  def autoSyncPipelined[A](f: Pipeline => A): A =
    using{ jedis =>
      val pipeline = jedis.pipelined()
      val a = f(pipeline)
      pipeline.sync()
      a
    }
}
