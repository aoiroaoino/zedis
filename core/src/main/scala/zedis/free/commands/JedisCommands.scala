package zedis.free.commands

import scala.collection.JavaConversions

import java.util.{Map, Set, List}

import scalaz.{Free, Kleisli, Monad}
import redis.clients.jedis.Jedis

trait JedisCommands {

  trait JedisCommandOp[A] { self =>
    protected def lift[M[_]](f: Jedis => A)(implicit M: Monad[M]): Kleisli[M, Jedis, A] =
      Kleisli{ jedis => M.point[A](f(jedis)) }

    def command[M[_]: Monad]: Kleisli[M, Jedis, A]
  }

  object JedisCommandOp {

    case class JClose() extends JedisCommandOp[Unit] {
      def command[M[_]: Monad] = lift(_.close())
    }
    case class JDecr(key: String) extends JedisCommandOp[Long] {
      def command[M[_]: Monad] = lift(_.decr(key))
    }
    case class JDecrBy(key: String, integer: Long) extends JedisCommandOp[Long] {
      def command[M[_]: Monad] = lift(_.decrBy(key, integer))
    }
    case class JDel(keys: String*) extends JedisCommandOp[Long] {
      def command[M[_]: Monad] = lift(_.del(keys: _*))
    }
    case class JDel1(key: String) extends JedisCommandOp[Long] {
      def command[M[_]: Monad] = lift(_.del(key))
    }
    case class JEcho(string: String) extends JedisCommandOp[String] {
      def command[M[_]: Monad] = lift(_.echo(string))
    }
    case class JExists(key: String) extends JedisCommandOp[Boolean] {
      def command[M[_]: Monad] = lift(_.exists(key))
    }
    case class JExpire(key: String, seconds: Int) extends JedisCommandOp[Long] {
      def command[M[_]: Monad] = lift(_.expire(key, seconds))
    }
    case class JExpireAt(key: String, unixTime: Long) extends JedisCommandOp[Long] {
      def command[M[_]: Monad] = lift(_.expireAt(key, unixTime))
    }
    case class JGet(key: String) extends JedisCommandOp[String] {
      def command[M[_]: Monad] = lift(_.get(key))
    }
    case class JGetSet(key: String, value: String) extends JedisCommandOp[String] {
      def command[M[_]: Monad] = lift(_.getSet(key, value))
    }
    case class JHdel(key: String, fields: String*) extends JedisCommandOp[Long] {
      def command[M[_]: Monad] = lift(_.hdel(key, fields: _*))
    }
    case class JHexists(key: String, field: String) extends JedisCommandOp[Boolean] {
      def command[M[_]: Monad] = lift(_.hexists(key, field))
    }
    case class JHget(key: String, field: String) extends JedisCommandOp[String] {
      def command[M[_]: Monad] = lift(_.hget(key, field))
    }
    case class JHgetAll(key: String) extends JedisCommandOp[Map[String, String]] {
      def command[M[_]: Monad] = lift(_.hgetAll(key))
    }
    case class JHincrBy(key: String, field: String, value: Long) extends JedisCommandOp[Long] {
      def command[M[_]: Monad] = lift(_.hincrBy(key, field, value))
    }
    case class JHincrByFloat(key: String, field: String, value: Double) extends JedisCommandOp[Double] {
      def command[M[_]: Monad] = lift(_.hincrByFloat(key, field, value))
    }
    case class JHkeys(key: String) extends JedisCommandOp[Set[String]] {
      def command[M[_]: Monad] = lift(_.hkeys(key))
    }
    case class JHlen(key: String) extends JedisCommandOp[Long] {
      def command[M[_]: Monad] = lift(_.hlen(key))
    }
    case class JHmget(key: String, fields: String*) extends JedisCommandOp[List[String]] {
      def command[M[_]: Monad] = lift(_.hmget(key, fields: _*))
    }
    case class JHmset(key: String, hash: Map[String, String]) extends JedisCommandOp[String] {
      def command[M[_]: Monad] = lift(_.hmset(key, hash))
    }
    case class JHset(key: String, field: String, value: String) extends JedisCommandOp[Long] {
      def command[M[_]: Monad] = lift(_.hset(key, field, value))
    }
    case class JHsetnx(key: String, field: String, value: String) extends JedisCommandOp[Long] {
      def command[M[_]: Monad] = lift(_.hsetnx(key, field, value))
    }
    case class JHvals(key: String) extends JedisCommandOp[List[String]] {
      def command[M[_]: Monad] = lift(_.hvals(key))
    }
    case class JIncr(key: String) extends JedisCommandOp[Long] {
      def command[M[_]: Monad] = lift(_.incr(key))
    }
    case class JIncrBy(key: String, integer: Long) extends JedisCommandOp[Long] {
      def command[M[_]: Monad] = lift(_.incrBy(key, integer))
    }
    case class JIncrByFloat(key: String, value: Double) extends JedisCommandOp[Double] {
      def command[M[_]: Monad] = lift(_.incrByFloat(key, value))
    }
    case class JKeys(pattern: String) extends JedisCommandOp[Set[String]] {
      def command[M[_]: Monad] = lift(_.keys(pattern))
    }
    case class JLindex(key: String, index: Long) extends JedisCommandOp[String] {
      def command[M[_]: Monad] = lift(_.lindex(key, index))
    }
    case class JLlen(key: String) extends JedisCommandOp[Long] {
      def command[M[_]: Monad] = lift(_.llen(key))
    }
    case class JLpop(key: String) extends JedisCommandOp[String] {
      def command[M[_]: Monad] = lift(_.lpop(key))
    }
    case class JLpush(key: String, strings: String*) extends JedisCommandOp[Long] {
      def command[M[_]: Monad] = lift(_.lpush(key, strings: _*))
    }
    case class JLpushx(key: String, string: String*) extends JedisCommandOp[Long] {
      def command[M[_]: Monad] = lift(_.lpushx(key, string: _*))
    }
    case class JLrange(key: String, start: Long, end: Long) extends JedisCommandOp[List[String]] {
      def command[M[_]: Monad] = lift(_.lrange(key, start, end))
    }
    case class JLrem(key: String, count: Long, value: String) extends JedisCommandOp[Long] {
      def command[M[_]: Monad] = lift(_.lrem(key, count, value))
    }
    case class JLset(key: String, index: Long, value: String) extends JedisCommandOp[String] {
      def command[M[_]: Monad] = lift(_.lset(key, index, value))
    }
    case class JLtrim(key: String, start: Long, end: Long) extends JedisCommandOp[String] {
      def command[M[_]: Monad] = lift(_.ltrim(key, start, end))
    }
    case class JMget(keys: String*) extends JedisCommandOp[List[String]] {
      def command[M[_]: Monad] = lift(_.mget(keys: _*))
    }
    case class JPersist(key: String) extends JedisCommandOp[Long] {
      def command[M[_]: Monad] = lift(_.persist(key))
    }
    case class JRandomKey() extends JedisCommandOp[String] {
      def command[M[_]: Monad] = lift(_.randomKey())
    }
    case class JRename(oldkey: String, newkey: String) extends JedisCommandOp[String] {
      def command[M[_]: Monad] = lift(_.rename(oldkey, newkey))
    }
    case class JRestore(key: String, ttl: Int, serializedValue: Array[Byte]) extends JedisCommandOp[String] {
      def command[M[_]: Monad] = lift(_.restore(key, ttl, serializedValue))
    }
    case class JRpop(key: String) extends JedisCommandOp[String] {
      def command[M[_]: Monad] = lift(_.rpop(key))
    }
    case class JRpoplpush(srckey: String, dstkey: String) extends JedisCommandOp[String] {
      def command[M[_]: Monad] = lift(_.rpoplpush(srckey, dstkey))
    }
    case class JRpush(key: String, strings: String*) extends JedisCommandOp[Long] {
      def command[M[_]: Monad] = lift(_.rpush(key, strings: _*))
    }
    case class JRpushx(key: String, string: String*) extends JedisCommandOp[Long] {
      def command[M[_]: Monad] = lift(_.rpushx(key, string: _*))
    }
    case class JSet(key: String, value: String) extends JedisCommandOp[String] {
      def command[M[_]: Monad] = lift(_.set(key, value))
    }
    case class JSet1(key: String, value: String, nxxx: String) extends JedisCommandOp[String] {
      def command[M[_]: Monad] = lift(_.set(key, value, nxxx))
    }
    case class JSet2(key: String, value: String, nxxx: String, expx: String, time: Int) extends JedisCommandOp[String] {
      def command[M[_]: Monad] = lift(_.set(key, value, nxxx, expx, time))
    }
    case class JSet3(key: String, value: String, nxxx: String, expx: String, time: Long) extends JedisCommandOp[String] {
      def command[M[_]: Monad] = lift(_.set(key, value, nxxx, expx, time))
    }
    case class JTtl(key: String) extends JedisCommandOp[Long] {
      def command[M[_]: Monad] = lift(_.ttl(key))
    }
  }

  import JedisCommandOp._

  // smart constractors
  def close() =
    Free.liftF(JClose())

  def decr(key: String) =
    Free.liftF(JDecr(key))

  def decrBy(key: String, integer: Long) =
    Free.liftF(JDecrBy(key, integer))

  def del(keys: String*) =
    Free.liftF(JDel(keys: _*))

  def del(key: String) =
    Free.liftF(JDel1(key))

  def echo(string: String) =
    Free.liftF(JEcho(string))

  def exists(key: String) =
    Free.liftF(JExists(key))

  def expire(key: String, seconds: Int) =
    Free.liftF(JExpire(key, seconds))

  def expireAt(key: String, unixTime: Long) =
    Free.liftF(JExpireAt(key, unixTime))

  def get(key: String) =
    Free.liftF(JGet(key))

  def getSet(key: String, value: String) =
    Free.liftF(JGetSet(key, value))

  def hdel(key: String, fields: String*) =
    Free.liftF(JHdel(key, fields: _*))

  def hexists(key: String, field: String) =
    Free.liftF(JHexists(key, field))

  def hget(key: String, field: String) =
    Free.liftF(JHget(key, field))

  def hgetAll(key: String) =
    Free.liftF(JHgetAll(key))

  def hincrBy(key: String, field: String, value: Long) =
    Free.liftF(JHincrBy(key, field, value))

  def hincrByFloat(key: String, field: String, value: Double) =
    Free.liftF(JHincrByFloat(key, field, value))

  def hkeys(key: String) =
    Free.liftF(JHkeys(key))

  def hlen(key: String) =
    Free.liftF(JHlen(key))

  def hmget(key: String, fields: String*) =
    Free.liftF(JHmget(key, fields: _*))

  def hmset(key: String, hash: Map[String, String]) =
    Free.liftF(JHmset(key, hash))

  def hset(key: String, field: String, value: String) =
    Free.liftF(JHset(key, field, value))

  def hsetnx(key: String, field: String, value: String) =
    Free.liftF(JHsetnx(key, field, value))

  def hvals(key: String) =
    Free.liftF(JHvals(key))

  def incr(key: String) =
    Free.liftF(JIncr(key))

  def incrBy(key: String, integer: Long) =
    Free.liftF(JIncrBy(key, integer))

  def incrByFloat(key: String, value: Double) =
    Free.liftF(JIncrByFloat(key, value))

  def keys(pattern: String) =
    Free.liftF(JKeys(pattern))

  def lindex(key: String, index: Long) =
    Free.liftF(JLindex(key, index))

  def llen(key: String) =
    Free.liftF(JLlen(key))

  def lpop(key: String) =
    Free.liftF(JLpop(key))

  def lpush(key: String, strings: String*) =
    Free.liftF(JLpush(key, strings: _*))

  def lpushx(key: String, string: String*) =
    Free.liftF(JLpushx(key, string: _*))

  def lrange(key: String, start: Long, end: Long) =
    Free.liftF(JLrange(key, start, end))

  def lrem(key: String, count: Long, value: String) =
    Free.liftF(JLrem(key, count, value))

  def lset(key: String, index: Long, value: String) =
    Free.liftF(JLset(key, index, value))

  def ltrim(key: String, start: Long, end: Long) =
    Free.liftF(JLtrim(key, start, end))

  def mget(keys: String*) =
    Free.liftF(JMget(keys: _*))

  def persist(key: String) =
    Free.liftF(JPersist(key))

  def randomKey() =
    Free.liftF(JRandomKey())

  def rename(oldkey: String, newkey: String) =
    Free.liftF(JRename(oldkey, newkey))

  def restore(key: String, ttl: Int, serializedValue: Array[Byte]) =
    Free.liftF(JRestore(key, ttl, serializedValue))

  def rpop(key: String) =
    Free.liftF(JRpop(key))

  def rpoplpush(srckey: String, dstkey: String) =
    Free.liftF(JRpoplpush(srckey, dstkey))

  def rpush(key: String, strings: String*) =
    Free.liftF(JRpush(key, strings: _*))

  def rpushx(key: String, string: String*) =
    Free.liftF(JRpushx(key, string: _*))

  def set(key: String, value: String) =
    Free.liftF(JSet(key, value))

  def set(key: String, value: String, nxxx: String) =
    Free.liftF(JSet1(key, value, nxxx))

  def set(key: String, value: String, nxxx: String, expx: String, time: Int) =
    Free.liftF(JSet2(key, value, nxxx, expx, time))

  def set(key: String, value: String, nxxx: String, expx: String, time: Long) =
    Free.liftF(JSet3(key, value, nxxx, expx, time))

  def ttl(key: String) =
    Free.liftF(JTtl(key))

}