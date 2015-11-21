package object zedis {

  import scalaz.Kleisli
  import scalaz.effect.IO
  import scalaz.concurrent.Task

  type Command[A, B] = Kleisli[IO, A, B]

  type =!>[A, B] = Command[A, B]

  object Command {
    def apply[A, B](f: A => B): A =!> B =
      Kleisli{ a => IO(f(a)) }
  }

  type AsyncCommand[A, B] = Kleisli[Task, A, B]

  type =&>[A, B] = AsyncCommand[A, B]

  object AsyncCommand {
    def apply[A, B](f: A => B): A =&> B =
      Kleisli{ a => Task(f(a)) }
  }
}
