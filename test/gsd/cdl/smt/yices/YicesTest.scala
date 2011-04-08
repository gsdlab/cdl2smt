/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gsd.cdl.smt.yices

import org.junit._
import Assert._
import org.scalatest.junit._

@Ignore
class YicesTest extends JUnitSuite {

  protected def MAX_LENGTH = 36
  protected def SEP = System.getProperty("file.separator")
  protected def DIR = System.getProperty("user.dir") + SEP + "test" + SEP + "gsd" + SEP + "cdl" + SEP + "smt" + SEP + "yices" + SEP

  private def DEFAULT_MAP = scala.collection.mutable.Map[Char, Int](
        ('l' -> 1),
        ('i' -> 2),
        ('b' -> 3),
        ('s' -> 4),
        ('o' -> 5),
        ('a' -> 6),
        ('c' -> 7),
        ('d' -> 8),
        ('e' -> 9),
        ('x' -> 10),
        (' ' -> 11),
        ('.' -> 12)
  )

  private var initialDir:String = null ;

  @Before
  def setUp: Unit =  {
    YicesStringDataType.setEncodingMap(DEFAULT_MAP)
  }

  @After
  def tearDown: Unit = {
    gsd.cdl.formula.TypeGraph.clear()
    YicesStringDataType.setEncodingMap(DEFAULT_MAP)
  }

  def testFile(str:String) = DIR + str

}
