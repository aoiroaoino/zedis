package zedis
package config

object AccessorConfig extends Config {

  override val tag = "accessor"

  def nodes: Set[(String, Int)] =
    getStringList("nodes").map(_.split(":")).collect{case Array(host, port) => (host, port.toInt)}.toSet

  def timeout: Int =
    getInt("timeout")
}
