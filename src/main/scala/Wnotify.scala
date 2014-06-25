object Wnotify {
  import helper.OptParse._
  import helper.Version
  import kernel.Watcher

  val usage = """usage: wnotify [-hvcse] [URL]
 -h   print this message
 -v   show program version
 -c   [cycle], UNIT: millisecond
 -s   [selector], DOM selector
 -e   [command], send log via external command"""
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
