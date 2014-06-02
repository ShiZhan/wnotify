package kernel

object Watcher {
  import java.net.URL
  import scala.collection.mutable.MutableList
  import org.apache.commons.codec.digest.DigestUtils.md5Hex
  import org.apache.commons.validator.routines.UrlValidator
  import org.jsoup.Jsoup
  import helper.DateTime._

  private val logger = org.slf4j.LoggerFactory.getLogger(this.getClass)

  private val httpSchemes = Array("http", "https")
  private val httpValidator = new UrlValidator(httpSchemes)
  private val timeout = 2000
  private val webLog = MutableList[(Long, String)]((getMillis, "start"))

  def run(target: String, cycle: Int, selector: String) = {
    if (httpValidator.isValid(target)) {
      logger.info("URL: [{}], cycle: [{}], selector: [{}]", target, cycle.toString, selector)
      logger.info("press Ctrl+C to exit")
      while (true) {
        try {
          val dateTime = getMillis
          val doc = Jsoup.parse(new URL(target), timeout)
          val area = doc.select(selector)
          val checksum = md5Hex(area.toString)
          if (checksum != webLog.last._2) println(dateTime.toDate + "; " + checksum)
          webLog += ((dateTime, checksum))
        } catch {
          case e: Exception => println(e)
        }
        Thread.sleep(cycle)
      }
    } else logger.error("invalid URL: [{}]", target)
  }
}
