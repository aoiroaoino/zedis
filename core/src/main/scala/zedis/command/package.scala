package zedis

import scalaz.{Free, NonEmptyList}
import zedis.adt._

package object command {

  type RedisCommand[A] = Free[CommandADT, A]

  def nel[A](head: A, tail: Seq[A]): NonEmptyList[A] = NonEmptyList(head, tail: _*)
}
