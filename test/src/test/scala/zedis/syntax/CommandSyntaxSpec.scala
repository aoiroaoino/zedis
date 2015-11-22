package zedis
package syntax

import scalaz.\/-

import zedis.syntax.command._

class CommandSyntaxSpec extends ZedisTestSuite {

  val stringToInt: String =!> Int = Command(_.toInt)

  val returnNull: String =!> String = Command(_ => null)

  val goodStr = "100"
  val badStr  = "100a"

  describe("ExecOps") {

    it("exec") {
      stringToInt.exec(goodStr)       shouldEqual \/-(goodStr.toInt)
      stringToInt.exec(badStr).isLeft shouldEqual true

      returnNull.exec("").isLeft shouldEqual true
    }

    it("execOpt") {
      stringToInt.execOpt(goodStr) shouldEqual Some(goodStr.toInt)
      stringToInt.execOpt(badStr)  shouldEqual None

      returnNull.execOpt("") shouldEqual None
    }

  }
}
