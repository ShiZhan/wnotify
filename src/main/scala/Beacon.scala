object Beacon {
  val usage = """usage: Beacon
 -h   print this message
 -v   show program version
 [JSON]   loading JSON configuration"""
  val incorrectArgs = "Incorrect parameters, see help (Present -h)."

  def main(args: Array[String]) = {
    println("Beacon program")
    args.toList match {
      case "-h" :: Nil => println(usage)
      case "-v" :: Nil => println(helper.Version.get)
      case json if (!json.isEmpty) =>
        println("Loading JSON:")
        println(json.mkString(", "))
      case _ => println(incorrectArgs)
    }
  }
}
