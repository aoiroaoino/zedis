package zedis
package config

import scala.collection.JavaConversions._

import com.typesafe.config.ConfigFactory

private[config] trait Config {

  private def root = "zedis"

  private lazy val config =
    ConfigFactory.defaultOverrides.withFallback(ConfigFactory.load())

  protected def tag: String

  protected def getInt(key: String): Int =
    config.getInt( root$tag$(key) )

  protected def getLong(key: String): Long =
    config.getLong( root$tag$(key) )

  protected def getString(key: String): String =
    config.getString( root$tag$(key) )

  protected def getStringList(key: String): List[String] =
    config.getStringList( root$tag$(key) ).toList


  // helper
  private def root$tag$(str: String): String =
    List(root, tag, str).filter(_.nonEmpty).mkString(".")
}
