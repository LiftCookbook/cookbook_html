package code.snippet

import net.liftweb.util.Helpers._
import net.liftweb.http.RequestVar
import net.liftweb.common.Loggable


object Database extends Loggable {
  def fetchThings : List[String] = {
    logger.info("I am fetching things")
    List("a","b","c")
  }
}

object data extends RequestVar[List[String]](Database.fetchThings)

class ListTheThings {
  def render = "*" #> data.get.mkString(",")
}

class CountTheThings {
  def render = "*" #> data.is.length
}
