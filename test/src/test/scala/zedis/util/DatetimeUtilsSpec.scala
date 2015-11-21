package zedis
package util

import com.github.nscala_time.time.Imports._

class DatetimeUtilsSpec extends ZedisTestSuite {
  import zedis.util.datetime._

  val prefix = "key-"
  val (yyyy, mm, dd, hh) = ("2015", "01", "01", "23")
  val datetime = DateTime.parse(yyyy).month(mm.toInt).day(dd.toInt).hour(hh.toInt)

  describe("DateTimeKey") {

    it("createDateTimeKey") {
      createDateTimeKey("yyyyMMddHH").run(datetime) shouldEqual s"$yyyy$mm$dd$hh"
    }

    it("with YYYY") {
      createKeyWith_YYYY(prefix).run(datetime) shouldEqual s"$prefix$yyyy"
    }

    it("with YYYYMM") {
      createKeyWith_YYYYMM(prefix).run(datetime) shouldEqual s"$prefix$yyyy$mm"
    }

    it("with YYYYMMDD") {
      createKeyWith_YYYYMMDD(prefix).run(datetime) shouldEqual s"$prefix$yyyy$mm$dd"
    }

    it("with YYYYMMDDHH") {
      createKeyWith_YYYYMMDDHH(prefix).run(datetime) shouldEqual s"$prefix$yyyy$mm$dd$hh"
    }
  }
}
