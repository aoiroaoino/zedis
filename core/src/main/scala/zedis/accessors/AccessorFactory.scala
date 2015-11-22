package zedis
package accessor

import zedis.config.AccessorConfig

object AccessorFactory {

  // todo: read from config
  private val defaultTimeout = 60 * 1000

  def local(): StandaloneAccessor =
    standalone("localhost", 6379, defaultTimeout)

  def local(port: Int): StandaloneAccessor =
    standalone("localhost", port, defaultTimeout)

  def standalone(host: String, port: Int, timeout: Int = defaultTimeout): StandaloneAccessor =
    new StandaloneAccessor {
      val _host    = host
      val _port    = port
      val _timeout = timeout
    }

  // read config file
  def standalone(): StandaloneAccessor = {
    val host    = AccessorConfig.host()
    val port    = AccessorConfig.port()
    val timeout = AccessorConfig.timeout()
    standalone(host, port, timeout)
  }

  def cluster(nodes: Set[(String, Int)], timeout: Int = defaultTimeout): ClasterAccessor =
    new ClasterAccessor {
      val _nodes   = nodes
      val _timeout = timeout
    }

  // read config file
  def cluster(): ClasterAccessor = {
    val nodes   = AccessorConfig.nodes()
    val timeout = AccessorConfig.timeout()
    cluster(nodes, timeout)
  }

}
