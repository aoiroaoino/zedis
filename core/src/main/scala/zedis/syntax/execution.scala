package zedis
package syntax

import scalaz.{\/, -\/, \/-}
import scalaz.effect.IO

trait ExecutionSyntax {
  implicit def executionOps[A](ioa: IO[Option[A]]) = new ExecutionOps(ioa)
}

final class ExecutionOps[A](ioa: IO[Option[A]]) {
  import scalaz.syntax.catchable._

  def exec(): Throwable \/ Option[A] =
    ioa.attempt.unsafePerformIO()

  def execEither(): Either[Throwable, Option[A]] =
    ioa.attempt.map(_.toEither).unsafePerformIO()

  def execOpt(): Option[A] =
    ioa.attempt.map(_ match {
      case \/-(Some(v)) => Some(v)
      case _            => None
    }).unsafePerformIO()
}
