name := "wnotify"

version := "1.0"

scalaVersion := Option(System.getProperty("scala.version")).getOrElse("2.11.2")

libraryDependencies ++= Seq(
  "commons-validator" % "commons-validator" % "1.4.0",
  "org.jsoup" % "jsoup" % "1.7.3",
  "com.typesafe" % "config" % "1.2.1",
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "org.slf4j" % "slf4j-log4j12" % "1.7.5",
  "log4j" % "log4j" % "1.2.17"
)
