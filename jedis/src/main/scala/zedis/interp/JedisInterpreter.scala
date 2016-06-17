package zedis
package interp

import scala.collection.JavaConverters._
import scala.concurrent.{Future, ExecutionContext}

import scalaz.{~>, Id}
import scalaz.Id.Id
import redis.clients.jedis.Jedis

import zedis.adt.CommandADT, CommandADT._
import zedis.util.Wrapper

class JedisInterpreter[F[_]: Wrapper](connection: Jedis) extends Interpreter[F] {

  override def connectionProc[A] = {
    case AUTH(password) =>
      wrapF(connection.auth(password))
    case ECHO(msg) =>
      wrapF(connection.echo(msg))
    case PING(_) =>
      wrapF(connection.ping())
    case QUIT =>
      wrapF(connection.quit())
    case SELECT(index) =>
      wrapF(connection.select(index))
  }

  override def hashesProc[A] = {
    case HDEL(key, fields) =>
      wrapF(connection.hdel(key, fields: _*))
    case HEXISTS(key, field) =>
      wrapF(connection.hexists(key, field))
    case HGET(key, field, codec) =>
      wrapF(Option(connection.hget(key, field)).map(codec.decode _))
    case HGETALL(key, codec) =>
      wrapF(connection.hgetAll(key).asScala.toMap.mapValues(codec.decode _))
    case HINCRBY(key, field, value) =>
      wrapF(connection.hincrBy(key, field, value))
    case HINCRBYFLOAT(key, field, value) =>
      wrapF(connection.hincrByFloat(key, field, value))
    case HKEYS(key) =>
      wrapF(connection.hkeys(key).asScala.toSet)
    case HLEN(key) =>
      wrapF(connection.hlen(key))
    case HMGET(key, fields, codec) =>
      wrapF(connection.hmget(key, fields: _*).asScala.map(Option(_).map(codec.decode _)))
    case HMSET(key, map, codec) =>
      wrapF(connection.hmset(key.toString, map.mapValues(codec.encode).asJava) == "OK")
    // case class HSCAN =>
    //   wrapF(connection.
    case HSET(key, field, value, codec) =>
      wrapF(connection.hset(key, field, codec.encode(value)))
    case HSETNX(key, field, value, codec) =>
      wrapF(connection.hsetnx(key, field, codec.encode(value)))
    // case HSTRLEN(key, field, codec) =>
    //   wrapF(connection.
    case HVALS(key, codec) =>
      wrapF(connection.hvals(key).asScala.map(codec.decode _))
  }

  override def keysProc[A] = ???

  override def listsProc[A] = ???

  override def setsProc[A] = ???

  override def stringsProc[A] = ???

  override def unsupportedError[A] = {
    case other => sys.error(s"$other is unsupported command in Jedis connection.")
  }
}

object JedisInterpreter {

  def apply[F[_]: Wrapper](connection: Jedis): CommandADT ~> F = new JedisInterpreter[F](connection).impl

  def id(connection: Jedis): CommandADT ~> Id = apply[Id](connection)

  def scalaFuture(connection: Jedis)(implicit ec: ExecutionContext): CommandADT ~> Future = apply[Future](connection)
}
