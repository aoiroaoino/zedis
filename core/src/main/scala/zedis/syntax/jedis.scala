package zedis.syntax

import scalaz.{Monad, \/}
import scalaz.syntax.catchable._
import scalaz.effect.IO

import redis.clients.jedis.Jedis

import zedis.free.jedis._

object jedis {
  implicit class JedisCommandOps[M[_]: Monad, A](ma: JedisCommand[A]) {
    def exec(session: Jedis): Throwable \/ A =
      runCommand[IO, A](ma, session).attempt.unsafePerformIO.flatMap{ n =>
        if (n == null) \/.left(new Exception("not found value")) else \/.right(n)
      }
  }
}
