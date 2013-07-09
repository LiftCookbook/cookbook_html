package bootstrap.liftweb

import net.liftweb._

import common._
import common.Full
import common.Full
import http._
import http.Html5Properties
import http.NotFoundAsTemplate
import http.ParsePath
import sitemap._
import Loc._
import net.liftmodules.JQueryModule
import net.liftweb.http.js.jquery._
import code.lib.CustomResourceId
import util.NamedPF


/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot extends Loggable {
  def boot {

    // where to search snippet
    LiftRules.addToPackages("code")

    val Forbid = If( () => false, () => ForbiddenResponse("No Way") )

    // Build SiteMap
    val entries = List(
      Menu.i("Home") / "index", // the simple way to declare a menu

      Menu.i("Download Link") / "downloadlink",
      Menu.i("Pass Thru Example") / "passthru",
      Menu.i("Head merge") / "headmerge",
      Menu.i("Forbidden") / "secret" >> Forbid,
      Menu.i("Sharing Between Snippets") / "snippetshare",

      // more complex because this menu allows anything in the
      // /static path to be visible
      Menu(Loc("Static", Link(List("static"), true, "/static/index"),
	       "Static Content")))

    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    LiftRules.setSiteMap(SiteMap(entries:_*))

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))

    //Init the jQuery module, see http://liftweb.net/jquery for more information.
    LiftRules.jsArtifacts = JQueryArtifacts
    JQueryModule.InitParam.JQuery=JQueryModule.JQuery172
    JQueryModule.init()

    CustomResourceId.init()

    // Custom 404:
    LiftRules.uriNotFound.prepend(NamedPF("404handler"){
      case (req,failure) =>
        NotFoundAsTemplate(ParsePath(List("404"),"html",true,false))
    })


    // Custom 403:

    def to403 : Box[LiftResponse] =
      for {
        session <- S.session
        req <- S.request
        template = Templates("403" :: Nil)
        response <- session.processTemplate(template, req, req.path, 403)
      } yield response

    LiftRules.responseTransformers.append {
      case resp if resp.toResponse.code == 403 => to403 openOr resp
      case resp => resp
    }


    // More general status pages:

    /*
    LiftRules.responseTransformers.append {
      case Customised(resp) => resp
      case resp => resp
    }

    object Customised {
      val definedPages = 403 :: 500 :: Nil

      def unapply(resp: LiftResponse) : Option[LiftResponse] =
        definedPages.find(_ == resp.toResponse.code).flatMap(toResponse)

      def toResponse(status: Int) : Box[LiftResponse] =
        for {
          session <- S.session
          req <- S.request
          template = Templates(status.toString :: Nil)
          response <- session.processTemplate(template, req, req.path, status)
      } yield response

    }
    */

  }
}
