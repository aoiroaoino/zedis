package zedis

import zedis.commands.{JedisCommand, PipelineCommand}
import zedis.util.DateTimeKey

trait ZedisClient
  extends JedisCommand
  with    PipelineCommand
  with    DateTimeKey
{
  protected def accessor(): Accessor
}
