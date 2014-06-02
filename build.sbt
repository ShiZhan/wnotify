name := "wnotify"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "commons-codec" % "commons-codec" % "1.9",
  "commons-validator" % "commons-validator" % "1.4.0",
  "org.jsoup" % "jsoup" % "1.7.3",
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "org.slf4j" % "slf4j-log4j12" % "1.7.5",
  "log4j" % "log4j" % "1.2.17"
)
