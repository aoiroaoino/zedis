import sbt._

object PipelineCommandGenerator {

  case class Command(returnType: String, methodName: String, args: (String, String)*) {

    val argsStr  = args.map{ case (n, t) => s"$n: $t" }.mkString(", ")
    val argsKeys = args.map{ case (n, t) => if (t.endsWith("*")) n + ": _*" else n }.mkString(", ")

    def method: String =
      s"""|def $$$methodName($argsStr): Pipeline =?> $returnType =
          |  kleisliOpt{ _.$methodName($argsKeys) }
          |""".stripMargin
  }

  lazy val template =
    s"""|package zedis
        |package commands
        |
        |import java.lang.{Long => JLong}
        |import java.util.{Map => JMap}
        |
        |import scalaz._
        |import redis.clients.jedis.{Pipeline, Response}
        |
        |trait PipelineCommand {
        |
        |${methods(indent = 2)}
        |}
        |""".stripLineEnd.stripMargin

  def methods(indent: Int): String =
    allCommands.map {command =>
      command.method.split("\n").map(" " * indent + _).mkString("\n") + "\n"
    }.mkString("\n")

  lazy val allCommands = Seq(
    Command("Response[String]", "get", "key" -> "String"),
    Command("Response[String]", "hget", "key" -> "String", "field" -> "String"),
    Command("Response[JLong]", "hset", "key" -> "String",  "field" -> "String", "value" -> "String"),
    Command("Response[JMap[String, String]]", "hgetAll", "key" -> "String"),
    Command("Response[JLong]", "hincrBy", "key" -> "String", "field" -> "String", "value" -> "Long"),
    Command("Response[JLong]", "expire", "key" -> "String", "seconds" -> "Int"),
    Command("Response[JLong]", "pfadd", "key" -> "String", "elements" -> "String*"),
    Command("Response[JLong]", "pfcount", "keys" -> "String*"),
    Command("Response[String]", "pfmerge", "destKey" -> "String", "sourceKeys" -> "String*"),
    Command("Unit", "sync")
  )
}
