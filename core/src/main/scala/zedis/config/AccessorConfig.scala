package zedis
package config

object AccessorConfig extends Config {

  override val tag = "accessor"

  // standalone
  def host(): String = getString("host")
  def port(): Int    = getInt("port")

  // cluster
  def nodes(): Set[(String, Int)] =
    getStringList("nodes").map(_.split(":")).collect{case Array(host, port) => (host, port.toInt)}.toSet

  // common
  def timeout(): Int = getInt("timeout")
}
