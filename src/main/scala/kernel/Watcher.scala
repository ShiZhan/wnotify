package kernel

object Watcher extends helper.Logging {
  import org.apache.commons.validator.routines.UrlValidator

  private val httpSchemes = Array("http", "https")
  private val httpValidator = new UrlValidator(httpSchemes)

  def run(target: String, cycle: Int, selector: String) = {
    if (httpValidator.isValid(target)) {
      logger.info("URL: [{}], cycle: [{}], selector: [{}]", target, cycle.toString, selector)
    } else logger.error("invalid URL: [{}]", target)
  }
}