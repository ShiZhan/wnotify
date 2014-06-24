object Wnotify {
  import helper.OptParse._
  import helper.Version
  import kernel.Watcher

  val usage = """usage: wnotify [-hv] [-c cycle|-s selector|-d|-f] [URL]
 -h   print this message
 -v   show program version
 -c   cycle (millisecond)
 -s   selector
 -d   show diff information
 -f   show changed content
 -m   send log via email"""
  val incorrectArgs = "Incorrect parameters, see help (Present -h)."

  def main(args: Array[String]) = {
    println("wNotify program")
    args.toList match {
      case "-h" :: Nil => println(usage)
      case "-v" :: Nil => println(Version.get)
      case optList =>
        parse(optList).toConfig match {
          case Some(config) => Watcher.run(config)
          case _ => println(incorrectArgs)
        }
    }
  }
}
