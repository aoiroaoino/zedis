package zedis

object adt {

  sealed trait RedisCommandADT[+A]

  // hashes

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

  // todo: value: T
  case class HSET[T](key: String, field: String, value: T, _codec: Codec[T]) extends RedisCommandADT[Long]

  case class HSETNX[T](key: String, field: String, value: T, _codec: Codec[T]) extends RedisCommandADT[Long]

  case class HSTRLEN(key: String, field: String) extends RedisCommandADT[Long]

  case class HVALS[T](key: String, _codec: Codec[T]) extends RedisCommandADT[Seq[T]]
}
