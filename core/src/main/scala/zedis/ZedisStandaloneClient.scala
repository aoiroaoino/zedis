package zedis

import zedis.accessor.StandaloneAccessor
import zedis.commands.{JedisCommand, PipelineCommand}

trait ZedisStandaloneClient
  extends StandaloneAccessor
  with    JedisCommand
  with    PipelineCommand {

  protected[this] def accessor(): StandaloneAccessor
}
