import AssemblyKeys._

assemblySettings

name := "LetterpressBot"

version := "0.1"

scalaVersion := "2.9.2"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

scalacOptions ++= Seq("-unchecked", "-deprecation")

libraryDependencies ++= Seq(
	"junit" % "junit" % "4.8.1" % "test",
	"org.scalatest" %% "scalatest" % "1.8" % "test"
	)

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

EclipseKeys.withSource := true