object Wnotify {
  import helper.{ OptParse, Version }

  val usage = """usage: wnotify <-c cycle|-s selector> [URL]
 -h   print this message
 -v   show program version
 -c   cycle
 -s   selector"""
  val incorrectArgs = "Incorrect parameters, see help (Present -h)."

  def main(args: Array[String]) = {
    println("wNotify program")
    args.toList match {
      case "-h" :: Nil => println(usage)
      case "-v" :: Nil => println(Version.get)
      case optList if (!optList.isEmpty) =>
        val options = OptParse.parse(optList)
        println("Options:")
        println(options.mkString("; "))
      case _ => println(incorrectArgs)
    }
  }
}
