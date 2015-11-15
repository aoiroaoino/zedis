package object zedis {

  import scalaz.Kleisli
  import scalaz.effect.IO
  import scalaz.concurrent.Task

  type KleisliIO[A, B] = Kleisli[IO, A, B]

  type =!>[A, B] = KleisliIO[A, B]

  object KleisliIO {
    def apply[A, B](f: A => B): A =!> B =
      Kleisli{ a => IO(f(a)) }
  }

  type KleisliTask[A, B] = Kleisli[Task, A, B]

  type =&>[A, B] = KleisliTask[A, B]

  object KleisliTask {
    def apply[A, B](f: A => B): A =&> B =
      Kleisli{ a => Task(f(a)) }
  }
}
