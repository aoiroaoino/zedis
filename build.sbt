lazy val commonSettings = Seq(
  organization := "com.github.aoiroaoino",
  scalaVersion := "2.11.8",
  crossScalaVersions := Seq("2.10.6", "2.11.8"),
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

val scalazVersion     = "7.2.3"
val scalaredisVersion = "3.0"
val jedisVersion      = "2.8.0"

val scalaz_core  = "org.scalaz"                 %% "scalaz-core"  % scalazVersion
val monocle_core = "com.github.julien-truffaut" %% "monocle-core" % "1.2.2"

val scalatest    = "org.scalatest"              %% "scalatest"    % "2.2.5" % "test"

lazy val root = (project in file("."))
  .settings(moduleName := "zedis")
  .settings(commonSettings)
  .aggregate(core, jedis, mock)
  .dependsOn(core, jedis, mock)

lazy val core = (project in file("core"))
  .settings(moduleName := "core")
  .settings(commonSettings)
  .settings(libraryDependencies ++= Seq(
    scalaz_core,
    scalatest
  ))

lazy val mock = (project in file("mock"))
  .settings(moduleName := "jedis")
  .settings(commonSettings)
  .settings(libraryDependencies ++= Seq(
    scalatest
  ))
  .dependsOn(core)

lazy val scalaredis = (project in file("scalaredis"))
  .settings(moduleName := "scalaredis")
  .settings(commonSettings)
  .settings(libraryDependencies ++= Seq(
    "net.debasishg" %% "redisclient" % scalaredisVersion,
    scalatest
  ))
  .dependsOn(core)

lazy val jedis = (project in file("jedis"))
  .settings(moduleName := "jedis")
  .settings(commonSettings)
  .settings(libraryDependencies ++= Seq(
    "redis.clients" % "jedis" % jedisVersion,
    scalatest
  ))
  .dependsOn(core)
