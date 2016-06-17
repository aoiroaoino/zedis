package zedis
package command

import scalaz.Free
import zedis.adt.CommandADT, CommandADT._
import zedis.util.Codec

object string extends StringCommand

trait StringCommand {

  def append[T: Codec](key: String, value: T): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](APPEND(key, value, Codec[T]))

  // def bitcount

  // def bitfield

  // def bitop

  // def bitpos

  def decr(key: String): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](DECR(key))

  def decrby(key: String, value: Long): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](DECRBY(key, value))

  def get[T: Codec](key: String): RedisCommand[Option[T]] =
    Free.liftF[CommandADT, Option[T]](GET(key, Codec[T]))

  // def getbit

  def getrange(key: String, start: Long, end: Long): RedisCommand[String] =
    Free.liftF[CommandADT, String](GETRANGE(key, start, end))

  def getset[T: Codec](key: String, value: T): RedisCommand[T] =
    Free.liftF[CommandADT, T](GETSET(key, value, Codec[T]))

  def incr(key: String): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](INCR(key))

  def incrby(key: String, value: Long): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](INCRBY(key, value))

  def incrbyfloat(key: String, value: Double): RedisCommand[Double] =
    Free.liftF[CommandADT, Double](INCRBYFLOAT(key, value))

  def mget[T: Codec](key: String, keys: String*): RedisCommand[Seq[Option[T]]] =
    Free.liftF[CommandADT, Seq[Option[T]]](MGET(nel(key, keys), Codec[T]))

  def mset[T: Codec](map: Map[String, T]): RedisCommand[String] =
    Free.liftF[CommandADT, String](MSET(map, Codec[T]))

  def msetnx[T: Codec](map: Map[String, T]): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](MSETNX(map, Codec[T]))

  def psetex[T: Codec](key: String, milliseconds: Long, value: T): RedisCommand[String] =
    Free.liftF[CommandADT, String](PSETEX(key, milliseconds, value, Codec[T]))

  def set[T: Codec](key: String, value: T): RedisCommand[String] =
    Free.liftF[CommandADT, String](SET(key, value, Codec[T]))

  // def setbit

  def setex[T: Codec](key: String, seconds: Long, value: T): RedisCommand[String] =
    Free.liftF[CommandADT, String](SETEX(key, seconds, value, Codec[T]))

  def setnx[T: Codec](key: String, value: T): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](SETNX(key, value, Codec[T]))

  def setrange[T: Codec](key: String, offset: Long, value: T): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](SETRANGE(key, offset, value, Codec[T]))

  def strlen(key: String): RedisCommand[Long] =
    Free.liftF[CommandADT, Long](STRLEN(key))
}
