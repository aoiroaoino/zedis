package zedis
package interp

import scala.collection.JavaConverters._

import scalaz.{~>, Id}
import scalaz.Id.Id
import redis.clients.jedis.Jedis

import zedis.adt._

object JedisInterpreter {

  def full(connection: Jedis): RedisCommandADT ~> Id = new (RedisCommandADT ~> Id) {
    override def apply[A](adt: RedisCommandADT[A]): Id[A] = adt match {
      case HDEL(key, fields) =>
        connection.hdel(key, fields: _*)
      case HEXISTS(key, field) =>
        connection.hexists(key, field)
      case HGET(key, field, codec) =>
        Option(connection.hget(key, field)).map(codec.decode)
      case HGETALL(key, codec) =>
        connection.hgetAll(key).asScala.toMap.mapValues(codec.decode)
      case HINCRBY(key, field, value) =>
        connection.hincrBy(key, field, value)
      case HINCRBYFLOAT(key, field, value) =>
        connection.hincrByFloat(key, field, value)
      case HKEYS(key) =>
        connection.hkeys(key).asScala.toSet
      case HLEN(key) =>
        connection.hlen(key)
      case HMGET(key, fields, codec) =>
        connection.hmget(key, fields: _*).asScala.map(Option(_).map(codec.decode))
      case HMSET(key, map, codec) =>
        connection.hmset(key.toString, map.mapValues(codec.encode).asJava) == "OK"
      // case class HSCAN =>
      //   connection.hscan
      case HSET(key, field, value, codec) =>
        connection.hset(key, field, codec.encode(value))
      case HSETNX(key, field, value, codec) =>
        connection.hsetnx(key, field, codec.encode(value))
      // case HSTRLEN(key, field, codec) =>
      //   sys.error(s"""$other is unsupported command in Jedis connection.""")
      case HVALS(key, codec) =>
        connection.hvals(key).asScala.map(codec.decode)
      case other =>
        sys.error(s"""$other is unsupported command in Jedis connection.""")
    }
  }
}
