/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gsd.cdl.smt.yices

import gsd.cdl.formula.GExpression
import gsd.cdl.smt._
import gsd.cdl.smt.util._
import scala.collection.mutable.Map
import scala.collection.mutable.ListBuffer

object YicesStringDataType extends SmtDataType {

  private var charEncodingLength:Int = 16;
  private var maxStringLength:Int = 500 ;
  private var map:Map[Char, Int] = null
  private var ignore =  Set[Char]()

  def getCharEncodingLength() = charEncodingLength ;

  def getMaxLength() = maxStringLength

  /*
   * Set the  maximum length of the number of characters that can
   * occur in any given string.
   *
   * @param maxStringLength the maximum limit of characters in any
   *                        given string.
   */

  def setMaxLength(maxStringLength:Int) : SmtDataType = {
    this.maxStringLength = maxStringLength
    return this
  }

  def getIgnoreSet() : Set[Char] = ignore
  def setIgnoreSet(ignore:Set[Char]) = this.ignore = ignore

  def getEncodingMap() = map

  def setEncodingMap(map:Map[Char, Int]) : SmtDataType = {
    this.map = map
    charEncodingLength = Math.ceil(Math.log(map.size + 1)/Math.log(2)).asInstanceOf[Int]
    return this
  }

  /*
   * Encodes a given string using the encoding map set by calling
   * <code>setEncodingMap</code> to a binary sequence.
   *
   * @param str the string to be encoded as a binary string.
   *
   * @return a string represented as a binary sequence.
   */

  def encodeStringAsBinary(str:String) : String = {
    var codedChars = new ListBuffer[String]

    for(c <- flat(str))
       codedChars.append(StringUtils.encodeAsBinaryString(c, getCharEncodingLength, map))

    var bin = new StringBuilder(codedChars.size * getCharEncodingLength)
    codedChars.foreach(cc => bin.append(cc))
    bin.toString
  }

  def pad(str:String) = str + new String(Array.fill(getMaxSizeInBits - str.length){ '0'})

  /*def encodeStringAsDecimal(str:String) : BigInteger = {
    val bin = encodeStringAsBinary(str)
    println("bin : " + bin)
    var expoent = bin.length - 1
    var res = new BigInteger("0")

    for(bit <- bin) {
      if (bit != '0') {
        res = res.add(new BigInteger("2").pow(expoent))
        println("res: " + res)
      }
      expoent -= 1
    }

    return res
  }*/

  def flat(str:String) : String = StringUtils.flat(str, ignore)

  def getMaxStrLength(exps:List[GExpression]) = StringUtils.getMaxStrLengthAmongAll(exps, ignore)

  def getLengthInBits(str:String) : Int = {
    var count = 0
    for(c <- str)
      if (! ignore.contains(c))
        count += 1

    return count * getCharEncodingLength
  }

  def pad(str:String, n:Int) : String = new String(Array.fill(n){ '0'}) + str

  def getMaxSizeInBits() : Int = getMaxLength * getCharEncodingLength

  def emptyString : String = pad("", getMaxSizeInBits)

  override def typeName() : String =
		"(bitvector " + getMaxSizeInBits + ")"
}
