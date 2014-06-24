package helper

object OptParse {
  import org.apache.commons.validator.routines.UrlValidator

  type Options = Map[Symbol, Any]
  private val httpSchemes = Array("http", "https")
  private val httpValidator = new UrlValidator(httpSchemes)

  def parse(optList: List[String]): Options = {
    optList match {
      case Nil => Map()
      case "-c" :: cycle :: more =>
        val value = try { cycle.toInt } catch { case e: Exception => 0 }
        if (0 == value) parse(more) // 0 is invalid here
        else parse(more) ++ Map('cycle -> cycle.toInt)
      case "-s" :: selector :: more => parse(more) ++ Map('selector -> selector)
      case "-d" :: more => parse(more) ++ Map('diff -> true)
      case "-f" :: more => parse(more) ++ Map('full -> true)
      case "-m" :: mail :: more => parse(more) ++ Map('mail -> mail)
      case target :: more =>
        if (httpValidator.isValid(target)) parse(more) ++ Map('target -> target)
        else parse(more)
    }
  }

  case class Config(
    target: String, cycle: Int, selector: String, diff: Boolean, full: Boolean, email: String)

  implicit class OptionsWrapper(o: Options) { // set defaults and get values
    val isValid = o.contains('target)
    val target = o.get('target) match { case Some(t: String) => t; case _ => "" }
    val cycle = o.get('cycle) match { case Some(c: Int) => c; case _ => 3600 * 1000 }
    val selector = o.get('selector) match { case Some(s: String) => s; case _ => "body" }
    val diff = o.get('diff) match { case Some(s: Boolean) => s; case _ => false }
    val full = o.get('full) match { case Some(f: Boolean) => f; case _ => false }
    val mail = o.get('mail) match { case Some(m: String) => m; case _ => "" }
    def toConfig =
      if (isValid) Some(Config(target, cycle, selector, diff, full, mail)) else None
  }
}
