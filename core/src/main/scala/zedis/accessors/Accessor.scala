package zedis
package accessor

abstract class Accessor {

  // milli seconds
  protected[this] val _timeout: Int
}
