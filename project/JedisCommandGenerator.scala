import sbt._

object JedisCommandGenerator {

  case class Command(returnType: String, methodName: String, args: (String, String)*) {

    val argsStr  = args.map{ case (n, t) => s"$n: $t" }.mkString(", ")
    val argsKeys = args.map{ case (n, t) => if (t.endsWith("*")) n + ": _*" else n }.mkString(", ")

    def method: String =
      s"""|def $methodName($argsStr): Jedis =!> $returnType =
          |  Command{ _.$methodName($argsKeys) }
          |""".stripMargin
  }

  lazy val template =
    s"""|package zedis
        |package commands
        |
        |import scala.collection.JavaConversions._
        |
        |import java.util.{Map => JMap, Set => JSet, List => JList}
        |
        |import redis.clients.jedis.Jedis
        |
        |trait JedisCommand {
        |
        |${methods(indent = 2)}
        |}
        |""".stripLineEnd.stripMargin

  def methods(indent: Int): String =
    allCommands.map {command =>
      command.method.split("\n").map(" " * indent + _).mkString("\n") + "\n"
    }.mkString("\n")

  lazy val allCommands = Seq(
    Command("Unit",                "close"),
    Command("Long",                "decr",         "key"     -> "String"),
    Command("Long",                "decrBy",       "key"     -> "String",  "integer"  -> "Long"),
    Command("Long",                "del",          "keys"    -> "String*"),
    Command("Long",                "del",          "key"     -> "String"),
    Command("String",              "echo",         "string"  -> "String"),
    Command("Boolean",             "exists",       "key"     -> "String"),
    Command("Long",                "expire",       "key"     -> "String",  "seconds"  -> "Int"),
    Command("Long",                "expireAt",     "key"     -> "String",  "unixTime" -> "Long"),
    Command("String",              "get",          "key"     -> "String"),
    Command("String",              "getSet",       "key"     -> "String",  "value"    -> "String"),
    Command("Long",                "hdel",         "key"     -> "String",  "fields"   -> "String*"),
    Command("Boolean",             "hexists",      "key"     -> "String",  "field"    -> "String"),
    Command("String",              "hget",         "key"     -> "String",  "field"    -> "String"),
    Command("JMap[String, String]", "hgetAll",      "key"     -> "String"),
    Command("Long",                "hincrBy",      "key"     -> "String",  "field"    -> "String",  "value" -> "Long"),
    Command("Double",              "hincrByFloat", "key"     -> "String",  "field"    -> "String",  "value" -> "Double"),
    Command("JSet[String]",         "hkeys",        "key"     -> "String"),
    Command("Long",                "hlen",         "key"     -> "String"),
    Command("JList[String]",        "hmget",        "key"     -> "String",  "fields"   -> "String*"),
    Command("String",              "hmset",        "key"     -> "String",  "hash"     -> "Map[String, String]"),
    Command("Long",                "hset",         "key"     -> "String",  "field"    -> "String",  "value" -> "String"),
    Command("Long",                "hsetnx",       "key"     -> "String",  "field"    -> "String",  "value" -> "String"),
    Command("JList[String]",        "hvals",        "key"     -> "String"),
    Command("Long",                "incr",         "key"     -> "String"),
    Command("Long",                "incrBy",       "key"     -> "String",  "integer"  -> "Long"),
    Command("Double",              "incrByFloat",  "key"     -> "String",  "value"    -> "Double"),
    Command("JSet[String]",         "keys",         "pattern" -> "String"),
    Command("String",              "lindex",       "key"     -> "String",  "index"    -> "Long"),
    Command("Long",                "llen",         "key"     -> "String"),
    Command("String",              "lpop",         "key"     -> "String"),
    Command("Long",                "lpush",        "key"     -> "String",  "strings"  -> "String*"),
    Command("Long",                "lpushx",       "key"     -> "String",  "string"   -> "String*"),
    Command("JList[String]",        "lrange",       "key"     -> "String",  "start"    -> "Long",    "end"   -> "Long"),
    Command("Long",                "lrem",         "key"     -> "String",  "count"    -> "Long",    "value" -> "String"),
    Command("String",              "lset",         "key"     -> "String",  "index"    -> "Long",    "value" -> "String"),
    Command("String",              "ltrim",        "key"     -> "String",  "start"    -> "Long",    "end"   -> "Long"),
    Command("JList[String]",        "mget",         "keys"    -> "String*"),
    Command("Long",                "persist",      "key"     -> "String"),
    Command("String",              "randomKey"),
    Command("String",              "rename",       "oldkey"  -> "String",  "newkey"   -> "String"),
    Command("String",              "restore",      "key"     -> "String",  "ttl"      -> "Int",     "serializedValue" -> "Array[Byte]"),
    Command("String",              "rpop",         "key"     -> "String"),
    Command("String",              "rpoplpush",    "srckey"  -> "String",  "dstkey"   -> "String"),
    Command("Long",                "rpush",        "key"     -> "String",  "strings"  -> "String*"),
    Command("Long",                "rpushx",       "key"     -> "String",  "string"   -> "String*"),
    Command("String",              "set",          "key"     -> "String",  "value"    -> "String"),
    Command("String",              "set",          "key"     -> "String",  "value"    -> "String",  "nxxx" -> "String"),
    Command("String",              "set",          "key"     -> "String",  "value"    -> "String",  "nxxx" -> "String", "expx" -> "String", "time" -> "Int"),
    Command("String",              "set",          "key"     -> "String",  "value"    -> "String",  "nxxx" -> "String", "expx" -> "String", "time" -> "Long"),
    Command("Long",                "ttl",          "key"     -> "String")
  )
}
