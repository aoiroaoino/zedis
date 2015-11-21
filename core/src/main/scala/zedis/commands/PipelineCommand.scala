package zedis
package commands

import java.lang.{Long => JLong}
import java.util.{Map => JMap}

import redis.clients.jedis.{Pipeline, Response}

trait PipelineCommand {

  def $get(key: String): Pipeline =!> Response[String] =
    Command{ _.get(key) }

  def $hget(key: String, field: String): Pipeline =!> Response[String] =
    Command{ _.hget(key, field) }

  def $hgetAll(key: String): Pipeline =!> Response[JMap[String, String]] =
    Command{ _.hgetAll(key) }

  def $hincrBy(key: String, field: String, value: Long): Pipeline =!> Response[JLong] =
    Command{ _.hincrBy(key, field, value) }

  def $expire(key: String, seconds: Int): Pipeline =!> Response[JLong] =
    Command{ _.expire(key, seconds) }

  def $sync(): Pipeline =!> Unit =
    Command{ _.sync() }

}
