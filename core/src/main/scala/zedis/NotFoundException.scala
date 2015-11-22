package zedis

case class NotFoundException(message: String) extends Exception(message)
