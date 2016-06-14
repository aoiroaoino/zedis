package zedis
package command

import scalaz.Free
import zedis.adt._

trait KeyCommand {

  def del(keys: String*): RedisCommand[Long] =
    Free.liftF[RedisCommandADT, Long](DEL(keys))

  def dump(key: String): RedisCommand[Array[Byte]] =
    Free.liftF[RedisCommandADT, Array[Byte]](DUMP(key))

  def exists(key: String): RedisCommand[Boolean] =
    Free.liftF[RedisCommandADT, Boolean](EXISTS(key))

  def expire(key: String, seconds: Long): RedisCommand[Boolean] =
    Free.liftF[RedisCommandADT, Boolean](EXPIRE(key, seconds))

  def expireat(key: String, unixTime: Long): RedisCommand[Boolean] =
    Free.liftF[RedisCommandADT, Boolean](EXPIREAT(key, unixTime))

  def keys(pattern: String): RedisCommand[Set[String]] =
    Free.liftF[RedisCommandADT, Set[String]](KEYS(pattern))

  // def migrate

  def move(key: String, dbIndex: Int): RedisCommand[Boolean] =
    Free.liftF[RedisCommandADT, Boolean](MOVE(key, dbIndex))

  // def object

  def persist(key: String): RedisCommand[Boolean] =
    Free.liftF[RedisCommandADT, Boolean](PERSIST(key))

  def pexpire(key: String, milliseconds: Long): RedisCommand[Long] =
    Free.liftF[RedisCommandADT, Long](PEXPIRE(key, milliseconds))

  def pexpireat(key: String, millisecondsUnixTime: Long): RedisCommand[Long] =
    Free.liftF[RedisCommandADT, Long](PEXPIREAT(key, millisecondsUnixTime))

  def pttl(key: String): RedisCommand[Long] =
    Free.liftF[RedisCommandADT, Long](PTTL(key))

  def randomkey: RedisCommand[String] =
    Free.liftF[RedisCommandADT, String](RANDOMKEY)

  def rename(oldKey: String, newKey: String): RedisCommand[String] =
    Free.liftF[RedisCommandADT, String](RENAME(oldKey, newKey))

  def renamenx(oldKey: String, newKey: String): RedisCommand[Boolean] =
    Free.liftF[RedisCommandADT, Boolean](RENAMENX(oldKey, newKey))

  def restore(key: String, milliseconds: Long, data: Array[Byte]): RedisCommand[String] =
    Free.liftF[RedisCommandADT, String](RESTORE(key, milliseconds, data))

  // def scan

  // def sort

  def ttl(key: String): RedisCommand[Long] =
    Free.liftF[RedisCommandADT, Long](TTL(key))

  // type is reserved word ...
  def valuetype(key: String): RedisCommand[String] =
    Free.liftF[RedisCommandADT, String](VALUETYPE(key))

  // def wait
}
