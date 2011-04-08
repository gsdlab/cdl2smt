package gsd.cdl.smt.yices

import org.scalatest.junit.JUnitSuite
import org.junit.Assert._
import org.junit.Test
import org.junit.Ignore
import java.io._

import gsd.cdl.smt.util._

class YicesProcessExecuterTest extends JUnitSuite {

  def YICES_VERSION = "1.0.29"

  @Test
  def testProcess() = {
    assertEquals(gsd.cdl.smt.yices.debugutils.YicesProcessExecuter.getResult(" --version"), YICES_VERSION);
  }
}
