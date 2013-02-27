package code.snippet

import net.liftweb.util.Helpers._
import net.liftweb.http.{S, SHtml}

class Notices {

  def render =
    "@noticeButton" #> SHtml.button("Click me",
      () => S.notice("checkPrivacyPolicy", <span>See our <a href="/static/privacy">privacy policy</a></span>))

}
