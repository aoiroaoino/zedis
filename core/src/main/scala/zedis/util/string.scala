package zedis
package util

trait StringUtils
  extends StringHelper

trait StringHelper {

  def toSlotKey(s: String): String = s"{$s}"
}
