package zedis
package syntax

import scalaz.\/
import scalaz.syntax.catchable._

trait KleisliIOSyntax {

  implicit class KleisliIOOps[A, B](command: A =!> B) {

    def exec(a: A): Throwable \/ B =
      command.run(a).attempt.unsafePerformIO

    def execOpt(a: A): Option[B] =
      command.run(a).attempt.unsafePerformIO.toOption
  }
}

object kleisliio extends KleisliIOSyntax
