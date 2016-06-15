package zedis

import scalaz.Free
import zedis.adt._

package object command {

  type RedisCommand[A] = Free[CommandADT, A]
}
