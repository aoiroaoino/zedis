import sbt._

object FreeCommandGenerator {

  lazy val template =
    s"""|package zedis.free.commands
        |
        |import scala.collection.JavaConversions
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

  case class JedisCommand(returnType: String, method: String, args: (String, String)*) {

    val className = method.capitalize
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
    JedisCommand("Unit",                "close"),
    JedisCommand("Long",                "decr",         "key"     -> "String"),
    JedisCommand("Long",                "decrBy",       "key"     -> "String",  "integer"  -> "Long"),
    JedisCommand("Long",                "del",          "keys"    -> "String*"),
    // JedisCommand("Long",                "del",          "key"     -> "String"),
    JedisCommand("String",              "echo",         "string"  -> "String"),
    JedisCommand("Boolean",             "exists",       "key"     -> "String"),
    JedisCommand("Long",                "expire",       "key"     -> "String",  "seconds"  -> "Int"),
    JedisCommand("Long",                "expireAt",     "key"     -> "String",  "unixTime" -> "Long"),
    JedisCommand("String",              "get",          "key"     -> "String"),
    JedisCommand("String",              "getSet",       "key"     -> "String",  "value"    -> "String"),
    JedisCommand("Long",                "hdel",         "key"     -> "String",  "fields"   -> "String*"),
    JedisCommand("Boolean",             "hexists",      "key"     -> "String",  "field"    -> "String"),
    JedisCommand("String",              "hget",         "key"     -> "String",  "field"    -> "String"),
    // JedisCommand("Map[String, String]", "hgetAll",      "key"     -> "String"),
    JedisCommand("Long",                "hincrBy",      "key"     -> "String",  "field"    -> "String",  "value" -> "Long"),
    JedisCommand("Double",              "hincrByFloat", "key"     -> "String",  "field"    -> "String",  "value" -> "Double"),
    // JedisCommand("Set[String]",         "hkeys",        "key"     -> "String"),
    JedisCommand("Long",                "hlen",         "key"     -> "String"),
    // JedisCommand("List[String]",        "hmget",        "key"     -> "String",  "fields"   -> "String*"),
    // JedisCommand("String",              "hmset",        "key"     -> "String",  "hash"     -> "Map[String, String]"),
    JedisCommand("Long",                "hset",         "key"     -> "String",  "field"    -> "String",  "value" -> "String"),
    JedisCommand("Long",                "hsetnx",       "key"     -> "String",  "field"    -> "String",  "value" -> "String"),
    // JedisCommand("List[String]",        "hvals",        "key"     -> "String"),
    JedisCommand("Long",                "incr",         "key"     -> "String"),
    JedisCommand("Long",                "incrBy",       "key"     -> "String",  "integer"  -> "Long"),
    JedisCommand("Double",              "incrByFloat",  "key"     -> "String",  "value"    -> "Double"),
    // JedisCommand("Set[String]",         "keys",         "pattern" -> "String"),
    JedisCommand("String",              "lindex",       "key"     -> "String",  "index"    -> "Long"),
    JedisCommand("Long",                "llen",         "key"     -> "String"),
    JedisCommand("String",              "lpop",         "key"     -> "String"),
    JedisCommand("Long",                "lpush",        "key"     -> "String",  "strings"  -> "String*"),
    JedisCommand("Long",                "lpushx",       "key"     -> "String",  "string"   -> "String*"),
    // JedisCommand("List[String]",        "lrange",       "key"     -> "String",  "start"    -> "Long",    "end"   -> "Long"),
    JedisCommand("Long",                "lrem",         "key"     -> "String",  "count"    -> "Long",    "value" -> "String"),
    JedisCommand("String",              "lset",         "key"     -> "String",  "index"    -> "Long",    "value" -> "String"),
    JedisCommand("String",              "ltrim",        "key"     -> "String",  "start"    -> "Long",    "end"   -> "Long"),
    // JedisCommand("List[String]",        "mget",         "keys"    -> "String*"),
    JedisCommand("Long",                "persist",      "key"     -> "String"),
    JedisCommand("String",              "randomKey"),
    JedisCommand("String",              "rename",       "oldkey"  -> "String",  "newkey"   -> "String"),
    JedisCommand("String",              "restore",      "key"     -> "String",  "ttl"      -> "Int",     "serializedValue" -> "Array[Byte]"),
    JedisCommand("String",              "rpop",         "key"     -> "String"),
    JedisCommand("String",              "rpoplpush",    "srckey"  -> "String",  "dstkey"   -> "String"),
    JedisCommand("Long",                "rpush",        "key"     -> "String",  "strings"  -> "String*"),
    JedisCommand("Long",                "rpushx",       "key"     -> "String",  "string"   -> "String*"),
    JedisCommand("String",              "set",          "key"     -> "String",  "value"    -> "String"),
    // JedisCommand("String",              "set",          "key"     -> "String",  "value"    -> "String",  "nxxx" -> "String"),
    // JedisCommand("String",              "set",          "key"     -> "String",  "value"    -> "String",  "nxxx" -> "String", "expx" -> "String", "time" -> "Int"),
    // JedisCommand("String",              "set",          "key"     -> "String",  "value"    -> "String",  "nxxx" -> "String", "expx" -> "String", "time" -> "Long"),
    JedisCommand("Long",                "ttl",          "key"     -> "String")
  )
}
