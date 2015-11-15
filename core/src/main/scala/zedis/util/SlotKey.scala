package zedis
package util

trait SlotKey {

  def toSlotKey(s: String): String = s"{$s}"
}
