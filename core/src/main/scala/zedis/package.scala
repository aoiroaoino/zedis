package object zedis {

  import scalaz._

  def kleisliOpt[A, B](f: A => B): A =?> B =
    Kleisli[Option, A, B](a => Option(f(a)))
}
