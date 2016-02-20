import AssemblyKeys._

assemblySettings

name := "LetterpressBot"

version := "1.1"

scalaVersion := "2.11.7"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "sonatype releases" at "https://oss.sonatype.org/content/repositories/releases/"

resolvers += "imagej releases" at "http://maven.imagej.net/content/repositories/releases/"

scalacOptions ++= Seq("-unchecked", "-deprecation")

libraryDependencies ++= Seq(
	"junit" % "junit" % "4.8.1" % "test",
	"org.scalatest" %% "scalatest" % "2.2.6" % "test",
	"org.twitter4j" % "twitter4j-core" % "3.0.5",
	"org.twitter4j" % "twitter4j-stream" % "3.0.5",
	"com.typesafe" % "config" % "1.0.0",
	"ch.qos.logback" % "logback-classic" % "1.0.9",
	"org.slf4s" %% "slf4s-api" % "1.7.12",
	"net.imagej" % "ij" % "1.47i"
	)
