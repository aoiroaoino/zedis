package zedis
package example

import scalaz.\/

import zedis.syntax.command._

object ZedisStandaloneClientExample {

  case class IdContainer[A](id: Int, data: A)

  case class User(name: String, age: Int)

  object User extends ZedisStandaloneClient {

    val prefix = s"user-"

    val nameField = "name"
    val ageField  = "age"

    private def createKey(id: Int): String = s"$prefix$id"

    def getUserName(id: Int): Either[Throwable, String] =
      accessor.using{ hget(createKey(id), nameField).exec }.toEither

    def getUserAge(id: Int): Option[String] =
      accessor.using{ hget(createKey(id), ageField).execOpt }

    def find(id: Int): Throwable \/ IdContainer[User] = {
      val key = createKey(id)
      accessor.pipelined{
        (for{
          name <- $hget(key, nameField)
          age  <- $hget(key, ageField)
          _    <- $sync()
        } yield IdContainer(id, User(name.get(), age.get().toInt)) ).exec
      }
    }

    def insert(id: Int, user: User): Throwable \/ Int = {
      val key = createKey(id)
      accessor.autoSyncPipelined{
        (for{
          _ <- $hset(key, nameField, user.name)
          _ <- $hset(key, ageField, user.age)
        } yield id).exec
      }
    }

  }
}
