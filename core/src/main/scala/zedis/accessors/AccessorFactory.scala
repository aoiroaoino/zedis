package zedis
package accessor

import zedis.config.AccessorConfig

object AccessorFactory {

  // todo: read from config
  private val defaultTimeout = 60 * 1000

  def local(): Accessor =
    standalone("localhost", 6379, defaultTimeout)

  def local(port: Int): Accessor =
    standalone("localhost", port, defaultTimeout)

  def standalone(host: String, port: Int, timeout: Int = defaultTimeout): Accessor =
    new Accessor {
      val _host    = host
      val _port    = port
      val _timeout = timeout
    }

  // read config file
  def standalone(): Accessor = {
    val host    = AccessorConfig.host()
    val port    = AccessorConfig.port()
    val timeout = AccessorConfig.timeout()
    standalone(host, port, timeout)
  }
}
