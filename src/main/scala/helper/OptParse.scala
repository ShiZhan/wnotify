package helper

object OptParse {
  import org.apache.commons.validator.routines.UrlValidator

  type Options = Map[Symbol, Any]

  implicit class IntTranslator(value: String) {
    def toIntOrElse(default: Int) =
      try { value.toInt } catch { case e: Exception => default }
  }

  private val httpSchemes = Array("http", "https")
  private val httpValidator = new UrlValidator(httpSchemes)

  def parse(optList: List[String]): Options = {
    optList match {
      case Nil => Map()
      case "-c" :: cycle :: more =>
        val value = cycle.toIntOrElse(0)
        if (0 == value) parse(more)
        else parse(more) ++ Map('cycle -> value)
      case "-s" :: selector :: more => parse(more) ++ Map('selector -> selector)
      case "-e" :: exec :: more => parse(more) ++ Map('exec -> exec)
      case target :: more =>
        if (httpValidator.isValid(target)) parse(more) ++ Map('target -> target)
        else parse(more)
    }
  }

  case class Config(target: String, cycle: Int, selector: String, exec: String)
  implicit class OptionsWrapper(o: Options) { // set defaults and get values
    val isValid = o.contains('target)
    val target = o.get('target) match { case Some(t: String) => t; case _ => "" }
    val cycle = o.get('cycle) match { case Some(c: Int) => c; case _ => 3600 * 1000 }
    val selector = o.get('selector) match { case Some(s: String) => s; case _ => "body" }
    val exec = o.get('exec) match { case Some(e: String) => e; case _ => null }
    def toConfig = if (isValid) Some(Config(target, cycle, selector, exec)) else None
  }
}
