package zedis
package commands

import java.lang.{Long => JLong}
import java.util.{Map => JMap}

import scalaz._
import redis.clients.jedis.{Pipeline, Response}

trait PipelineCommand {

  def $get(key: String): Pipeline =?> Response[String] =
    kleisliOpt{ _.get(key) }

  def $hget(key: String, field: String): Pipeline =?> Response[String] =
    kleisliOpt{ _.hget(key, field) }

  def $hset(key: String, field: String, value: String): Pipeline =?> Response[JLong] =
    kleisliOpt{ _.hset(key, field, value) }

  def $hgetAll(key: String): Pipeline =?> Response[JMap[String, String]] =
    kleisliOpt{ _.hgetAll(key) }

  def $hincrBy(key: String, field: String, value: Long): Pipeline =?> Response[JLong] =
    kleisliOpt{ _.hincrBy(key, field, value) }

  def $expire(key: String, seconds: Int): Pipeline =?> Response[JLong] =
    kleisliOpt{ _.expire(key, seconds) }

  def $sync(): Pipeline =?> Unit =
    kleisliOpt{ _.sync() }

}
