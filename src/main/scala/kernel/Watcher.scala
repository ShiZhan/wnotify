package kernel

object Watcher {
  import java.net.URL
  import org.jsoup.Jsoup
  import helper.DateTime._

  private val logger = org.slf4j.LoggerFactory.getLogger(this.getClass)

  def run(target: String, cycle: Int, selector: String, diff: Boolean, full: Boolean): Unit = {
    val timeout = 2000
    var previous = ""
    logger.info("Options: [{}]", (target, cycle, selector, diff, full))
    logger.info("press Ctrl+C to exit")
    while (true) {
      try {
        val dateTime = getMillis
        val doc = Jsoup.parse(new URL(target), timeout)
        val area = doc.select(selector)
        val current = area.toString
        if (current != previous) {
          println(dateTime.toDate)
          if (diff == true) {
            println("diff:")
            println(current diff previous)
            previous = current
          }
          if (full == true) {
            println("content:")
            println(current)
          }
        }
      } catch { case e: Exception => println(e) }
      Thread.sleep(cycle)
    }
  }
}
