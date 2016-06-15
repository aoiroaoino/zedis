package zedis
package command

import scalaz.Free
import zedis.adt._

object list extends ListCommand {
  // for linsert
  sealed abstract class Position(val show: String)
  case object Before extends Position("before")
  case object After extends Position("after")
}

trait ListCommand {
  import list._

  // def blpop

  // def brpop

  // def brpoplpush

  def lindex[T: Codec](key: String, index: Long): RedisCommand[Option[T]] =
    Free.liftF[CommandADT, Option[T]](LINDEX(key, index, Codec[T]))

  def linsert[T: Codec](key: String, position: Position, pivot: T, value: T): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](LINSERT(key, position.show, pivot, value, Codec[T]))

  def llen(key: String): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](LLEN(key))

  def lpop[T: Codec](key: String): RedisCommand[Option[T]] =
    Free.liftF[CommandADT, Option[T]](LPOP(key, Codec[T]))

  def lpush[T: Codec](key: String, values: T*): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](LPUSH(key, values, Codec[T]))

  def lpushx[T: Codec](key: String, values: T*): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](LPUSHX(key, values, Codec[T]))

  def lrange[T: Codec](key: String, start: Long, end: Long): RedisCommand[Seq[T]] =
    Free.liftF[CommandADT, Seq[T]](LRANGE(key, start, end, Codec[T]))

  def lrem(key: String, count: Long, value: String): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](LREM(key, count, value))

  def lset[T: Codec](key: String, index: Long, value: T): RedisCommand[String] =
    Free.liftF[CommandADT, String](LSET(key, index, value, Codec[T]))

  def ltrim(key: String, start: Long, stop: Long): RedisCommand[String] =
    Free.liftF[CommandADT, String](LTRIM(key, start, stop))

  def rpop[T: Codec](key: String): RedisCommand[Option[T]] =
    Free.liftF[CommandADT, Option[T]](RPOP(key, Codec[T]))

  def rpoplpush[T: Codec](source: String, destination: String): RedisCommand[T] =
    Free.liftF[CommandADT, T](RPOPLPUSH(source, destination, Codec[T]))

  def rpush[T: Codec](key: String, value: T*): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](RPUSH(key, value, Codec[T]))

  def rpushx[T: Codec](key: String, value: T): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](RPUSHX(key, value, Codec[T]))
}
