package zedis
package interp

import scala.collection.JavaConverters._
import scala.concurrent.{Future, ExecutionContext}

import scalaz.{~>, Id}
import scalaz.Id.Id
import redis.clients.jedis.Jedis
import redis.clients.jedis.BinaryClient.LIST_POSITION

import zedis.adt.CommandADT, CommandADT._
import zedis.command.list.Position
import zedis.util.Wrapper

object JedisInterpreter {
  def apply[F[_]: Wrapper](connection: Jedis): CommandADT ~> F = new JedisInterpreter[F](connection).impl

  def id(connection: Jedis): CommandADT ~> Id = apply[Id](connection)

  def scalaFuture(connection: Jedis)(implicit ec: ExecutionContext): CommandADT ~> Future = apply[Future](connection)


  def toJedisPosition(p: Position): LIST_POSITION = p match {
    case Position.Before => LIST_POSITION.BEFORE
    case Position.After  => LIST_POSITION.AFTER
  }
}

class JedisInterpreter[F[_]: Wrapper](connection: Jedis) extends Interpreter[F] {
  import JedisInterpreter._

  override def connectionProc[A] = {
    case AUTH(password) =>
      wrapF(connection.auth(password))
    case ECHO(msg) =>
      wrapF(connection.echo(msg))
    case PING(_) =>
      wrapF(connection.ping())
    case QUIT =>
      wrapF(connection.quit())
    case SELECT(index) =>
      wrapF(connection.select(index))
  }

  override def hashesProc[A] = {
    case HDEL(key, fields) =>
      wrapF(connection.hdel(key, fields: _*))
    case HEXISTS(key, field) =>
      wrapF(connection.hexists(key, field))
    case HGET(key, field,codec) =>
      wrapF(Option(connection.hget(key, field)).map(codec.decode _))
    case HGETALL(key,codec) =>
      wrapF(connection.hgetAll(key).asScala.toMap.mapValues(codec.decode _))
    case HINCRBY(key, field, value) =>
      wrapF(connection.hincrBy(key, field, value))
    case HINCRBYFLOAT(key, field, value) =>
      wrapF(connection.hincrByFloat(key, field, value))
    case HKEYS(key) =>
      wrapF(connection.hkeys(key).asScala.toSet)
    case HLEN(key) =>
      wrapF(connection.hlen(key))
    case HMGET(key, fields,codec) =>
      wrapF(connection.hmget(key, fields: _*).asScala.map(Option(_).map(codec.decode _)))
    case HMSET(key, map,codec) =>
      wrapF(connection.hmset(key.toString, map.mapValues(codec.encode).asJava) == "OK")
    // case class HSCAN =>
    //   wrapF(connection.
    case HSET(key, field, value,codec) =>
      wrapF(connection.hset(key, field,codec.encode(value)))
    case HSETNX(key, field, value,codec) =>
      wrapF(connection.hsetnx(key, field,codec.encode(value)))
    // case HSTRLEN(key, field,codec) =>
    //   wrapF(connection.
    case HVALS(key,codec) =>
      wrapF(connection.hvals(key).asScala.map(codec.decode _))
  }

  override def keysProc[A] = {
    case DEL(keys) =>
      wrapF(connection.del(keys.list.toList: _*))
    case DUMP(key) =>
      wrapF(connection.dump(key))
    case EXISTS(key) =>
      wrapF(connection.exists(key))
    case EXPIRE(key, seconds) =>
      wrapF(connection.expire(key, seconds.toInt))
    case EXPIREAT(key, unixTime) =>
      wrapF(connection.expireAt(key, unixTime))
    case KEYS(pattern) =>
      wrapF(connection.keys(pattern).asScala.toSet)
    // case MIGRATE
    //  wrapF(connection.
    case MOVE(key, dbIndex) =>
      wrapF(connection.move(key, dbIndex))
    // case OBJECT
    //  wrapF(connection.
    case PERSIST(key) =>
      wrapF(connection.persist(key))
    case PEXPIRE(key, milliseconds) =>
      wrapF(connection.pexpire(key, milliseconds))
    case PEXPIREAT(key, millisecondsUnixTime) =>
      wrapF(connection.pexpireAt(key, millisecondsUnixTime))
    case PTTL(key) =>
      wrapF(connection.pttl(key))
    case RANDOMKEY =>
      wrapF(connection.randomKey())
    case RENAME(key, newKey) =>
      wrapF(connection.rename(key, newKey))
    case RENAMENX(key, newKey) =>
      wrapF(connection.renamenx(key, newKey))
    case RESTORE(key, milliseconds, data) =>
      wrapF(connection.restore(key, milliseconds.toInt, data))
    // case SCAN
    //  wrapF(connection.
    // case SORT
    //  wrapF(connection.
    case TTL(key) =>
      wrapF(connection.ttl(key))
    case TYPE(key) =>
      wrapF(connection.`type`(key))
    // case WAIT
    //  wrapF(connection.
  }

  override def listsProc[A] = {
    // case BLPOP
    //  wrapF(connection.
    // case BRPOP
    //  wrapF(connection.
    // case BRPOPLPUSH
    //  wrapF(connection.
    case LINDEX(key, index, codec) =>
      wrapF(Option(connection.lindex(key, index)).map(codec.decode))
    case LINSERT(key, position, pivot, value, codec) =>
      wrapF(connection.linsert(key, toJedisPosition(position), codec.encode(pivot), codec.encode(value)))
    case LLEN(key) =>
      wrapF(connection.llen(key))
    case LPOP(key, codec) =>
      wrapF(Option(connection.lpop(key)).map(codec.decode))
    case LPUSH(key, values, codec) =>
      wrapF(connection.lpush(key, values.map(codec.encode): _*))
    case LPUSHX(key, values, codec) =>
      wrapF(connection.lpushx(key, values.map(codec.encode): _*))
    case LRANGE(key, start, end, codec) =>
      wrapF(connection.lrange(key, start, end).asScala.map(codec.decode))
    case LREM(key, count, value) =>
      wrapF(connection.lrem(key, count, value))
    case LSET(key, index, value, codec) =>
      wrapF(connection.lset(key, index, codec.encode(value)))
    case LTRIM(key, start, stop) =>
      wrapF(connection.ltrim(key, start, stop))
    case RPOP(key, codec) =>
      wrapF(Option(connection.rpop(key)).map(codec.decode))
    case RPOPLPUSH(source, destination, codec) =>
      wrapF(codec.decode(connection.rpoplpush(source, destination)))
    case RPUSH(key, value, codec) =>
      wrapF(connection.rpush(key, value.map(codec.encode): _*))
    case RPUSHX(key, value, codec) =>
      wrapF(connection.rpushx(key, codec.encode(value)))
  }

  override def setsProc[A] = {
    case SADD(key, values, codec) =>
      wrapF(connection.sadd(key, values.list.toList.map(codec.encode): _*))
    case SCARD(key) =>
      wrapF(connection.scard(key))
    case SDIFF(keys, codec) =>
      wrapF(connection.sdiff(keys.list.toList.map(codec.encode): _*).asScala.toSet)
    case SDIFFSTORE(destination, keys) =>
      wrapF(connection.sdiffstore(destination, keys.list.toList: _*))
    case SINTER(keys, codec) =>
      wrapF(connection.sinter(keys.list.toList: _*).asScala.toSet.map(codec.decode))
    case SINTERSTORE(destination, keys) =>
      wrapF(connection.sinterstore(destination, keys.list.toList: _*))
    case SISMEMBER(key, member, codec) =>
      wrapF(connection.sismember(key, codec.encode(member)))
    case SMEMBERS(key, codec) =>
      wrapF(connection.smembers(key).asScala.toSet.map(codec.decode))
    case SMOVE(source, destination, member, codec) =>
      wrapF(connection.smove(source, destination, codec.encode(member)))
    case SPOP(key, count, codec) =>
      wrapF(connection.spop(key, count).asScala.toSet.map(codec.decode))
    case SRANDMEMBER(key, count, codec) =>
      wrapF(connection.srandmember(key, count.toInt).asScala.map(codec.decode))
    case SREM(key, members, codec) =>
      wrapF(connection.srem(key, members.list.toList.map(codec.encode): _*))
    // case SSCAN
    case SUNION(keys, codec) =>
      wrapF(connection.sunion(keys.list.toList: _*).asScala.toSet.map(codec.decode))
    case SUNIONSTORE(destination, keys) =>
      wrapF(connection.sunionstore(destination, keys.list.toList: _*))
  }

  override def stringsProc[A] = {
    case APPEND(key, value, codec) =>
      wrapF(connection.append(key, codec.encode(value)))
    // case BITCOUNT
    //  wrapF(connection.
    // case BITFIELD
    //  wrapF(connection.
    // case BITOP
    //  wrapF(connection.
    // case BITPOS
    //  wrapF(connection.
    case DECR(key) =>
      wrapF(connection.decr(key))
    case DECRBY(key, value) =>
      wrapF(connection.decrBy(key, value))
    case GET(key, codec) =>
      wrapF(Option(connection.get(key)).map(codec.decode))
    // case GETBIT
    //  wrapF(connection.
    case GETRANGE(key, start, end) =>
      wrapF(connection.getrange(key, start, end))
    case GETSET(key, value, codec) =>
      val result = connection.getSet(key, codec.encode(value))
      wrapF(codec.decode(result))
    case INCR(key) =>
      wrapF(connection.incr(key))
    case INCRBY(key, value) =>
      wrapF(connection.incrBy(key, value))
    case INCRBYFLOAT(key, value) =>
      wrapF(connection.incrByFloat(key, value))
    case MGET(keys, codec) =>
      wrapF(connection.mget(keys.list.toList: _*).asScala.map(Option(_)))
    case MSET(map, codec) =>
      val keyValues = map.mapValues(codec.encode).flatMap { case (k, v) => List(k, v) }.toList
      wrapF(connection.mset(keyValues: _*))
    case MSETNX(map, codec) =>
      val keyValues = map.mapValues(codec.encode).flatMap { case (k, v) => List(k, v) }.toList
      wrapF(connection.msetnx(keyValues: _*))
    case PSETEX(key, milliseconds, value, codec) =>
      wrapF(connection.psetex(key, milliseconds, codec.encode(value)))
    case SET(key, value, codec) =>
      wrapF(connection.set(key, codec.encode(value)))
    // case SETBIT
    //  wrapF(connection.
    case SETEX(key, seconds, value, codec) =>
      wrapF(connection.setex(key, seconds.toInt, codec.encode(value)))
    case SETNX(key, value, codec) =>
      wrapF(connection.setnx(key, codec.encode(value)))
    case SETRANGE(key, offset, value, codec) =>
      wrapF(connection.setrange(key, offset, codec.encode(value)))
    case STRLEN(key) =>
      wrapF(connection.strlen(key))
  }

  override def unsupportedError[A] = {
    case other => sys.error(s"$other is unsupported command in Jedis connection.")
  }
}
