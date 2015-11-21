package zedis
package syntax

import scalaz.\/

trait CommandSyntax {
  implicit def toCommandExecOps[A, B](command: Command[A, B]): CommandExecOps[A, B] =
    new CommandExecOps(command)
}

final class CommandExecOps[A, B](command: Command[A, B]) {
  import scalaz.syntax.catchable._

  def exec(a: A): Throwable \/ B =
    command.run(a).attempt.unsafePerformIO

  def execOpt(a: A): Option[B] =
    command.run(a).attempt.unsafePerformIO.toOption
}
