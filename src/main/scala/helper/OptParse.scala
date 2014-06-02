package helper

object OptParse {
  type Options = Map[Symbol, Any]

  def parse(optList: List[String]): Options = {
    optList match {
      case Nil => Map()
      case "-c" :: cycle :: more =>
        val value = try { cycle.toInt } catch { case e: Exception => 0 }
        if (0 == value) parse(more) // 0 is invalid here
        else parse(more) ++ Map('cycle -> cycle.toInt)
      case "-s" :: selector :: more => parse(more) ++ Map('selector -> selector)
      case "-d" :: more => parse(more) ++ Map('diff -> true)
      case target :: more => parse(more) ++ Map('target -> target)
    }
  }

  implicit class OptionsWrapper(o: Options) { // set defaults and get values
    val target = o.get('target) match { case Some(t: String) => t; case _ => "" }
    val cycle = o.get('cycle) match { case Some(c: Int) => c; case _ => 3600 * 1000 }
    val selector = o.get('selector) match { case Some(s: String) => s; case _ => "body" }
    val diff = o.get('diff) match { case Some(s: Boolean) => s; case _ => false }
  }
}