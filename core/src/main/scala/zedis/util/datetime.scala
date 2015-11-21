package zedis
package util

import scalaz.Reader

import com.github.nscala_time.time.Imports._

trait DateTimeUtils
  extends DateTimeHelper
  with    DateTimeKey

trait DateTimeHelper {

  def now(): DateTime = DateTime.now
}

trait DateTimeKey {

  def createDateTimeKey(format: String): Reader[DateTime, String] =
    Reader{ date => date.toString(format) }

  def createKeyWith_YYYY(prefix: String): Reader[DateTime, String] =
    createDateTimeKey("yyyy") map (prefix + _)

  def createKeyWith_YYYYMM(prefix: String): Reader[DateTime, String] =
    createDateTimeKey("yyyyMM") map (prefix + _)

  def createKeyWith_YYYYMMDD(prefix: String): Reader[DateTime, String] =
    createDateTimeKey("yyyyMMdd") map (prefix + _)

  def createKeyWith_YYYYMMDDHH(prefix: String): Reader[DateTime, String] =
    createDateTimeKey("yyyyMMddHH") map (prefix + _)
}
