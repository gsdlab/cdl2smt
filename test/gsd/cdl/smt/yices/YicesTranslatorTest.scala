/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gsd.cdl.smt.yices

import org.junit._
import Assert._

import gsd.cdl.smt.yices.debugutils._

class YicesTranslatorTest extends YicesTest {

  @Test
  def translateSample0Test() = {
    assertTrue(YicesTranslator.translate(testFile("sample0.iml"), testFile("sample0.smt")))
    assertTrue(YicesProcessExecuter.isSatisfiable(testFile("sample0.smt")))
  }

  @Test
  def translateSample1Test() = {
    assertTrue(YicesTranslator.translate(testFile("sample1.iml"), testFile("sample1.smt")))
    assertTrue(YicesProcessExecuter.isSatisfiable(testFile("sample1.smt")))
  }

  @Test
  def translateSample2Test() = {
    assertTrue(YicesTranslator.translate(testFile("sample2.iml"), testFile("sample2.smt")))
    assertTrue(YicesProcessExecuter.isSatisfiable(testFile("sample2.smt")))
  }

  @Test
  def translateSample3Test() = {
    assertTrue(YicesTranslator.translate(testFile("sample3.iml"), testFile("sample3.smt")))
    assertTrue(YicesProcessExecuter.isSatisfiable(testFile("sample3.smt")))
  }

  @Test
  def translateSample4Test() = {
    assertTrue(YicesTranslator.translate(testFile("sample4.iml"), testFile("sample4.smt")))
    assertTrue(YicesProcessExecuter.isSatisfiable(testFile("sample4.smt")))
  }

  @Test
  def atomicContradictionsTest() = {
    assertTrue(YicesTranslator.translate(testFile("atomicContradiction.iml"), testFile("atomicContradiction.smt")))
    assertTrue(YicesProcessExecuter.isSatisfiable(testFile("atomicContradiction.smt")))
  }

  @Test
  def translateDisjunctiveTest() = {
    assertTrue(YicesTranslator.translate(testFile("disjunctive.iml"), testFile("disjunctive.smt")))
    assertTrue(YicesProcessExecuter.isSatisfiable(testFile("disjunctive.smt")))
  }


  @Test
  def translateEnum1Test1() = {
    assertTrue(YicesTranslator.translate(testFile("legal1.iml"), testFile("legal1.smt")))
  }

  @Test
  def translateEnum1Test2() = {
    assertTrue(YicesTranslator.translate(testFile("enums1.iml"), testFile("enums1.smt")))
    val lines = scala.io.Source.fromFile(testFile("enums1.smt")).mkString
    // should contain strings:
    // ENUMERATION_DUPLICATE_scalar_var OPTION_1_ENUMERATION_DUPLICATE
    // and
    // ENUMERATION_scalar_var OPTION_1
    assertTrue(lines.contains("ENUMERATION_DUPLICATE_scalar_var OPTION_1_ENUMERATION_DUPLICATE"))
    assertTrue(lines.contains("ENUMERATION_scalar_var OPTION_1"))
    assertTrue(lines.contains("(define ENUMERATION_scalar_var::ENUMERATION_scalar)"))
    assertTrue(gsd.cdl.smt.yices.debugutils.YicesProcessExecuter.isSatisfiable(testFile("enums1.smt")))
    assertTrue(true)
  }

  @Test
  def translateBoolDataTest() = {
    assertTrue(YicesTranslator.translate(testFile("booldataEnums.iml"), testFile("booldataEnums.smt")))
    val lines = scala.io.Source.fromFile(testFile("booldataEnums.smt")).mkString
    assertTrue(lines.contains("ROM_MONITOR_bool_var"))
    assertTrue(YicesProcessExecuter.isSatisfiable(testFile("booldataEnums.smt")))
  }

  @Test
  def translatePcVmWareTest() = {
    YicesTranslator.translate(testFile("pc_vmWare.iml"), testFile("pc_vmWare.smt"))
     assertFalse(YicesProcessExecuter.isSatisfiable(testFile("pc_vmWare.smt")))
  }
}
