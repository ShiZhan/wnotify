package kernel

object Watcher {
  import java.io.{ BufferedReader, BufferedWriter, InputStreamReader, OutputStreamWriter }
  import java.net.URL
  import org.jsoup.Jsoup
  import helper.OptParse.Config

  private val logger = org.slf4j.LoggerFactory.getLogger(this.getClass)

  def run(config: Config): Unit = {
    val Config(target, cycle, selector, exec) = config
    logger.info("Config: [{}]", config)
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
        logger.info("Content changed to:")
        println(current)
        if (exec != null) {
          logger.info("calling [{}]", exec)
          val message = "Previous:\n" + previous + "\nCurrent:\n" + current
          val p = Runtime.getRuntime.exec(exec)
          val input = new BufferedReader(new InputStreamReader(p.getInputStream))
          val output = new BufferedWriter(new OutputStreamWriter(p.getOutputStream))
          output.write(message)
          Iterator.continually(input.readLine).takeWhile(_ != null).foreach(println)
        }
        current
      }
      case (previous, _) => previous
    }
  }
}
