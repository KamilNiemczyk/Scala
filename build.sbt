val scala3Version = "3.4.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "projekt",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= {
      val akkaVersion = "2.6.20"
      Seq(
        "org.scalameta" %% "munit" % "1.0.0" % Test,
        "com.typesafe.akka" %% "akka-actor" % akkaVersion,
        "com.typesafe" % "config" % "1.4.1"
      )
    }
  )
