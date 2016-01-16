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

  lazy val genCommand = taskKey[Unit]("generate Commands for Command.scala")
  lazy val genPipelineCommand = taskKey[Unit]("generate Commands for PipelineCommand.scala")

  lazy val genCommands = Seq(
    genCommand <<= (scalaSource in Compile, streams) map {
      (scalaSource, streams) => {
        val f = scalaSource / "zedis" / "commands"/ "Command.scala"
        val source = CommandGenerator.template
        IO.write(f, source)
        streams.log("Finish!")
      }
    },
    genPipelineCommand <<= (scalaSource in Compile, streams) map {
      (scalaSource, streams) => {
        val f = scalaSource / "zedis" / "commands" / "PipelineCommand.scala"
        val source = PipelineCommandGenerator.template
        IO.write(f, source)
        streams.log("Finish!")
      }
    }
  )

  val scalazVersion  = "7.2.0"

  // main
  val scalaz_core       = "org.scalaz"                 %% "scalaz-core"       % scalazVersion
  val scalaz_effect     = "org.scalaz"                 %% "scalaz-effect"     % scalazVersion
  val scalaz_concurrent = "org.scalaz"                 %% "scalaz-concurrent" % scalazVersion
  val jedis             = "redis.clients"              %  "jedis"             % "2.8.0"
  val nscala_time       = "com.github.nscala-time"     %% "nscala-time"       % "2.6.0"
  val typesafe_config   = "com.typesafe"               %  "config"            % "1.2.1"

  // test
  val scalatest         = "org.scalatest"              %% "scalatest"         % "2.2.5"  % "test"

  val testDependencies = Seq(
    scalatest
  )

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
        scalaz_core,
        scalaz_effect,
        scalaz_concurrent,
        jedis,
        nscala_time,
        typesafe_config
      )
    ) ++ genCommands
  )

  lazy val test = Project(
    id           = "test",
    base         = file("test"),
    settings     = commonSettings ++ Seq(
      name := "zedis-test",
      libraryDependencies ++= testDependencies
    ),
    dependencies = Seq(core)
  )

  lazy val example = Project(
    id           = "example",
    base         = file("example"),
    settings     = commonSettings ++ Seq(
      name := "zedis-example",
      libraryDependencies ++= testDependencies
    ),
    dependencies = Seq(core)
  )
}
