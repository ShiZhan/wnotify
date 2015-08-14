import sbt._
import Keys._
import sbtassembly.Plugin._
import AssemblyKeys._

object MyBuild extends Build {
  val scalaV = Option(System.getProperty("scala.version")).getOrElse("2.11.6")
  val validV = "1.4.0"
  val jsoupV = "1.8.1"
  val confV  = "1.3.0"
  val slf4jV = "1.7.12"
  val log4jV = "1.2.17"

  lazy val commonSettings = Defaults.defaultSettings ++ Seq(
    version      := "0.1-SNAPSHOT",
    organization := "com.simba",
    scalaVersion := scalaV,
    scalacOptions in Compile ++= Seq(
      "-encoding", "UTF-8",
      "-deprecation", "-feature", "-unchecked",
      "-Xlint"),
    libraryDependencies ++= Seq(
      "commons-validator" % "commons-validator" % validV,
      "org.jsoup"    % "jsoup"  % jsoupV,
      "com.typesafe" % "config" % confV,
      "org.slf4j" % "slf4j-api"     % slf4jV,
      "org.slf4j" % "slf4j-log4j12" % slf4jV,
      "log4j"     % "log4j"         % log4jV
    )
  )

  lazy val copyDependencies = TaskKey[Unit]("copyDep")

  def copyDepTask = copyDependencies <<= (update, crossTarget, scalaVersion) map {
    (updateReport, out, scalaVer) =>
    updateReport.allFiles foreach { srcPath =>
      val destPath = out / "lib" / srcPath.getName
      IO.copyFile(srcPath, destPath, preserveLastModified=true)
    }
  }

  lazy val buildSettings = commonSettings

  lazy val wnotify = Project(
    id = "wnotify",
    base = file("."),
    settings = commonSettings ++ assemblySettings ++ copyDepTask ++ Seq(
      logLevel in assembly := Level.Warn
    )
  )
}
