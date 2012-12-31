import AssemblyKeys._

assemblySettings

name := "LetterpressBot"

version := "0.1"

scalaVersion := "2.9.2"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

scalacOptions ++= Seq("-unchecked", "-deprecation")

libraryDependencies ++= Seq(
	"junit" % "junit" % "4.8.1" % "test",
	"org.scalatest" %% "scalatest" % "1.8" % "test",
	"org.twitter4j" % "twitter4j-core" % "3.0.3",
	"org.twitter4j" % "twitter4j-stream" % "3.0.3",
	"com.typesafe.akka" % "akka-actor" % "2.0.4",
	"com.typesafe" % "config" % "1.0.0",
	"ch.qos.logback" % "logback-classic" % "1.0.9",
	"com.weiglewilczek.slf4s" % "slf4s_2.9.1" % "1.0.7"
	)

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

EclipseKeys.withSource := true