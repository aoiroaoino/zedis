package zedis
package interp

import scala.collection.JavaConverters._

import scalaz.{~>, Id, Applicative}
import scalaz.Id.Id
import redis.clients.jedis.Jedis

import zedis.adt._

class JedisInterpreter[F[_]: Applicative](connection: Jedis) extends Interpreter[F] {

  override def hashesProc[A] = {
    case HDEL(key, fields) =>
      wrapF(connection.hdel(key, fields: _*))
    case HEXISTS(key, field) =>
      wrapF(connection.hexists(key, field))
    case HGET(key, field, codec) =>
      wrapF(Option(connection.hget(key, field)).map(codec.decode _))
    case HGETALL(key, codec) =>
      wrapF(connection.hgetAll(key).asScala.toMap.mapValues(codec.decode _))
    case HINCRBY(key, field, value) =>
      wrapF(connection.hincrBy(key, field, value))
    case HINCRBYFLOAT(key, field, value) =>
      wrapF(connection.hincrByFloat(key, field, value))
    case HKEYS(key) =>
      wrapF(connection.hkeys(key).asScala.toSet)
    case HLEN(key) =>
      wrapF(connection.hlen(key))
    case HMGET(key, fields, codec) =>
      wrapF(connection.hmget(key, fields: _*).asScala.map(Option(_).map(codec.decode _)))
    case HMSET(key, map, codec) =>
      wrapF(connection.hmset(key.toString, map.mapValues(codec.encode).asJava) == "OK")
    // case class HSCAN =>
    //   wrapF(connection.
    case HSET(key, field, value, codec) =>
      wrapF(connection.hset(key, field, codec.encode(value)))
    case HSETNX(key, field, value, codec) =>
      wrapF(connection.hsetnx(key, field, codec.encode(value)))
    // case HSTRLEN(key, field, codec) =>
    //   wrapF(connection.
    case HVALS(key, codec) =>
      wrapF(connection.hvals(key).asScala.map(codec.decode _))
  }
}

object JedisInterpreter {
  def apply(connection: Jedis): CommandADT ~> Id = new JedisInterpreter[Id](connection).impl
}
