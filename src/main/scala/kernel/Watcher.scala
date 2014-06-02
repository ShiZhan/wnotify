package kernel

object Watcher {
  import java.net.URL
  import scala.collection.mutable.MutableList
  import org.apache.commons.codec.digest.DigestUtils.md5Hex
  import org.jsoup.Jsoup
  import helper.DateTime._

  private val logger = org.slf4j.LoggerFactory.getLogger(this.getClass)

  def run(target: String, cycle: Int, selector: String, diff: Boolean): Unit = {
    val timeout = 2000
    val webLog = MutableList[(Long, String)]((getMillis, "start"))
    var previous = ""
    logger.info("URL: [{}], cycle: [{}], selector: [{}], diff: [{}]",
      target, cycle.toString, selector, diff.toString)
    logger.info("press Ctrl+C to exit")
    while (true) {
      try {
        val dateTime = getMillis
        val doc = Jsoup.parse(new URL(target), timeout)
        val area = doc.select(selector)
        val current = area.toString
        val checksum = md5Hex(current)
        if (checksum != webLog.last._2) {
          println(dateTime.toDate + "; " + checksum)
          if (diff == true) {
            println("diff:")
            println(current diff previous)
            previous = current
          }
        }
        webLog += ((dateTime, checksum))
      } catch { case e: Exception => println(e) }
      Thread.sleep(cycle)
    }
  }
}
