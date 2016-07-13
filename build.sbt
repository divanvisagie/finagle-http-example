name := """finatra-http-seed"""
organization := "com.example"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.7"

fork in run := true

parallelExecution in ThisBuild := false

javaOptions ++= Seq(
  "-Dlog.service.output=/dev/stderr",
  "-Dlog.access.output=/dev/stderr")

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "Twitter Maven" at "https://maven.twttr.com")


lazy val versions = new {
  val finatra = "2.1.4"
  val guice = "4.0"
  val logback = "1.0.13"
  val mockito = "1.9.5"
  val scalatest = "2.2.3"
  val specs2 = "2.3.12"
  val swagger = "0.5.0"
  val finagle = "6.34.0"
}

lazy val baseSettings = Seq(
  version := "1.0.0-SNAPSHOT",
  scalaVersion := "2.11.7",
  ivyScala := ivyScala.value.map(_.copy(overrideScalaVersion = true)),
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    "Twitter Maven" at "https://maven.twttr.com"
  ),
  fork in run := true
 )

lazy val root = (project in file(".")).
  settings(
    name := """finagle-http-example""",
    organization := "com.divanvisagie",
    moduleName := "finagle-http-seed"
  ).
  aggregate(
    server
  )

lazy val client = (project in file("client")).
  settings(baseSettings).
  settings(
    libraryDependencies ++= Seq(
      "com.twitter" %% "finagle-http" % versions.finagle
    )
  )

lazy val server = (project in file("server")).
  settings(baseSettings).
  settings(
    libraryDependencies ++= Seq(
      "com.github.xiaodongw" %% "swagger-finatra2" % versions.swagger,

      "com.twitter.finatra" %% "finatra-http" % versions.finatra,
      "com.twitter.finatra" %% "finatra-slf4j" % versions.finatra,
      "ch.qos.logback" % "logback-classic" % versions.logback,
      "ch.qos.logback" % "logback-classic" % versions.logback % "test",

      "com.twitter.finatra" %% "finatra-http" % versions.finatra % "test",
      "com.twitter.inject" %% "inject-server" % versions.finatra % "test",
      "com.twitter.inject" %% "inject-app" % versions.finatra % "test",
      "com.twitter.inject" %% "inject-core" % versions.finatra % "test",
      "com.twitter.inject" %% "inject-modules" % versions.finatra % "test",
      "com.google.inject.extensions" % "guice-testlib" % versions.guice % "test",

      "com.twitter.finatra" %% "finatra-http" % versions.finatra % "test" classifier "tests",
      "com.twitter.inject" %% "inject-server" % versions.finatra % "test" classifier "tests",
      "com.twitter.inject" %% "inject-app" % versions.finatra % "test" classifier "tests",
      "com.twitter.inject" %% "inject-core" % versions.finatra % "test" classifier "tests",
      "com.twitter.inject" %% "inject-modules" % versions.finatra % "test" classifier "tests",

      "org.mockito" % "mockito-core" % versions.mockito % "test",
      "org.scalatest" %% "scalatest" % versions.scalatest % "test",
      "org.specs2" %% "specs2" % versions.specs2 % "test"
      )
    )
  

 

