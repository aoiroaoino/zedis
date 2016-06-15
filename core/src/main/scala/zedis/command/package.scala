package zedis

import scalaz.Free
import zedis.adt._

package object command {

  type RedisCommand[A] = Free[CommandADT, A]

  object all extends AllCommand

  object connection extends ConnectionCommand

  object hash extends HasheCommand

  object key extends KeyCommand
}
