package code.snippet

import net.liftweb.util.Helpers._
import net.liftweb.util.PassThru
import scala.util.Random
import xml.Text

class PassThruSnippet {

  private def fiftyFifty = Random.nextBoolean

  def render =
    if (fiftyFifty) "*" #> Text("Congratulations! The content was changed")
    else PassThru

}
