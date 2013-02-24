package code.lib

import net.liftweb.util._
import net.liftweb.http._

object CustomResourceId {

  def init() : Unit = {

    // The random number we're using to avoid caching
    val resourceId = Helpers.nextFuncName

    // Prefix lift:with-resource-id links with "/cache/{resouceId}"
    LiftRules.attachResourceId = (path: String) => {
      "/cache/" + resourceId + path
    }

    // Remove the cache/{resourceId} from the request if there is one
    LiftRules.statelessRewrite.prepend( NamedPF("BrowserCacheAssist") {
      case RewriteRequest(ParsePath("cache" :: id :: file, suffix, _, _), _, _) =>
        RewriteResponse(file, suffix)
    })

  }
}
