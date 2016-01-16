package zedis
package example

import scalaz.\/

import zedis.Imports._
import zedis.accessor.AccessorFactory

object ZedisClientExample {

  case class IdContainer[A](id: Int, data: A)
  case class User(name: String, age: Int)

  object User extends ZedisClient {

    private[this] def accessor() = AccessorFactory.local()

    val (prefix, nameField, ageField) = ("user-", "name", "age")

    private def createKey(id: Int): String = s"$prefix$id"

    def getName(id: Int): Either[Throwable, Option[String]] =
      accessor.using{ hget(createKey(id), nameField) }.execEither()

    def getAge(id: Int): Option[String] =
      accessor.using{ hget(createKey(id), ageField) }.execOpt()

    def find(id: Int): Throwable \/ Option[IdContainer[User]] = {
      val key = createKey(id)
      accessor.pipeline{
        (for{
          name <- $hget(key, nameField)
          age  <- $hget(key, ageField)
          _    <- $sync()
        } yield IdContainer(id, User(name.get(), age.get().toInt)) )
      }.exec()
    }

    def insert(id: Int, name: String, age: Int): Throwable \/ Option[Int] = {
      val key = createKey(id)
      accessor.autoSyncPipeline{
        (for{
          _ <- $hset(key, nameField, name)
          _ <- $hset(key, ageField, age.toString)
        } yield id)
      }.exec()
    }

  }
}
