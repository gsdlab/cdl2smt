import sbt._
import java.io._
import Process._

class Build(info: ProjectInfo) extends DefaultProject(info) {

	override def mainScalaSourcePath = "src"
	override def testScalaSourcePath = "test"
	override def testResourcesPath = "test"

	val scalatest = "org.scalatest" % "scalatest" % "1.0" % "test"

//	override def mainClass = Some("gsd.cdl.smt.tests.IMLToSMTSpec")
	override def mainClass = Some("gsd.cdl.smt.tests.Translate")

}
