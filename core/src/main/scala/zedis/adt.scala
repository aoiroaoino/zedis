package zedis

import scalaz.NonEmptyList

object adt {

  sealed trait CommandADT[+A]

  //
  // connection
  //

  case class AUTH(password: String) extends CommandADT[String]

  case class ECHO(message: String) extends CommandADT[String]

  case class PING(message: Option[String]) extends CommandADT[String]

  case object QUIT extends CommandADT[String]

  case class SELECT(dbIndex: Int) extends CommandADT[String]

  //
  // hashes
  //

  case class HDEL(key: String, fields: Seq[String]) extends CommandADT[Long]

  case class HEXISTS(key: String, field: String) extends CommandADT[Boolean]

  case class HGET[T](key: String, field: String, _codec: Codec[T]) extends CommandADT[Option[T]]

  case class HGETALL[T](key: String, _codec: Codec[T]) extends CommandADT[Map[String, T]]

  case class HINCRBY(key: String, field: String, value: Long) extends CommandADT[Long]

  case class HINCRBYFLOAT(key: String, field: String, value: Double) extends CommandADT[Double]

  case class HKEYS(key: String) extends CommandADT[Set[String]]

  case class HLEN(key: String) extends CommandADT[Long]

  case class HMGET[T](key: String, fields: Seq[String], _codec: Codec[T]) extends CommandADT[Seq[Option[T]]]

  case class HMSET[T](key: String, map: Map[String, T], _codec: Codec[T]) extends CommandADT[Boolean]

  // case class HSCAN

  case class HSET[T](key: String, field: String, value: T, _codec: Codec[T]) extends CommandADT[Long]

  case class HSETNX[T](key: String, field: String, value: T, _codec: Codec[T]) extends CommandADT[Long]

  case class HSTRLEN(key: String, field: String) extends CommandADT[Long]

  case class HVALS[T](key: String, _codec: Codec[T]) extends CommandADT[Seq[T]]

  //
  // keys
  //

  case class DEL(keys: NonEmptyList[String]) extends CommandADT[Long]

  case class DUMP(key: String) extends CommandADT[Array[Byte]]

  case class EXISTS(key: String) extends CommandADT[Boolean]

  case class EXPIRE(key: String, seconds: Long) extends CommandADT[Boolean]

  case class EXPIREAT(key: String, unixTime: Long) extends CommandADT[Boolean]

  case class KEYS(pattern: String) extends CommandADT[Set[String]]

  // case class MIGRATE

  case class MOVE(key: String, dbIndex: Int) extends CommandADT[Boolean]

  // case class OBJECT

  case class PERSIST(key: String) extends CommandADT[Boolean]

  case class PEXPIRE(key: String, milliseconds: Long) extends CommandADT[Long]

  case class PEXPIREAT(key: String, millisecondsUnixTime: Long) extends CommandADT[Long]

  case class PTTL(key: String) extends CommandADT[Long]

  case object RANDOMKEY extends CommandADT[String]

  case class RENAME(oldKey: String, newKey: String) extends CommandADT[String]

  case class RENAMENX(oldKey: String, newKey: String) extends CommandADT[Boolean]

  case class RESTORE(key: String, milliseconds: Long, data: Array[Byte]) extends CommandADT[String]

  // case class SCAN

  // case class SORT

  case class TTL(key: String) extends CommandADT[Long]

  // type is reserved word ...
  case class VALUETYPE(key: String) extends CommandADT[String]

  // case class WAIT

  //
  // lists
  //

  // case class BLPOP

  // case class BRPOP

  // case class BRPOPLPUSH

  case class LINDEX[T](key: String, index: Long, _codec: Codec[T]) extends CommandADT[Option[T]]

  case class LINSERT[T](key: String, position: String, pivot: T, value: T, _codec: Codec[T]) extends CommandADT[Long]

  case class LLEN(key: String) extends CommandADT[Long]

  case class LPOP[T](key: String, _codec: Codec[T]) extends CommandADT[Option[T]]

  case class LPUSH[T](key: String, values: Seq[T], _codec: Codec[T]) extends CommandADT[Long]

  case class LPUSHX[T](key: String, values: Seq[T], _codec: Codec[T]) extends CommandADT[Long]

  case class LRANGE[T](key: String, start: Long, end: Long, _codec: Codec[T]) extends CommandADT[Seq[T]]

  case class LREM(key: String, count: Long, value: String) extends CommandADT[Long]

  case class LSET[T](key: String, index: Long, value: T, _codec: Codec[T]) extends CommandADT[String]

  case class LTRIM(key: String, start: Long, stop: Long) extends CommandADT[String]

  case class RPOP[T](key: String, _codec: Codec[T]) extends CommandADT[Option[T]]

  case class RPOPLPUSH[T](source: String, destination: String, _codec: Codec[T]) extends CommandADT[T]

  case class RPUSH[T](key: String, value: Seq[T], _codec: Codec[T]) extends CommandADT[Long]

  case class RPUSHX[T](key: String, value: T, _codec: Codec[T]) extends CommandADT[Long]

  //
  // sets
  //

  case class SADD[T](key: String, values: NonEmptyList[T], _codec: Codec[T]) extends CommandADT[Long]

  case class SCARD(key: String) extends CommandADT[Long]

  case class SDIFF[T](keys: NonEmptyList[String], _codec: Codec[T]) extends CommandADT[Set[T]]

  case class SDIFFSTORE(destination: String, keys: NonEmptyList[String]) extends CommandADT[Long]

  case class SINTER[T](keys: NonEmptyList[String], _codec: Codec[T]) extends CommandADT[Set[T]]

  case class SINTERSTORE(destination: String, keys: NonEmptyList[String]) extends CommandADT[Long]

  case class SISMEMBER[T](key: String, member: T, _codec: Codec[T]) extends CommandADT[Boolean]

  case class SMEMBERS[T](key: String, _codec: Codec[T]) extends CommandADT[Set[T]]

  case class SMOVE[T](source: String, destination: String, member: T, _codec: Codec[T]) extends CommandADT[Long]

  case class SPOP[T](key: String, count: Long, _codec: Codec[T]) extends CommandADT[Set[T]]

  case class SRANDMEMBER[T](key: String, count: Long, _codec: Codec[T]) extends CommandADT[Seq[T]]

  case class SREM[T](key: String, members: NonEmptyList[T], _codec: Codec[T]) extends CommandADT[Long]

  // case class SSCAN

  case class SUNION[T](keys: NonEmptyList[String], _codec: Codec[T]) extends CommandADT[Set[T]]

  case class SUNIONSTORE(destination: String, keys: NonEmptyList[String]) extends CommandADT[Long]

  //
  // strings
  //

  case class APPEND[T](key: String, value: T, _codec: Codec[T]) extends CommandADT[Long]

  // case class BITCOUNT

  // case class BITFIELD

  // case class BITOP

  // case class BITPOS

  case class DECR(key: String) extends CommandADT[Long]

  case class DECRBY(key: String, value: Long) extends CommandADT[Long]

  case class GET[T](key: String, _codec: Codec[T]) extends CommandADT[Option[T]]

  // case class GETBIT

  case class GETRANGE(key: String, start: Long, end: Long) extends CommandADT[String]

  case class GETSET[T](key: String, value: T, _codec: Codec[T]) extends CommandADT[T]

  case class INCR(key: String) extends CommandADT[Long]

  case class INCRBY(key: String, value: Long) extends CommandADT[Long]

  case class INCRBYFLOAT(key: String, value: Double) extends CommandADT[Double]

  case class MGET[T](keys: NonEmptyList[String], _codec: Codec[T]) extends CommandADT[Seq[Option[T]]]

  case class MSET[T](map: Map[String, T], _codec: Codec[T]) extends CommandADT[String]

  case class MSETNX[T](map: Map[String, T], _codec: Codec[T]) extends CommandADT[Long]

  case class PSETEX[T](key: String, milliseconds: Long, value: T, _codec: Codec[T]) extends CommandADT[String]

  // todo: support options
  case class SET[T](key: String, value: T, _codec: Codec[T]) extends CommandADT[String]

  // case class SETBIT

  case class SETEX[T](key: String, seconds: Long, value: T, _codec: Codec[T]) extends CommandADT[String]

  case class SETNX[T](key: String, value: T, _codec: Codec[T]) extends CommandADT[Long]

  case class SETRANGE[T](key: String, offset: Long, value: T, _codec: Codec[T]) extends CommandADT[Long]

  case class STRLEN(key: String) extends CommandADT[Long]
}
