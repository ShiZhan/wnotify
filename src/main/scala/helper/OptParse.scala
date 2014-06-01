package helper

object OptParse {
  type Options = Map[Symbol, Any]

  def parse(optList: List[String]): Options = {
    optList match {
      case Nil => Map()
      case "-c" :: cycle :: more => parse(more) ++ Map('cycle -> cycle.toInt)
      case "-s" :: selector :: more => parse(more) ++ Map('selector -> selector)
      case url :: more if (url.startsWith("http://") || url.startsWith("https://")) =>
        parse(more) ++ Map('url -> url)
      case unknown :: more => parse(more)
    }
  }
}