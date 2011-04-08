/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gsd.cdl.smt.util

import gsd.cdl.formula._
import gsd.cdl.formula.TypeGraph._
import gsd.cdl.smt.yices.propagator.GVariableSubstring
import scala.collection.mutable.HashMap
import scala.collection.mutable.Map


object StringUtils {


  def flat(str:String, ignore:Set[Char]) : String = {
    var newstr = str
    for(c <- ignore)
      newstr = newstr.replace("" + c, "")
    return newstr
  }

 /*
  *  Gets the greatest (max) string literal length among all the
  *  expressions in the list.
  *
  *  @param exps a list of expressions
  *  @return the length of the greatest (max) string literal length or -1 if no
  *          string literals are present.
  */
 def getMaxStrLengthAmongAll(exps:List[GExpression], ignore:Set[Char]) : Int = {
   var max:Int = -1
   var e:GExpression = null
   println(e)
   for(exp <- exps) {
     val maxTemp = getMaxStrLength(exp, ignore)
     if (maxTemp > max)
       max = maxTemp
   }
   return max ;
  }

 /* . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

 /*
  *  Gets the greatest (max) string literal in the expression.
  *
  *  @param exp an expression
  *  @return the length of the greatest (max) string literal length or -1 if no
  *          string literals are present.
  */
  def  getMaxStrLength(exp:GExpression, ignore:Set[Char]) : Int = {

    var maxTemp1:Int = -1 ;
    var maxTemp2:Int = -1 ;
    var maxTemp3:Int = -1 ;

    exp match {
      case GStringLiteral(value) =>
        maxTemp1 = flat(value, ignore).length

      case GNot(exp) =>
        maxTemp1 = getMaxStrLength(exp, ignore)

      case GConditional(cond, pass, fail) =>
        maxTemp1 = getMaxStrLength(cond, ignore)
        maxTemp2 = getMaxStrLength(pass, ignore)
        maxTemp3 = getMaxStrLength(fail, ignore)

      case GOr(exp1, exp2)  =>
        maxTemp1 = getMaxStrLength(exp1, ignore)
        maxTemp2 = getMaxStrLength(exp2, ignore)

      case GAnd(exp1, exp2)  =>
        maxTemp1 = getMaxStrLength(exp1, ignore)
        maxTemp2 = getMaxStrLength(exp2, ignore)

      case GEq(exp1, exp2)  =>
        maxTemp1 = getMaxStrLength(exp1, ignore)
        maxTemp2 = getMaxStrLength(exp2, ignore)

      case GLessThan(exp1, exp2)  =>
        maxTemp1 = getMaxStrLength(exp1, ignore)
        maxTemp2 = getMaxStrLength(exp2, ignore)

      case GGreaterThan(exp1, exp2)  => ""
        maxTemp1 = getMaxStrLength(exp1, ignore)
        maxTemp2 = getMaxStrLength(exp2, ignore)

      case GLessEqThan(exp1, exp2) =>
        maxTemp1 = getMaxStrLength(exp1, ignore)
        maxTemp2 = getMaxStrLength(exp2, ignore)

      case GGreaterEqThan(exp1, exp2) =>
        maxTemp1 = getMaxStrLength(exp1, ignore)
        maxTemp2 = getMaxStrLength(exp2, ignore)

      case GPlus(exp1, exp2) =>
        maxTemp1 = getMaxStrLength(exp1, ignore)
        maxTemp2 = getMaxStrLength(exp2, ignore)

      case GMinus(exp1, exp2) =>
        maxTemp1 = getMaxStrLength(exp1, ignore)
        maxTemp2 = getMaxStrLength(exp2, ignore)

      case GTimes(exp1, exp2) =>
        maxTemp1 = getMaxStrLength(exp1, ignore)
        maxTemp2 = getMaxStrLength(exp2, ignore)

      case GDivide(exp1, exp2) =>
        maxTemp1 = getMaxStrLength(exp1, ignore)
        maxTemp2 = getMaxStrLength(exp2, ignore)

      case GMod(exp1, exp2) =>
        maxTemp1 = getMaxStrLength(exp1, ignore)
        maxTemp2 = getMaxStrLength(exp2, ignore)

      case GDot(exp1, exp2) =>
        maxTemp1 = getMaxStrLength(exp1, ignore)
        maxTemp2 = getMaxStrLength(exp2, ignore)

      case GSubString(exp1, exp2) =>
        maxTemp1 = getMaxStrLength(exp1, ignore)
        maxTemp2 = getMaxStrLength(exp2, ignore)

      case GVariableSubstring(exp1, exp2) =>
        maxTemp1 = getMaxStrLength(exp1, ignore)
        maxTemp2 = getMaxStrLength(exp2, ignore)

      case GBoolFunc(exp) =>
        maxTemp1 = getMaxStrLength(exp, ignore)

      case GOptionalBoolFunc(exp) =>
        maxTemp1 = getMaxStrLength(exp, ignore)

      case _ => /* Do nothing. */
   }
   return Math.max(Math.max(maxTemp1, maxTemp2), maxTemp3)
 }

 /* . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/
 /*
  *  Gets the characters that belong to the string literals given a list of
  *  expressions.
  *
  *  @param exps list of expressions
  *  @return a map containing the characters that comprise the expressions'
  *          string literals that occur in them.
  */
  def  getStringLiteralsEncodingMap(exps:List[GExpression], ignore:Set[Char]) : Map[Char, Int] = {
    val map:Map[Char, Int] = new HashMap[Char, Int]

    for(exp <- exps)
      getStringLiteralsEncodingMap(exp, map, ignore)

    return map
  }

 /* . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/
 /*
  *  Gets the characters that belong to the string literals given an expression.
  *
  *  @param exp an expression
  *  @return the character set, encoded as a list, containing the characters
  *              that comprise the expression's string literals that occur in
  *              it (such encoding start from 1).
  */
  private def  getStringLiteralsEncodingMap(exp:GExpression, map:Map[Char, Int], ignore:Set[Char]) : Unit = {

    exp match {
      case GStringLiteral(value) =>
        flat(value, ignore).foreach(
          c => if (!map.contains(c)) map.put(c, map.size + 1)
        )

      case GNot(exp) =>
        getStringLiteralsEncodingMap(exp, map, ignore)

      case GConditional(cond, pass, fail) =>
        getStringLiteralsEncodingMap(cond, map, ignore)
        getStringLiteralsEncodingMap(pass, map, ignore)
        getStringLiteralsEncodingMap(fail, map, ignore)

      case GOr(exp1, exp2)  =>
        getStringLiteralsEncodingMap(exp1, map, ignore)
        getStringLiteralsEncodingMap(exp2, map, ignore)

      case GAnd(exp1, exp2)  =>
        getStringLiteralsEncodingMap(exp1, map, ignore)
        getStringLiteralsEncodingMap(exp2, map, ignore)

      case GEq(exp1, exp2)  =>
        getStringLiteralsEncodingMap(exp1, map, ignore)
        getStringLiteralsEncodingMap(exp2, map, ignore)

      case GLessThan(exp1, exp2)  =>
        getStringLiteralsEncodingMap(exp1, map, ignore)
        getStringLiteralsEncodingMap(exp2, map, ignore)

      case GGreaterThan(exp1, exp2)  =>
        getStringLiteralsEncodingMap(exp1, map, ignore)
        getStringLiteralsEncodingMap(exp2, map, ignore)

      case GLessEqThan(exp1, exp2) =>
        getStringLiteralsEncodingMap(exp1, map, ignore)
        getStringLiteralsEncodingMap(exp2, map, ignore)

      case GGreaterEqThan(exp1, exp2) =>
        getStringLiteralsEncodingMap(exp1, map, ignore)
        getStringLiteralsEncodingMap(exp2, map, ignore)

      case GPlus(exp1, exp2) =>
        getStringLiteralsEncodingMap(exp1, map, ignore)
        getStringLiteralsEncodingMap(exp2, map, ignore)

      case GMinus(exp1, exp2) =>
        getStringLiteralsEncodingMap(exp1, map, ignore)
        getStringLiteralsEncodingMap(exp2, map, ignore)

      case GTimes(exp1, exp2) =>
        getStringLiteralsEncodingMap(exp1, map, ignore)
        getStringLiteralsEncodingMap(exp2, map, ignore)

      case GDivide(exp1, exp2) =>
        getStringLiteralsEncodingMap(exp1, map, ignore)
        getStringLiteralsEncodingMap(exp2, map, ignore)

      case GMod(exp1, exp2) =>
        getStringLiteralsEncodingMap(exp1, map, ignore)
        getStringLiteralsEncodingMap(exp2, map, ignore)

      case GDot(exp1, exp2) =>
        getStringLiteralsEncodingMap(exp1, map, ignore)
        getStringLiteralsEncodingMap(exp2, map, ignore)

      case GVariableSubstring(exp1, exp2) =>
        getStringLiteralsEncodingMap(exp1, map, ignore)
        getStringLiteralsEncodingMap(exp2, map, ignore)

      //case GSubString(exp1, exp2) =>
        //getStringLiteralsEncodingMap(exp1, map, ignore)
        //getStringLiteralsEncodingMap(exp2, map, ignore)

      case GBoolFunc(exp) =>
        getStringLiteralsEncodingMap(exp, map, ignore)

      case GOptionalBoolFunc(exp) =>
        getStringLiteralsEncodingMap(exp, map, ignore)

      case _ => /* Do nothing. */
   }
 }

 /* . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

 /*
  *  Returns a given character as a binary string.
  *
  *  @param c a character.
  *  @param maxBits the maximum amount of bits to be used.
  *  @param map a mapping from a character to its corresponding integer value.
  *
  *  @return n encoded as a binary string.
  */
  def encodeAsBinaryString(c:Char, maxBits:Int, map:Map[Char, Int]) : String =  {

    val binary:Array[Char] = new  Array[Char](maxBits);
    var pos:Int = 0
    var x:Int = map(c)


    while(x >= 2) {
      if (x % 2 == 1)
        binary(pos) = '1'
      else
        binary(pos) = '0'
      x = x / 2
      pos += 1
    }

    if (x == 1) {
      binary(pos) = '1'
      pos += 1
    }

    while (pos < maxBits) {
      binary(pos) = '0'
      pos += 1
    }

    return new String(binary)
  }
}
