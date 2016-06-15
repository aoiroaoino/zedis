package zedis
package command

import scalaz.Free
import zedis.adt._

trait HasheCommand {

  def hdel(key: String, fields: String*): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](HDEL(key, fields))

  def hexists(key: String, field: String): RedisCommand[Boolean] =
    Free.liftF[CommandADT, Boolean](HEXISTS(key, field))

  def hget[T: Codec](key: String, field: String): RedisCommand[Option[T]] =
    Free.liftF[CommandADT, Option[T]](HGET[T](key, field, Codec[T]))

  def hgetall[T: Codec](key: String): RedisCommand[Map[String, T]] =
    Free.liftF[CommandADT, Map[String, T]](HGETALL(key, Codec[T]))

  def hincrby(key: String, field: String, value: Long): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](HINCRBY(key, field, value))

  def hincrbyfloat(key: String, field: String, value: Double): RedisCommand[Double] =
    Free.liftF[CommandADT, Double](HINCRBYFLOAT(key, field, value))

  def hkeys(key: String): RedisCommand[Set[String]] =
    Free.liftF[CommandADT, Set[String]](HKEYS(key))

  def hlen(key: String): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](HLEN(key))

  def hmget[T: Codec](key: String, fields: String*): RedisCommand[Seq[Option[T]]] =
    Free.liftF[CommandADT, Seq[Option[T]]](HMGET(key, fields, Codec[T]))

  def hmset[T: Codec](key: String, map: Map[String, T]): RedisCommand[Boolean] =
    Free.liftF[CommandADT, Boolean](HMSET(key, map, Codec[T]))

  // def hscan

  def hset[T: Codec](key: String, field: String, value: T): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](HSET(key, field, value, Codec[T]))

  def hsetnx[T: Codec](key: String, field: String, value: T): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](HSETNX(key, field, value, Codec[T]))

  def hstrlen(key: String, field: String): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](HSTRLEN(key, field))

  def hvals[T: Codec](key: String): RedisCommand[Seq[T]] =
    Free.liftF[CommandADT, Seq[T]](HVALS(key, Codec[T]))
}
