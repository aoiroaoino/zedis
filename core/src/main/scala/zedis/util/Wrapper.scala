package zedis
package util

import scala.concurrent.{Future, ExecutionContext}
import scalaz.Id.Id

trait Wrapper[F[_]] {

  def wrap[A](a: => A): F[A]
}

object Wrapper {

  def apply[F[_]: Wrapper]: Wrapper[F] = implicitly[Wrapper[F]]

  implicit lazy val idWrapper: Wrapper[Id] = new Wrapper[Id] {
    def wrap[A](a: => A): Id[A] = a
  }

  implicit def scalaFutureWrapper(implicit ec: ExecutionContext): Wrapper[Future] = new Wrapper[Future] {
    def wrap[A](a: => A): Future[A] = Future(a)
  }
}
