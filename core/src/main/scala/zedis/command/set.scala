package zedis
package command

import scalaz.Free
import zedis.adt.CommandADT, CommandADT._
import zedis.util.Codec

object set extends SetCommand

trait SetCommand {

  def sadd[T: Codec](key: String, value: T, values: T*): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](SADD(key, nel(value, values), Codec[T]))

  def scard(key: String): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](SCARD(key))

  def sdiff[T: Codec](key: String, keys: String*): RedisCommand[Set[T]] =
    Free.liftF[CommandADT, Set[T]](SDIFF(nel(key, keys), Codec[T]))

  def sdiffstore(destination: String, key: String, keys: String*): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](SDIFFSTORE(destination, nel(key, keys)))

  def sinter[T: Codec](key: String, keys: String*): RedisCommand[Set[T]] =
    Free.liftF[CommandADT, Set[T]](SINTER(nel(key, keys), Codec[T]))

  def sinterstore(destination: String, key: String, keys: String*): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](SINTERSTORE(destination, nel(key, keys)))

  def sismember[T: Codec](key: String, member: T): RedisCommand[Boolean] =
    Free.liftF[CommandADT, Boolean](SISMEMBER(key, member, Codec[T]))

  def smembers[T: Codec](key: String): RedisCommand[Set[T]] =
    Free.liftF[CommandADT, Set[T]](SMEMBERS(key, Codec[T]))

  def smove[T: Codec](source: String, destination: String, member: T): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](SMOVE(source, destination, member, Codec[T]))

  def spop[T: Codec](key: String, count: Long = 1L): RedisCommand[Set[T]] =
    Free.liftF[CommandADT, Set[T]](SPOP(key, count, Codec[T]))

  def srandmember[T: Codec](key: String, count: Long = 1L): RedisCommand[Seq[T]] =
    Free.liftF[CommandADT, Seq[T]](SRANDMEMBER(key, count, Codec[T]))

  def srem[T: Codec](key: String, member: T, members: T*): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](SREM(key, nel(member, members), Codec[T]))

  // def sscan

  def sunion[T: Codec](key: String, keys: String*): RedisCommand[Set[T]] =
    Free.liftF[CommandADT, Set[T]](SUNION(nel(key, keys), Codec[T]))

  def sunionstore(destination: String, key: String, keys: String*): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](SUNIONSTORE(destination, nel(key, keys)))
}
