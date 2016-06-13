package zedis

import scalaz.Free
import zedis.adt._

package object command {

  type RedisCommand[A] = Free[RedisCommandADT, A]

  object all extends HasheCommand

  object hash extends HasheCommand
}
