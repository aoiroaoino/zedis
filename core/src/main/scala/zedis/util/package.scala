package zedis

package object util {

  object all extends AllUtils

  object datetime extends DateTimeUtils

  object string extends StringUtils

  object jedis extends JedisUtils
}
