package code.lib

import net.liftweb.http.Req

object RobotDetector {

  val botNames =
    "Googlebot" ::
    "Mediapartners-Google" ::
    "AdsBot-Google" :: Nil

  def known_?(ua: String) =
    botNames exists (ua contains _)

  def googlebot_?(r: Req) : Boolean =
    r.header("User-Agent") exists known_?

}
