/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gsd.cdl.smt.yices

import org.junit._
import Assert._
import scala.collection.mutable.HashMap

class YicesStringDataTypeTest extends YicesTest {

  @Test
  def padTest() {
    YicesStringDataType.setMaxLength(4)
    assertEquals(YicesStringDataType.pad(YicesStringDataType.encodeStringAsBinary("abc")), new StringBuffer("0000011100110110").reverse.toString)
    assertEquals(YicesStringDataType.pad(YicesStringDataType.encodeStringAsBinary("cde")), new StringBuffer("0000100110000111").reverse.toString)

  }

  @Test
  def getCharEncodingLengthTest() = {

    assertEquals(YicesStringDataType.getCharEncodingLength, 4)

    var map = new HashMap[Char, Int] ;
    map += ('l' -> 1)
    map += ('i' -> 2)
    map += ('b' -> 3)
    map += ('s' -> 4)
    map += ('o' -> 5)
    YicesStringDataType.setEncodingMap(map)
    assertEquals(YicesStringDataType.getCharEncodingLength, 3)

    map = new HashMap[Char, Int] ;
    map += ('a' -> 1)
    map += ('b' -> 2)
    map += ('c' -> 3)
    map += ('d' -> 4)
    map += ('e' -> 5)
    map += ('f' -> 6)
    map += ('g' -> 7)
    map += ('h' -> 8)
    map += ('i' -> 9)
    map += ('j' -> 10)
    map += ('k' -> 11)
    map += ('l' -> 12)
    map += ('m' -> 13)
    map += ('n' -> 14)
    map += ('o' -> 15)
    map += ('p' -> 16)
    map += ('q' -> 17)

    YicesStringDataType.setEncodingMap(map)
    assertEquals(YicesStringDataType.getCharEncodingLength, 5)
  }

  @Test
  def getLengthInBitsTest() = {
    assert(YicesStringDataType.getLengthInBits("libx.so") == "libx.so".length * 4)
  }

  @Test
  def  encodeStringAsBinaryTest() {
      /*
       * l 0001
       * i 0010
       * b 0011
       * x 1010
       * x 1010
       * x 1010
       * . 1100
       * s 0100
       * o 0101
       *
       */
      val encodedStr1 = YicesStringDataType.encodeStringAsBinary("libxxx.so")
      assertEquals(encodedStr1, new StringBuffer("010101001100101010101010001100100001").reverse.toString())

      /*
       * l 0001
       * i 0010
       * b 0011
       * a 0110
       * b 0011
       * c 0111
       * . 1100
       * s 0100
       * o 0101
       *
       */

      val encodedStr2 = YicesStringDataType.encodeStringAsBinary("libabc.so")
      assertEquals(encodedStr2, new StringBuffer("010101001100011100110110001100100001").reverse.toString())

      /*
       * l 0001
       * i 0010
       * b 0011
       * c 0111
       * d 1000
       * e 1001
       * . 1100
       * s 0100
       * o 0101
       *
       */
      val encodedStr3 = YicesStringDataType.encodeStringAsBinary("libcde.so")
      assertEquals(encodedStr3, new StringBuffer("010101001100100110000111001100100001").reverse.toString())
  }

  /*@Test
  def  encodeStringAsDecimalTest() {
    assertEquals(YicesStringDataType.encodeStringAsDecimal("abc"), new java.math.BigInteger("1846"))
    assertEquals(YicesStringDataType.encodeStringAsDecimal("cde"), new java.math.BigInteger("2439"))
  }*/
}
