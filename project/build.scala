import sbt._
import Keys._

object ZedisBuild extends Build {

  lazy val commonSettings = Seq(
    organization := "com.github.aoiroaoino",
    scalaVersion := "2.11.7",
    crossScalaVersions := Seq("2.10.6", "2.11.7"),
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-Xlint",
      "-Ywarn-dead-code",
      "-Ywarn-numeric-widen",
      "-language:higherKinds",
      "-language:postfixOps",
      "-language:implicitConversions"
    )
  ) ++ warnUnusedImport

  // from https://github.com/non/cats/blob/v0.2.0/build.sbt#L295-L306
  lazy val warnUnusedImport = Seq(
    scalacOptions ++= {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, 10)) =>
          Seq()
        case Some((2, n)) if n >= 11 =>
          Seq("-Ywarn-unused-import")
      }
    },
    scalacOptions in (Compile, console) ~= {_.filterNot(_ == "-Ywarn-unused-import")},
    scalacOptions in (Test, console) <<= (scalacOptions in (Compile, console))
  )

  // main
  val scalazCore     = "org.scalaz"                 %% "scalaz-core"   % "7.2.0-M4"
  val scalazStream   = "org.scalaz.stream"          %% "scalaz-stream" % "0.8"
  val monocleCore    = "com.github.julien-truffaut" %% "monocle-core"  % "1.1.1"
  val monocleMacro   = "com.github.julien-truffaut" %% "monocle-macro" % "1.1.1"
  val jedis          = "redis.clients"              %  "jedis"         % "2.7.2"

  // test
  val scalatest      = "org.scalatest"              %% "scalatest"     % "2.2.4"  % "test"

  lazy val root = Project(
    id        = "zedis",
    base      = file("."),
    settings  = commonSettings,
    aggregate = Seq(core, test)
  )

  lazy val core = Project(
    id       = "core",
    base     = file("core"),
    settings = commonSettings ++ Seq(
      name := "zedis-core",
      libraryDependencies ++= Seq(
        scalazCore,
        scalazStream,
        jedis
      ),
      genJedisCommands <<= (scalaSource in Compile, streams) map {
        (scalaSource, streams) => {
          val f = scalaSource / "zedis" / "free" / "commands" / "JedisCommands.scala"
          val source = FreeCommandGenerator.template
          IO.write(f, source)
          streams.log("Finish!")
        }
      }
    )
  )

  lazy val orm = Project(
    id           = "orm",
    base         = file("orm"),
    settings     = commonSettings ++ Seq(
      name := "zedis-orm"//,
      // libraryDependencies ++= Seq(
      // )
    ),
    dependencies = Seq(core)
  )

  lazy val test = Project(
    id           = "test",
    base         = file("test"),
    settings     = commonSettings ++ Seq(
      name := "zedis-test",
      libraryDependencies ++= Seq(
        scalatest
      )
    ),
    dependencies = Seq(core)
  )

  lazy val genJedisCommands = taskKey[Unit]("generate free monad for JedisCommands")
}
