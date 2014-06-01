import sbt._
import Keys._

object MyBuild extends Build {
  lazy val buildSettings = Defaults.defaultSettings ++ Seq(
    version := "0.1-SNAPSHOT",
    organization := "com.simba",
    scalaVersion := "2.10.4"
  )

  lazy val copyDependencies = TaskKey[Unit]("copyDep")

  def copyDepTask = copyDependencies <<= (update, crossTarget, scalaVersion) map {
    (updateReport, out, scalaVer) =>
    updateReport.allFiles foreach { srcPath =>
      val destPath = out / "lib" / srcPath.getName
      IO.copyFile(srcPath, destPath, preserveLastModified=true)
    }
  }

  lazy val wnotify = Project(
    id = "wnotify",
    base = file("."),
    settings = Defaults.defaultSettings ++
    Seq(
      copyDepTask
    )
  )
}
