package zedis
package util

import scalaz.effect.{IO, Resource}
import redis.clients.jedis.{Jedis, Pipeline}

object resource {

  implicit lazy val jedsResourceInstance: Resource[Jedis] = new Resource[Jedis] {
    override def close(f: Jedis): IO[Unit] =
      IO(f.close())
  }

  implicit lazy val pipelineResourceInstance: Resource[Pipeline] = new Resource[Pipeline] {
    override def close(f: Pipeline): IO[Unit] =
      IO(f.sync())
    }
}
