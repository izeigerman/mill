package mill.integration

import ammonite.ops._
import utest._

object JawnTests extends IntegrationTestSuite("MILL_JAWN_REPO", "jawn") {
  val tests = Tests{
    initWorkspace()

    def check(scalaVersion: String) = {
      val firstCompile = eval(s"jawn[$scalaVersion].Parser.test")

      assert(
        firstCompile,
        ls.rec(workspacePath).exists(_.last == "AsyncParser.class"),
        ls.rec(workspacePath).exists(_.last == "CharBuilderSpec.class")
      )

      for(scalaFile <- ls.rec(workspacePath).filter(_.ext == "scala")){
        write.append(scalaFile, "\n}")
      }

      val brokenCompile = eval(s"jawn[$scalaVersion].Parser.test")

      assert(!brokenCompile)
    }

    'scala21111 - check("2.11.11")
    'scala2123 - check("2.12.3")
  }
}
