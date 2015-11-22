package zedis

import zedis.accessor.Accessor
import zedis.commands.{JedisCommand, PipelineCommand}
import zedis.util.DateTimeKey

trait ZedisClient
  extends JedisCommand
  with    PipelineCommand
  with    DateTimeKey
{
  protected def accessor[A <: Accessor](): A
}
