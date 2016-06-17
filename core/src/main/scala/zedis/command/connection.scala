package zedis
package command

import scalaz.Free
import zedis.adt.CommandADT, CommandADT._

object connection extends ConnectionCommand

trait ConnectionCommand {

  def auth(password: String): RedisCommand[String] =
    Free.liftF[CommandADT, String](AUTH(password))

  def echo(message: String): RedisCommand[String] =
    Free.liftF[CommandADT, String](ECHO(message))

  def ping(message: Option[String] = None): RedisCommand[String] =
    Free.liftF[CommandADT, String](PING(message))

  def quit: RedisCommand[String] =
    Free.liftF[CommandADT, String](QUIT)

  def select(dbIndex: Int): RedisCommand[String] =
    Free.liftF[CommandADT, String](SELECT(dbIndex))
}
