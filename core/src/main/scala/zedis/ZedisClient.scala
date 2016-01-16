package zedis

import zedis.commands.{Command, PipelineCommand}

trait ZedisClient
  extends Command
  with    PipelineCommand
