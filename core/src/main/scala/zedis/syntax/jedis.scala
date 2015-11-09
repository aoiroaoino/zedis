package zedis.syntax

import scalaz.\/
import scalaz.syntax.catchable._
import scalaz.effect.IO

import redis.clients.jedis.Jedis

trait JedisCommandSyntax {
  import zedis.free.jedis._

  implicit class JedisCommandOps[A](ma: JedisCommand[A]) {

    def exec(session: Jedis): Throwable \/ A = {
      runCommand[IO, A](ma, session).attempt.unsafePerformIO.flatMap{ n =>
        if (n == null) \/.left(new Exception("not found value")) else \/.right(n)
      }
    }

    def execOpt(session: Jedis): Option[A] =
      runCommand[IO, A](ma, session).attempt.unsafePerformIO.fold(_ => None, a => Option(a)) // nullチェック含む
  }
}

object jedis extends JedisCommandSyntax
