name := "FlyServer"

version := "1.0"

lazy val `flyserver` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

resolvers ++= Seq(
  "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
  "Websudos" at "https://dl.bintray.com/websudos/oss-releases/"
)

libraryDependencies ++= Seq( jdbc , cache , ws   , specs2 % Test )

libraryDependencies ++= Seq(
  "com.websudos" % "phantom-dsl_2.11" % "1.26.6"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

routesGenerator := InjectedRoutesGenerator