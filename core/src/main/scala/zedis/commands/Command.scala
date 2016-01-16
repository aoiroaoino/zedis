package zedis
package commands

import scala.collection.JavaConversions._
import java.util.{Map => JMap, Set => JSet, List => JList}

import scalaz._
import redis.clients.jedis.Jedis

trait Command {

  def close(): Jedis =?> Unit =
    kleisliOpt{ _.close() }

  def decr(key: String): Jedis =?> Long =
    kleisliOpt{ _.decr(key) }

  def decrBy(key: String, integer: Long): Jedis =?> Long =
    kleisliOpt{ _.decrBy(key, integer) }

  def del(keys: String*): Jedis =?> Long =
    kleisliOpt{ _.del(keys: _*) }

  def del(key: String): Jedis =?> Long =
    kleisliOpt{ _.del(key) }

  def echo(string: String): Jedis =?> String =
    kleisliOpt{ _.echo(string) }

  def exists(key: String): Jedis =?> Boolean =
    kleisliOpt{ _.exists(key) }

  def expire(key: String, seconds: Int): Jedis =?> Long =
    kleisliOpt{ _.expire(key, seconds) }

  def expireAt(key: String, unixTime: Long): Jedis =?> Long =
    kleisliOpt{ _.expireAt(key, unixTime) }

  def get(key: String): Jedis =?> String =
    kleisliOpt{ _.get(key) }

  def getSet(key: String, value: String): Jedis =?> String =
    kleisliOpt{ _.getSet(key, value) }

  def hdel(key: String, fields: String*): Jedis =?> Long =
    kleisliOpt{ _.hdel(key, fields: _*) }

  def hexists(key: String, field: String): Jedis =?> Boolean =
    kleisliOpt{ _.hexists(key, field) }

  def hget(key: String, field: String): Jedis =?> String =
    kleisliOpt{ _.hget(key, field) }

  def hgetAll(key: String): Jedis =?> JMap[String, String] =
    kleisliOpt{ _.hgetAll(key) }

  def hincrBy(key: String, field: String, value: Long): Jedis =?> Long =
    kleisliOpt{ _.hincrBy(key, field, value) }

  def hincrByFloat(key: String, field: String, value: Double): Jedis =?> Double =
    kleisliOpt{ _.hincrByFloat(key, field, value) }

  def hkeys(key: String): Jedis =?> JSet[String] =
    kleisliOpt{ _.hkeys(key) }

  def hlen(key: String): Jedis =?> Long =
    kleisliOpt{ _.hlen(key) }

  def hmget(key: String, fields: String*): Jedis =?> JList[String] =
    kleisliOpt{ _.hmget(key, fields: _*) }

  def hmset(key: String, hash: Map[String, String]): Jedis =?> String =
    kleisliOpt{ _.hmset(key, hash) }

  def hset(key: String, field: String, value: String): Jedis =?> Long =
    kleisliOpt{ _.hset(key, field, value) }

  def hsetnx(key: String, field: String, value: String): Jedis =?> Long =
    kleisliOpt{ _.hsetnx(key, field, value) }

  def hvals(key: String): Jedis =?> JList[String] =
    kleisliOpt{ _.hvals(key) }

  def incr(key: String): Jedis =?> Long =
    kleisliOpt{ _.incr(key) }

  def incrBy(key: String, integer: Long): Jedis =?> Long =
    kleisliOpt{ _.incrBy(key, integer) }

  def incrByFloat(key: String, value: Double): Jedis =?> Double =
    kleisliOpt{ _.incrByFloat(key, value) }

  def keys(pattern: String): Jedis =?> JSet[String] =
    kleisliOpt{ _.keys(pattern) }

  def lindex(key: String, index: Long): Jedis =?> String =
    kleisliOpt{ _.lindex(key, index) }

  def llen(key: String): Jedis =?> Long =
    kleisliOpt{ _.llen(key) }

  def lpop(key: String): Jedis =?> String =
    kleisliOpt{ _.lpop(key) }

  def lpush(key: String, strings: String*): Jedis =?> Long =
    kleisliOpt{ _.lpush(key, strings: _*) }

  def lpushx(key: String, string: String*): Jedis =?> Long =
    kleisliOpt{ _.lpushx(key, string: _*) }

  def lrange(key: String, start: Long, end: Long): Jedis =?> JList[String] =
    kleisliOpt{ _.lrange(key, start, end) }

  def lrem(key: String, count: Long, value: String): Jedis =?> Long =
    kleisliOpt{ _.lrem(key, count, value) }

  def lset(key: String, index: Long, value: String): Jedis =?> String =
    kleisliOpt{ _.lset(key, index, value) }

  def ltrim(key: String, start: Long, end: Long): Jedis =?> String =
    kleisliOpt{ _.ltrim(key, start, end) }

  def mget(keys: String*): Jedis =?> JList[String] =
    kleisliOpt{ _.mget(keys: _*) }

  def persist(key: String): Jedis =?> Long =
    kleisliOpt{ _.persist(key) }

  def randomKey(): Jedis =?> String =
    kleisliOpt{ _.randomKey() }

  def rename(oldkey: String, newkey: String): Jedis =?> String =
    kleisliOpt{ _.rename(oldkey, newkey) }

  def restore(key: String, ttl: Int, serializedValue: Array[Byte]): Jedis =?> String =
    kleisliOpt{ _.restore(key, ttl, serializedValue) }

  def rpop(key: String): Jedis =?> String =
    kleisliOpt{ _.rpop(key) }

  def rpoplpush(srckey: String, dstkey: String): Jedis =?> String =
    kleisliOpt{ _.rpoplpush(srckey, dstkey) }

  def rpush(key: String, strings: String*): Jedis =?> Long =
    kleisliOpt{ _.rpush(key, strings: _*) }

  def rpushx(key: String, string: String*): Jedis =?> Long =
    kleisliOpt{ _.rpushx(key, string: _*) }

  def set(key: String, value: String): Jedis =?> String =
    kleisliOpt{ _.set(key, value) }

  def set(key: String, value: String, nxxx: String): Jedis =?> String =
    kleisliOpt{ _.set(key, value, nxxx) }

  def set(key: String, value: String, nxxx: String, expx: String, time: Int): Jedis =?> String =
    kleisliOpt{ _.set(key, value, nxxx, expx, time) }

  def set(key: String, value: String, nxxx: String, expx: String, time: Long): Jedis =?> String =
    kleisliOpt{ _.set(key, value, nxxx, expx, time) }

  def ttl(key: String): Jedis =?> Long =
    kleisliOpt{ _.ttl(key) }

}
