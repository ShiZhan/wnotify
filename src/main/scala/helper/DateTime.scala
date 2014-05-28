/**
 * dateTime
 */
package helper

/**
 * @author ShiZhan
 *
 */
object DateTime {
  import java.text.SimpleDateFormat
  import java.lang.System

  val dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z")
  def getMillis = System.currentTimeMillis
  implicit class Convert2Date(millis: Long) {
    def toDate = dateFormat.format(millis)
  }
}