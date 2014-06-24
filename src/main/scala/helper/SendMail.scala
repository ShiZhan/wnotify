package helper

object SendMail {
  import org.apache.commons.mail._
  import com.typesafe.config.ConfigFactory

  private val mailConf = ConfigFactory.load("mail.conf")
  private val hostName = mailConf.getString("email.hostname")
  private val smtpPort = mailConf.getInt("email.port")
  private val userName = mailConf.getString("email.username")
  private val password = mailConf.getString("email.password")
  private val ssl = mailConf.getBoolean("email.is.ssl")
  val mailer = mailConf.getString("email.mailer")

  sealed abstract class MailType
  case object Plain extends MailType
  case object Rich extends MailType
  case object MultiPart extends MailType

  case class Mail(
    from: (String, String),
    to: Seq[String],
    cc: Seq[String] = Seq.empty,
    bcc: Seq[String] = Seq.empty,
    subject: String,
    message: String,
    richMessage: Option[String] = None,
    attachment: Option[(java.io.File)] = None)

  def send(mail: Mail) {
    val format =
      if (mail.attachment.isDefined) MultiPart
      else if (mail.richMessage.isDefined) Rich
      else Plain

    val commonsMail: Email = format match {
      case Plain => new SimpleEmail().setMsg(mail.message)
      case Rich => new HtmlEmail().setHtmlMsg(mail.richMessage.get).setTextMsg(mail.message)
      case MultiPart => {
        val attachment = new EmailAttachment()
        attachment.setPath(mail.attachment.get.getAbsolutePath)
        attachment.setDisposition(EmailAttachment.ATTACHMENT)
        attachment.setName(mail.attachment.get.getName)
        new MultiPartEmail().attach(attachment).setMsg(mail.message)
      }
    }

    commonsMail.setHostName(hostName)
    commonsMail.setSmtpPort(smtpPort)
    commonsMail.setAuthentication(userName, password)
    commonsMail.setSSL(ssl)

    mail.to foreach (commonsMail.addTo)
    mail.cc foreach (commonsMail.addCc)
    mail.bcc foreach (commonsMail.addBcc)

    commonsMail.
      setFrom(mail.from._1, mail.from._2).
      setSubject(mail.subject).
      send()
  }
}