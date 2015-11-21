package zedis
package commands

import scala.collection.JavaConversions._

import java.util.{Map => JMap, Set => JSet, List => JList}

import redis.clients.jedis.Jedis

trait JedisCommand {

  def close(): Jedis =!> Unit =
    Command{ _.close() }

  def decr(key: String): Jedis =!> Long =
    Command{ _.decr(key) }

  def decrBy(key: String, integer: Long): Jedis =!> Long =
    Command{ _.decrBy(key, integer) }

  def del(keys: String*): Jedis =!> Long =
    Command{ _.del(keys: _*) }

  def del(key: String): Jedis =!> Long =
    Command{ _.del(key) }

  def echo(string: String): Jedis =!> String =
    Command{ _.echo(string) }

  def exists(key: String): Jedis =!> Boolean =
    Command{ _.exists(key) }

  def expire(key: String, seconds: Int): Jedis =!> Long =
    Command{ _.expire(key, seconds) }

  def expireAt(key: String, unixTime: Long): Jedis =!> Long =
    Command{ _.expireAt(key, unixTime) }

  def get(key: String): Jedis =!> String =
    Command{ _.get(key) }

  def getSet(key: String, value: String): Jedis =!> String =
    Command{ _.getSet(key, value) }

  def hdel(key: String, fields: String*): Jedis =!> Long =
    Command{ _.hdel(key, fields: _*) }

  def hexists(key: String, field: String): Jedis =!> Boolean =
    Command{ _.hexists(key, field) }

  def hget(key: String, field: String): Jedis =!> String =
    Command{ _.hget(key, field) }

  def hgetAll(key: String): Jedis =!> JMap[String, String] =
    Command{ _.hgetAll(key) }

  def hincrBy(key: String, field: String, value: Long): Jedis =!> Long =
    Command{ _.hincrBy(key, field, value) }

  def hincrByFloat(key: String, field: String, value: Double): Jedis =!> Double =
    Command{ _.hincrByFloat(key, field, value) }

  def hkeys(key: String): Jedis =!> JSet[String] =
    Command{ _.hkeys(key) }

  def hlen(key: String): Jedis =!> Long =
    Command{ _.hlen(key) }

  def hmget(key: String, fields: String*): Jedis =!> JList[String] =
    Command{ _.hmget(key, fields: _*) }

  def hmset(key: String, hash: Map[String, String]): Jedis =!> String =
    Command{ _.hmset(key, hash) }

  def hset(key: String, field: String, value: String): Jedis =!> Long =
    Command{ _.hset(key, field, value) }

  def hsetnx(key: String, field: String, value: String): Jedis =!> Long =
    Command{ _.hsetnx(key, field, value) }

  def hvals(key: String): Jedis =!> JList[String] =
    Command{ _.hvals(key) }

  def incr(key: String): Jedis =!> Long =
    Command{ _.incr(key) }

  def incrBy(key: String, integer: Long): Jedis =!> Long =
    Command{ _.incrBy(key, integer) }

  def incrByFloat(key: String, value: Double): Jedis =!> Double =
    Command{ _.incrByFloat(key, value) }

  def keys(pattern: String): Jedis =!> JSet[String] =
    Command{ _.keys(pattern) }

  def lindex(key: String, index: Long): Jedis =!> String =
    Command{ _.lindex(key, index) }

  def llen(key: String): Jedis =!> Long =
    Command{ _.llen(key) }

  def lpop(key: String): Jedis =!> String =
    Command{ _.lpop(key) }

  def lpush(key: String, strings: String*): Jedis =!> Long =
    Command{ _.lpush(key, strings: _*) }

  def lpushx(key: String, string: String*): Jedis =!> Long =
    Command{ _.lpushx(key, string: _*) }

  def lrange(key: String, start: Long, end: Long): Jedis =!> JList[String] =
    Command{ _.lrange(key, start, end) }

  def lrem(key: String, count: Long, value: String): Jedis =!> Long =
    Command{ _.lrem(key, count, value) }

  def lset(key: String, index: Long, value: String): Jedis =!> String =
    Command{ _.lset(key, index, value) }

  def ltrim(key: String, start: Long, end: Long): Jedis =!> String =
    Command{ _.ltrim(key, start, end) }

  def mget(keys: String*): Jedis =!> JList[String] =
    Command{ _.mget(keys: _*) }

  def persist(key: String): Jedis =!> Long =
    Command{ _.persist(key) }

  def randomKey(): Jedis =!> String =
    Command{ _.randomKey() }

  def rename(oldkey: String, newkey: String): Jedis =!> String =
    Command{ _.rename(oldkey, newkey) }

  def restore(key: String, ttl: Int, serializedValue: Array[Byte]): Jedis =!> String =
    Command{ _.restore(key, ttl, serializedValue) }

  def rpop(key: String): Jedis =!> String =
    Command{ _.rpop(key) }

  def rpoplpush(srckey: String, dstkey: String): Jedis =!> String =
    Command{ _.rpoplpush(srckey, dstkey) }

  def rpush(key: String, strings: String*): Jedis =!> Long =
    Command{ _.rpush(key, strings: _*) }

  def rpushx(key: String, string: String*): Jedis =!> Long =
    Command{ _.rpushx(key, string: _*) }

  def set(key: String, value: String): Jedis =!> String =
    Command{ _.set(key, value) }

  def set(key: String, value: String, nxxx: String): Jedis =!> String =
    Command{ _.set(key, value, nxxx) }

  def set(key: String, value: String, nxxx: String, expx: String, time: Int): Jedis =!> String =
    Command{ _.set(key, value, nxxx, expx, time) }

  def set(key: String, value: String, nxxx: String, expx: String, time: Long): Jedis =!> String =
    Command{ _.set(key, value, nxxx, expx, time) }

  def ttl(key: String): Jedis =!> Long =
    Command{ _.ttl(key) }

}
