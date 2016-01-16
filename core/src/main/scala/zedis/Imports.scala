package zedis

import scalaz.std.OptionInstances

object Imports
  extends OptionInstances
  with    zedis.syntax.AllSyntaxes
