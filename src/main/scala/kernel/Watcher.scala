package kernel

object Watcher extends helper.Logging {
  import org.apache.commons.validator.routines.UrlValidator
  import helper.DateTime._

  private val httpSchemes = Array("http", "https")
  private val httpValidator = new UrlValidator(httpSchemes)
  private var log = List[(Long, String)]((getMillis, "start"))

  def console: Unit = {
    val prompt = "> "
    println("enter [exit] to exit, [tail] to show last 10 log entries.")
    print(prompt)
    for (line <- io.Source.stdin.getLines) {
      val output = line.split(" ").toList match {
        case "tail" :: Nil =>
          log.takeRight(10)
            .map { case (millis, md5) => millis.toDate + "\t" + md5 }
            .mkString("Time                     \tChecksum\n---\n", "\n", "\n")
        case "exit" :: Nil => return
        case "" :: Nil => ""
        case _ => "Unrecognized command: [%s]\n".format(line)
      }
      print(output + prompt)
    }
  }

  def run(target: String, cycle: Int, selector: String) = {
    if (httpValidator.isValid(target)) {
      logger.info("URL: [{}], cycle: [{}], selector: [{}]", target, cycle.toString, selector)
      console
    } else logger.error("invalid URL: [{}]", target)
  }
}
