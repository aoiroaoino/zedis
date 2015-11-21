package zedis
package util

import zedis.util.string._

class StringUtilsSpec extends ZedisTestSuite {

  val keyName = "key"

  describe("StringHelper") {

    it("toSlotKey") {
      toSlotKey(keyName) shouldEqual s"{$keyName}"
    }
  }
}
