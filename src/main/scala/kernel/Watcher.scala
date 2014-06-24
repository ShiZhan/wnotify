package kernel

object Watcher {
  import java.net.URL
  import org.jsoup.Jsoup
  import helper.OptParse.Config

  private val logger = org.slf4j.LoggerFactory.getLogger(this.getClass)

  def run(config: Config): Unit = {
    val Config(target, cycle, selector, diff, full, mail) = config
    logger.info("Config: [{}]", (target, cycle, selector, diff, full, mail))
    logger.info("press Ctrl+C to exit")
    val timeout = 2000
    val url = new URL(target)
    def check =
      try { Some(Jsoup.parse(url, timeout).select(selector).toString) }
      catch { case e: Exception => logger.error(e.toString); None }
    val first = check.getOrElse(""); logger.info("Beginning content:"); println(first)
    val samples = Iterator.continually { Thread.sleep(cycle); check }
    (first /: samples) {
      case (previous, Some(current)) if (current != previous) => {
        logger.info("Content changed")
        if (diff) println(current diff previous)
        if (full) println(current)
        if (mail != null) println("TODO: send email notification")
        current
      }
      case (previous, _) => previous
    }
  }
}
