package code.snippet

import net.liftweb.util.Helpers._
import net.liftweb.http.{ResponseShortcutException, InMemoryResponse, LiftResponse, SHtml}
import xml.Text

class DownloadLink {

  val poem =
    "Roses are red," ::
    "Violets are blue," ::
    "Lift rocks!" ::
    "And so do you." :: Nil

  def render =
    ".poem" #> poem.map(line => ".line" #> line) &
    "a" #> downloadLink

  def poemTextFile : LiftResponse =
    InMemoryResponse(
      poem.mkString("\n").getBytes("UTF-8"),
      "Content-Type" -> "text/plain; charset=utf8" ::
      "Content-Disposition" -> "attachment; filename=\"poem.txt\"" :: Nil,
      cookies=Nil, 200)

  def downloadLink =
    SHtml.link("#notused", () => throw new ResponseShortcutException(poemTextFile), Text("Download") )

}
