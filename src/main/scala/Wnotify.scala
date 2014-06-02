object Wnotify {
  import helper.OptParse._
  import helper.Version
  import kernel.Watcher

  val usage = """usage: wnotify [-hv] [-c cycle|-s selector|-d] [URL]
 -h   print this message
 -v   show program version
 -c   cycle (millisecond)
 -s   selector
 -d   show diff information"""
  val incorrectArgs = "Incorrect parameters, see help (Present -h)."

  def main(args: Array[String]) = {
    println("wNotify program")
    args.toList match {
      case "-h" :: Nil => println(usage)
      case "-v" :: Nil => println(Version.get)
      case optList if (!optList.isEmpty) =>
        val options = parse(optList)
        Watcher.run(options.target, options.cycle, options.selector, options.diff)
      case _ => println(incorrectArgs)
    }
  }
}
