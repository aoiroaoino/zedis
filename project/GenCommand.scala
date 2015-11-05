import sbt._

object FreeCommandGenerator {

  lazy val template =
    s"""|package zedis.free.commands
        |
        |import scala.collection.JavaConversions
        |
        |import java.util.{Map, Set, List}
        |
        |import scalaz.{Free, Kleisli, Monad}
        |import redis.clients.jedis.Jedis
        |
        |trait JedisCommands {
        |
        |  trait JedisCommandOp[A] { self =>
        |    protected def lift[M[_]](f: Jedis => A)(implicit M: Monad[M]): Kleisli[M, Jedis, A] =
        |      Kleisli{ jedis => M.point[A](f(jedis)) }
        |
        |    def command[M[_]: Monad]: Kleisli[M, Jedis, A]
        |  }
        |
        |  object JedisCommandOp {
        |
        |${classDefs(4)}
        |  }
        |
        |  import JedisCommandOp._
        |
        |  // smart constractors
        |${smartConstructors(2)}
        |}""".stripMargin

  case class JedisCommand(index: Int, returnType: String, method: String, args: (String, String)*) {

    val className = if (index == 0) s"${method.capitalize}" else s"${method.capitalize}$index"
    val argsStr   = args.map{ case (n, t) => s"$n: $t" }.mkString(", ")
    val argsKeys  = args.map{ case (k, v) => if (v.endsWith("*")) k + ": _*" else k }.mkString(", ")

    def classDef: String =
      s"""|case class J$className($argsStr) extends JedisCommandOp[$returnType] {
          |  def command[M[_]: Monad] = lift(_.$method($argsKeys))
          |}
          |""".stripMargin

    def smartConstructor: String =
      s"""|def $method($argsStr) =
          |  Free.liftF(J$className($argsKeys))
          |""".stripMargin
  }

  def classDefs(indent: Int): String =
    allCommands.map {s =>
      s.classDef.split("\n").map(" " * indent + _).mkString("\n")
    }.mkString("\n")

  def smartConstructors(indent: Int): String =
    allCommands.map {s =>
      s.smartConstructor.split("\n").map(" " * indent + _).mkString("\n") + "\n"
    }.mkString("\n")

  lazy val allCommands = Seq(
    JedisCommand(0, "Unit",                "close"),
    JedisCommand(0, "Long",                "decr",         "key"     -> "String"),
    JedisCommand(0, "Long",                "decrBy",       "key"     -> "String",  "integer"  -> "Long"),
    JedisCommand(0, "Long",                "del",          "keys"    -> "String*"),
    JedisCommand(1, "Long",                "del",          "key"     -> "String"),
    JedisCommand(0, "String",              "echo",         "string"  -> "String"),
    JedisCommand(0, "Boolean",             "exists",       "key"     -> "String"),
    JedisCommand(0, "Long",                "expire",       "key"     -> "String",  "seconds"  -> "Int"),
    JedisCommand(0, "Long",                "expireAt",     "key"     -> "String",  "unixTime" -> "Long"),
    JedisCommand(0, "String",              "get",          "key"     -> "String"),
    JedisCommand(0, "String",              "getSet",       "key"     -> "String",  "value"    -> "String"),
    JedisCommand(0, "Long",                "hdel",         "key"     -> "String",  "fields"   -> "String*"),
    JedisCommand(0, "Boolean",             "hexists",      "key"     -> "String",  "field"    -> "String"),
    JedisCommand(0, "String",              "hget",         "key"     -> "String",  "field"    -> "String"),
    JedisCommand(0, "Map[String, String]", "hgetAll",      "key"     -> "String"),
    JedisCommand(0, "Long",                "hincrBy",      "key"     -> "String",  "field"    -> "String",  "value" -> "Long"),
    JedisCommand(0, "Double",              "hincrByFloat", "key"     -> "String",  "field"    -> "String",  "value" -> "Double"),
    JedisCommand(0, "Set[String]",         "hkeys",        "key"     -> "String"),
    JedisCommand(0, "Long",                "hlen",         "key"     -> "String"),
    JedisCommand(0, "List[String]",        "hmget",        "key"     -> "String",  "fields"   -> "String*"),
    JedisCommand(0, "String",              "hmset",        "key"     -> "String",  "hash"     -> "Map[String, String]"),
    JedisCommand(0, "Long",                "hset",         "key"     -> "String",  "field"    -> "String",  "value" -> "String"),
    JedisCommand(0, "Long",                "hsetnx",       "key"     -> "String",  "field"    -> "String",  "value" -> "String"),
    JedisCommand(0, "List[String]",        "hvals",        "key"     -> "String"),
    JedisCommand(0, "Long",                "incr",         "key"     -> "String"),
    JedisCommand(0, "Long",                "incrBy",       "key"     -> "String",  "integer"  -> "Long"),
    JedisCommand(0, "Double",              "incrByFloat",  "key"     -> "String",  "value"    -> "Double"),
    JedisCommand(0, "Set[String]",         "keys",         "pattern" -> "String"),
    JedisCommand(0, "String",              "lindex",       "key"     -> "String",  "index"    -> "Long"),
    JedisCommand(0, "Long",                "llen",         "key"     -> "String"),
    JedisCommand(0, "String",              "lpop",         "key"     -> "String"),
    JedisCommand(0, "Long",                "lpush",        "key"     -> "String",  "strings"  -> "String*"),
    JedisCommand(0, "Long",                "lpushx",       "key"     -> "String",  "string"   -> "String*"),
    JedisCommand(0, "List[String]",        "lrange",       "key"     -> "String",  "start"    -> "Long",    "end"   -> "Long"),
    JedisCommand(0, "Long",                "lrem",         "key"     -> "String",  "count"    -> "Long",    "value" -> "String"),
    JedisCommand(0, "String",              "lset",         "key"     -> "String",  "index"    -> "Long",    "value" -> "String"),
    JedisCommand(0, "String",              "ltrim",        "key"     -> "String",  "start"    -> "Long",    "end"   -> "Long"),
    JedisCommand(0, "List[String]",        "mget",         "keys"    -> "String*"),
    JedisCommand(0, "Long",                "persist",      "key"     -> "String"),
    JedisCommand(0, "String",              "randomKey"),
    JedisCommand(0, "String",              "rename",       "oldkey"  -> "String",  "newkey"   -> "String"),
    JedisCommand(0, "String",              "restore",      "key"     -> "String",  "ttl"      -> "Int",     "serializedValue" -> "Array[Byte]"),
    JedisCommand(0, "String",              "rpop",         "key"     -> "String"),
    JedisCommand(0, "String",              "rpoplpush",    "srckey"  -> "String",  "dstkey"   -> "String"),
    JedisCommand(0, "Long",                "rpush",        "key"     -> "String",  "strings"  -> "String*"),
    JedisCommand(0, "Long",                "rpushx",       "key"     -> "String",  "string"   -> "String*"),
    JedisCommand(0, "String",              "set",          "key"     -> "String",  "value"    -> "String"),
    JedisCommand(1, "String",              "set",          "key"     -> "String",  "value"    -> "String",  "nxxx" -> "String"),
    JedisCommand(2, "String",              "set",          "key"     -> "String",  "value"    -> "String",  "nxxx" -> "String", "expx" -> "String", "time" -> "Int"),
    JedisCommand(3, "String",              "set",          "key"     -> "String",  "value"    -> "String",  "nxxx" -> "String", "expx" -> "String", "time" -> "Long"),
    JedisCommand(0, "Long",                "ttl",          "key"     -> "String")
  )
}
