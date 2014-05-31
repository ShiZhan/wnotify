package helper

object Config extends Logging {
  import java.io.File
  import scala.util.parsing.json.JSON

  case class Parser(json: String) {
    private val config = JSON.parseFull(json).get.asInstanceOf[Map[String, Any]]
    val url = config.get("url") match { case Some(value: String) => value; case _ => "." }
    val cycle = config.get("cycle") match { case Some(value: Int) => value; case _ => 3600 }
  }

  def fromFile(jsonFileName: String) = {
    try {
      val jsonFile = new File(jsonFileName)
      val jsonString = io.Source.fromFile(jsonFile).mkString
      Parser(jsonString)
    } catch {
      case e: Exception =>
        logger.error("Error loading JSON: [{}]", jsonFileName)
        logger.error(e.toString)
        logger.error("loading default configuration")
        Parser(BuildIn.getString("default.json"))
    }
  }
}