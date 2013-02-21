package code.lib

import org.specs2._
import matcher._
import net.liftweb.mocks.MockHttpServletRequest
import net.liftweb.mockweb.MockWeb

class RobotDetectorSpec extends Specification with DataTables {

  def is = "Can detect Google robots" ^ {
    "Bot?" || "User Agent" |
    true   !! "Mozilla/5.0 (Googlebot/2.1)" |
    true   !! "Googlebot-Video/1.0" |
    true   !! "Mediapartners-Google" |
    true   !! "AdsBot-Google" |
    false  !! "Mozilla/5.0 (KHTML, like Gecko)" |> {
    (expectedResult, userAgent) => {
      val http = new MockHttpServletRequest()
      http.headers = Map("User-Agent" -> List(userAgent))
      MockWeb.testReq(http) { r =>
        RobotDetector.googlebot_?(r) must_== expectedResult
      }
      }
    }
  }


}
