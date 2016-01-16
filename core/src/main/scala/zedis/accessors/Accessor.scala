package zedis
package accessor

import scalaz.=?>
import scalaz.effect.IO

import redis.clients.jedis.{Jedis, Pipeline}

abstract class Accessor {
  import zedis.util.resource._

  protected[this] val _host: String
  protected[this] val _port: Int

  private[this] def connection: IO[Jedis] =
    IO(new Jedis(_host, _port))

  private[this] def toPipeline(j: Jedis): IO[Pipeline] =
    IO(j.pipelined())

  def using[A](f: Jedis =?> A): IO[Option[A]] =
    connection.using(j => IO(f.run(j)))

  def pipeline[A](f: Pipeline =?> A): IO[Option[A]] =
    connection.flatMap(toPipeline).map(f.run)

  def autoSyncPipeline[A](f: Pipeline =?> A): IO[Option[A]] =
    connection.flatMap(toPipeline).using(p => IO(f.run(p)))
}
