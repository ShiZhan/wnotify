object Wnotify {
  val usage = """usage: wnotify
 -h   print this message
 -v   show program version
 [JSON]   loading JSON configuration"""
  val incorrectArgs = "Incorrect parameters, see help (Present -h)."

  def main(args: Array[String]) = {
    println("wNotify program")
    args.toList match {
      case "-h" :: Nil => println(usage)
      case "-v" :: Nil => println(helper.Version.get)
      case json if (!json.isEmpty) =>
        println("Loading JSON:")
        println(helper.Config.fromFile(json.head).url)
      case _ => println(incorrectArgs)
    }
  }
}
