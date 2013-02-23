package code.snippet

import xml.{NodeSeq, Unparsed}
import net.liftweb.http.S

object Html5Shiv {

  def render = Unparsed(
    """|<!--[if lt IE 9]>
       |<script src="http://html5shim.googlecode.com/svn/trunk/html5.js">
       |</script>
       |<![endif]-->""".stripMargin)

}


object IEOnly {

  private def condition : String =
    S.attr("cond") openOr "IE"

  def render(ns: NodeSeq) : NodeSeq =
    Unparsed("<!--[if " + condition + "]>") ++ ns ++ Unparsed("<![endif]-->")

}
