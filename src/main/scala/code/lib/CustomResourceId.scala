package code.lib

import net.liftweb.util._
import net.liftweb.http._



import net.liftweb.http._
import net.liftweb.util._

object AssetCacheBuster {

  def init() : Unit = {
    val resourceId = Helpers.nextFuncName

    LiftRules.attachResourceId = (path: String) => {
      println("Considering attaching on "+path)
      val PathRegex = """\/cached(\/css\/|\/js\/)(\S+)""".r
      try {
        val PathRegex(root, rest) = path
        println("MATCH! "+root+" "+rest)
        "/cached" + root + resourceId + "/" + rest
      } catch {
        case e: scala.MatchError => path
      }
    }

    // Remove the cache/{resourceId} from the request if there is one

    LiftRules.statelessRewrite.prepend( NamedPF("BrowserCacheAssist") {

/*
* 127.0.0.1:8080/cached/css/F1186108324060L10M4M/standard.css
* RewriteRequest(ParsePath(List(css, standard),css,true,false),GetRequest,net.liftweb.http.provider.servlet.HTTPRequestServlet@72e9891)

*
* */

      case RewriteRequest(ParsePath("cached" :: "css" :: id :: file :: Nil, suffix, _, _), _, _) =>
        println("Rewrite on "+file)
        println("TO..."+ ("css" :: file :: Nil) + " "+ suffix)
        RewriteResponse("css" :: file :: Nil, suffix)


    })
  }
}
object CustomResourceId {

  def init() : Unit = {

    // The random number we're using to avoid caching
    val resourceId = Helpers.nextFuncName

    // Prefix lift:with-resource-id links with "/cache/{resourceId}"
    LiftRules.attachResourceId = (path: String) => {
      println("attaching to "+path)
      "/cache/" + resourceId + path
    }

    // Remove the cache/{resourceId} from the request if there is one
    LiftRules.statelessRewrite.prepend( NamedPF("BrowserCacheAssist") {
      case RewriteRequest(ParsePath("cache" :: id :: file, suffix, _, _), _, _) =>
        println("Re-writing on "+file)
        RewriteResponse(file, suffix)
    })

  }
}
