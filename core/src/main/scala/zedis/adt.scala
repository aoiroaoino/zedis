package zedis

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

  case class DEL(keys: Seq[String]) extends CommandADT[Long]

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
}
