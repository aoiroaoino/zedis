package zedis
package commands

import scala.collection.JavaConversions._

import java.util.{Map => JMap, Set => JSet, List => JList}

import redis.clients.jedis.Jedis

trait JedisCommand {

  def close(): Jedis =!> Unit =
    KleisliIO{ _.close() }

  def decr(key: String): Jedis =!> Long =
    KleisliIO{ _.decr(key) }

  def decrBy(key: String, integer: Long): Jedis =!> Long =
    KleisliIO{ _.decrBy(key, integer) }

  def del(keys: String*): Jedis =!> Long =
    KleisliIO{ _.del(keys: _*) }

  def del(key: String): Jedis =!> Long =
    KleisliIO{ _.del(key) }

  def echo(string: String): Jedis =!> String =
    KleisliIO{ _.echo(string) }

  def exists(key: String): Jedis =!> Boolean =
    KleisliIO{ _.exists(key) }

  def expire(key: String, seconds: Int): Jedis =!> Long =
    KleisliIO{ _.expire(key, seconds) }

  def expireAt(key: String, unixTime: Long): Jedis =!> Long =
    KleisliIO{ _.expireAt(key, unixTime) }

  def get(key: String): Jedis =!> String =
    KleisliIO{ _.get(key) }

  def getSet(key: String, value: String): Jedis =!> String =
    KleisliIO{ _.getSet(key, value) }

  def hdel(key: String, fields: String*): Jedis =!> Long =
    KleisliIO{ _.hdel(key, fields: _*) }

  def hexists(key: String, field: String): Jedis =!> Boolean =
    KleisliIO{ _.hexists(key, field) }

  def hget(key: String, field: String): Jedis =!> String =
    KleisliIO{ _.hget(key, field) }

  def hgetAll(key: String): Jedis =!> JMap[String, String] =
    KleisliIO{ _.hgetAll(key) }

  def hincrBy(key: String, field: String, value: Long): Jedis =!> Long =
    KleisliIO{ _.hincrBy(key, field, value) }

  def hincrByFloat(key: String, field: String, value: Double): Jedis =!> Double =
    KleisliIO{ _.hincrByFloat(key, field, value) }

  def hkeys(key: String): Jedis =!> JSet[String] =
    KleisliIO{ _.hkeys(key) }

  def hlen(key: String): Jedis =!> Long =
    KleisliIO{ _.hlen(key) }

  def hmget(key: String, fields: String*): Jedis =!> JList[String] =
    KleisliIO{ _.hmget(key, fields: _*) }

  def hmset(key: String, hash: Map[String, String]): Jedis =!> String =
    KleisliIO{ _.hmset(key, hash) }

  def hset(key: String, field: String, value: String): Jedis =!> Long =
    KleisliIO{ _.hset(key, field, value) }

  def hsetnx(key: String, field: String, value: String): Jedis =!> Long =
    KleisliIO{ _.hsetnx(key, field, value) }

  def hvals(key: String): Jedis =!> JList[String] =
    KleisliIO{ _.hvals(key) }

  def incr(key: String): Jedis =!> Long =
    KleisliIO{ _.incr(key) }

  def incrBy(key: String, integer: Long): Jedis =!> Long =
    KleisliIO{ _.incrBy(key, integer) }

  def incrByFloat(key: String, value: Double): Jedis =!> Double =
    KleisliIO{ _.incrByFloat(key, value) }

  def keys(pattern: String): Jedis =!> JSet[String] =
    KleisliIO{ _.keys(pattern) }

  def lindex(key: String, index: Long): Jedis =!> String =
    KleisliIO{ _.lindex(key, index) }

  def llen(key: String): Jedis =!> Long =
    KleisliIO{ _.llen(key) }

  def lpop(key: String): Jedis =!> String =
    KleisliIO{ _.lpop(key) }

  def lpush(key: String, strings: String*): Jedis =!> Long =
    KleisliIO{ _.lpush(key, strings: _*) }

  def lpushx(key: String, string: String*): Jedis =!> Long =
    KleisliIO{ _.lpushx(key, string: _*) }

  def lrange(key: String, start: Long, end: Long): Jedis =!> JList[String] =
    KleisliIO{ _.lrange(key, start, end) }

  def lrem(key: String, count: Long, value: String): Jedis =!> Long =
    KleisliIO{ _.lrem(key, count, value) }

  def lset(key: String, index: Long, value: String): Jedis =!> String =
    KleisliIO{ _.lset(key, index, value) }

  def ltrim(key: String, start: Long, end: Long): Jedis =!> String =
    KleisliIO{ _.ltrim(key, start, end) }

  def mget(keys: String*): Jedis =!> JList[String] =
    KleisliIO{ _.mget(keys: _*) }

  def persist(key: String): Jedis =!> Long =
    KleisliIO{ _.persist(key) }

  def randomKey(): Jedis =!> String =
    KleisliIO{ _.randomKey() }

  def rename(oldkey: String, newkey: String): Jedis =!> String =
    KleisliIO{ _.rename(oldkey, newkey) }

  def restore(key: String, ttl: Int, serializedValue: Array[Byte]): Jedis =!> String =
    KleisliIO{ _.restore(key, ttl, serializedValue) }

  def rpop(key: String): Jedis =!> String =
    KleisliIO{ _.rpop(key) }

  def rpoplpush(srckey: String, dstkey: String): Jedis =!> String =
    KleisliIO{ _.rpoplpush(srckey, dstkey) }

  def rpush(key: String, strings: String*): Jedis =!> Long =
    KleisliIO{ _.rpush(key, strings: _*) }

  def rpushx(key: String, string: String*): Jedis =!> Long =
    KleisliIO{ _.rpushx(key, string: _*) }

  def set(key: String, value: String): Jedis =!> String =
    KleisliIO{ _.set(key, value) }

  def set(key: String, value: String, nxxx: String): Jedis =!> String =
    KleisliIO{ _.set(key, value, nxxx) }

  def set(key: String, value: String, nxxx: String, expx: String, time: Int): Jedis =!> String =
    KleisliIO{ _.set(key, value, nxxx, expx, time) }

  def set(key: String, value: String, nxxx: String, expx: String, time: Long): Jedis =!> String =
    KleisliIO{ _.set(key, value, nxxx, expx, time) }

  def ttl(key: String): Jedis =!> Long =
    KleisliIO{ _.ttl(key) }

}
