package code.lib

import org.specs2.mutable._
import net.liftweb.mocks.MockHttpServletRequest
import net.liftweb.mockweb.MockWeb

class SingleRobotDetectorSpec extends Specification {

  "Google Bot Detector" should {

    "spot a web crawler" in {

      val userAgent = "Mozilla/5.0 (compatible; Googlebot/2.1)"

      // Mock a request with the right header:
      val http = new MockHttpServletRequest()
      http.headers = Map("User-Agent" -> List(userAgent))

      // Test with a Lift Req:
      MockWeb.testReq(http) { r =>
        RobotDetector.googlebot_?(r) must beTrue
      }


    }

  }

}
