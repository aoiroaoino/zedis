package zedis
package util

import scalaz.Reader

import com.github.nscala_time.time.Imports._

trait DateTimeKey {

  def now(): DateTime = DateTime.now

  def createDateTimeKey(format: String): Reader[DateTime, String] =
    Reader{ date => date.toString(format) }

  def createKeyWithYYMM(prefix: String): Reader[DateTime, String] =
    createDateTimeKey("yyMM") map (prefix + _)

  def createKeyWithYYMMDD(prefix: String): Reader[DateTime, String] =
    createDateTimeKey("yyMMdd") map (prefix + _)

  def createKeyWithYYMMDDHH(prefix: String): Reader[DateTime, String] =
    createDateTimeKey("yyMMddhh") map (prefix + _)
}
