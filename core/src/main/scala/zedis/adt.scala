package zedis

object adt {

  sealed trait RedisCommandADT[+A]

  //
  // hashes
  //

  case class HDEL(key: String, fields: Seq[String]) extends RedisCommandADT[Long]

  case class HEXISTS(key: String, field: String) extends RedisCommandADT[Boolean]

  case class HGET[T](key: String, field: String, _codec: Codec[T]) extends RedisCommandADT[Option[T]]

  case class HGETALL[T](key: String, _codec: Codec[T]) extends RedisCommandADT[Map[String, T]]

  case class HINCRBY(key: String, field: String, value: Long) extends RedisCommandADT[Long]

  case class HINCRBYFLOAT(key: String, field: String, value: Double) extends RedisCommandADT[Double]

  case class HKEYS(key: String) extends RedisCommandADT[Set[String]]

  case class HLEN(key: String) extends RedisCommandADT[Long]

  case class HMGET[T](key: String, fields: Seq[String], _codec: Codec[T]) extends RedisCommandADT[Seq[Option[T]]]

  case class HMSET[T](key: String, map: Map[String, T], _codec: Codec[T]) extends RedisCommandADT[Boolean]

  // case class HSCAN

  case class HSET[T](key: String, field: String, value: T, _codec: Codec[T]) extends RedisCommandADT[Long]

  case class HSETNX[T](key: String, field: String, value: T, _codec: Codec[T]) extends RedisCommandADT[Long]

  case class HSTRLEN(key: String, field: String) extends RedisCommandADT[Long]

  case class HVALS[T](key: String, _codec: Codec[T]) extends RedisCommandADT[Seq[T]]

  //
  // keys
  //

  case class DEL(keys: Seq[String]) extends RedisCommandADT[Long]

  case class DUMP(key: String) extends RedisCommandADT[Array[Byte]]

  case class EXISTS(key: String) extends RedisCommandADT[Boolean]

  case class EXPIRE(key: String, seconds: Long) extends RedisCommandADT[Boolean]

  case class EXPIREAT(key: String, unixTime: Long) extends RedisCommandADT[Boolean]

  case class KEYS(pattern: String) extends RedisCommandADT[Set[String]]

  // case class MIGRATE

  case class MOVE(key: String, dbIndex: Int) extends RedisCommandADT[Boolean]

  // case class OBJECT

  case class PERSIST(key: String) extends RedisCommandADT[Boolean]

  case class PEXPIRE(key: String, milliseconds: Long) extends RedisCommandADT[Long]

  case class PEXPIREAT(key: String, millisecondsUnixTime: Long) extends RedisCommandADT[Long]

  case class PTTL(key: String) extends RedisCommandADT[Long]

  case object RANDOMKEY extends RedisCommandADT[String]

  case class RENAME(oldKey: String, newKey: String) extends RedisCommandADT[String]

  case class RENAMENX(oldKey: String, newKey: String) extends RedisCommandADT[Boolean]

  case class RESTORE(key: String, milliseconds: Long, data: Array[Byte]) extends RedisCommandADT[String]

  // case class SCAN

  // case class SORT

  case class TTL(key: String) extends RedisCommandADT[Long]

  // type is reserved word ...
  case class VALUETYPE(key: String) extends RedisCommandADT[String]

  // case class WAIT
}
