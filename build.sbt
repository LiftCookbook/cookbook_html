name := "Cookbook Html"

version := "1.0.2"

organization := "cookbook.liftweb.net"

scalaVersion := "2.11.2"

resolvers ++= Seq(
  "snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "releases"  at "https://oss.sonatype.org/content/repositories/releases"
)

jetty()

parallelExecution in Test := false

scalacOptions ++= Seq("-deprecation", "-unchecked")

libraryDependencies ++= {
  val liftVersion = "2.6-M4"
  Seq(
    "net.liftweb"     %% "lift-webkit"            % liftVersion,
    "net.liftmodules" %% "lift-jquery-module_2.6" % "2.8",
    "net.liftmodules" %% "textile_2.6"            % "1.3-SNAPSHOT",
    "ch.qos.logback"  %  "logback-classic"        % "1.0.6",
    "org.specs2"      %% "specs2"                 % "2.4.9"     % "test",
    "net.liftweb"     %% "lift-testkit"           % liftVersion % "test",
    "javax.servlet"   %  "javax.servlet-api"      % "3.0.1"     % "test"
  )
}

unmanagedResourceDirectories in Test <+= (baseDirectory) { _ / "src/main/webapp" }
