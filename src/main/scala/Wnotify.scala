object Wnotify {
  import helper.{ Resource, Version }
  import kernel.{ Parser, Watcher }
  import Parser._

  lazy val usage = Resource.getString("functions.txt")
  val incorrectArgs = "Incorrect parameters, see help (Present -h)."

  def main(args: Array[String]) = args.toList match {
    case "-h" :: Nil => println(usage)
    case "-v" :: Nil => println(Version.get)
    case optList =>
      parse(optList).options match {
        case Some(config) => Watcher.run(config)
        case _ => println(incorrectArgs)
      }
  }
}
