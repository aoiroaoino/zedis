package zedis
package util

import scalaz.effect.{IO, Resource}

import redis.clients.jedis.Jedis

trait JedisUtils
  extends JedisInstances

trait JedisInstances {

  implicit def jedisResourceInstance: Resource[Jedis] = new Resource[Jedis] {
    override def close(f: Jedis): IO[Unit] =
      IO{ f.close() }
  }
}
