/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gsd.cdl.smt.yices

import gsd.cdl.smt.yices.propagator.GVariableSubstring
import java.io.StringWriter

import gsd.cdl.formula._
import gsd.cdl.smt._
import gsd.cdl.smt.util.OutputWriter
import gsd.cdl.formula.TypeGraph._

/*
 * The <code> TranslateToSmtConstraint </code>  object translates a given
 * expression into a contraint written in the Yices input language.
*/
object YicesConstraint extends SmtConstraint {
  /*
   * Translation bit mask.
   */
  private val ROOT_LEVEL = 1
  private val PADDING = 2

  def apply(exp:GExpression) : String = translateGExpression(exp, ROOT_LEVEL)

  private def translateGExpression(gexp:GExpression, params:Int) : String = {

     gexp match {

      case GIntLiteral(value) => translateLiteral(value, params)

      case GEnumLiteral(originalValue, realValue) =>
        gsd.cdl.formula.Utils.guardEnumValue(realValue)

      case GStringLiteral(value) =>
        translateGStringLiteral(value, params)

      case GBoolLiteral(value)  =>
         translateGBoolLiteral(value, params)

      case GLiteral(value) => translateLiteral(value, params)

      case GVariable(id)  =>
        println(id.toString)
        id.toString

      case GNot(exp) =>
         var res = "(not " + translateGExpression(exp, params & ~ROOT_LEVEL) + ")"
         println(res)
         res

      case GConditional(cond, pass, fail) =>
         var res = "(if " +
         translateGExpression(cond, params & ~ROOT_LEVEL) + " " +
         translateGExpression(pass, params & ~ROOT_LEVEL) + " " +
         translateGExpression(fail, params & ~ROOT_LEVEL) + ")"
         println(res)
         res

      case GOr(exp1, exp2)  =>
        translateBinaryOperator("or", exp1, exp2, params)

      case GAnd(exp1, exp2)  =>
         translateBinaryOperator("and", exp1, exp2, params)

      case GEq(exp1, exp2)  =>
        translateGEq(exp1, exp2, params)

      case GLessThan(exp1, exp2)  =>
         translateBinaryOperator("<", exp1, exp2, params)

      case GGreaterThan(exp1, exp2)  => ""
         translateBinaryOperator(">", exp1, exp2, params)

      case GLessEqThan(exp1, exp2) =>
         translateBinaryOperator("<=", exp1, exp2, params)

      case GGreaterEqThan(exp1, exp2) =>
         translateBinaryOperator(">=", exp1, exp2, params)

      case GPlus(exp1, exp2) =>
         translateBinaryOperator("+", exp1, exp2, params)

      case GMinus(exp1, exp2) =>
         translateBinaryOperator("-", exp1, exp2, params)

      case GTimes(exp1, exp2) =>
         translateBinaryOperator("*", exp1, exp2, params)

      case GDivide(exp1, exp2) =>
         translateBinaryOperator("/", exp1, exp2, params)

      case GMod(exp1, exp2) =>
         translateBinaryOperator("%", exp1, exp2, params)

      /* Note that GSubString should not be found!!! */
      case GVariableSubstring(exp1, exp2) =>
        translateGVariableSubstring(exp1, exp2.asInstanceOf[GStringLiteral], params)

      case GBoolFunc(exp) =>  translateGBoolFunc(exp, params)

      case GOptionalBoolFunc(exp) =>  /* Should never occur!! (deprecated) */
        println("Warning: found an instance of GOptionalBoolFunc- ignoring it!")
        ""

      case _ => throw new Exception("Unknown GExpression type: " + gexp.getClass)
     }
   }

   def translateGEq(exp1:GExpression, exp2:GExpression, params:Int) : String = {
        if ((exp1.isInstanceOf[GVariable] && exp1.asInstanceOf[GVariable].isEnum)) {
          translateEnumGEQ(exp1.asInstanceOf[GVariable], exp2, params)
        } else if ((exp2.isInstanceOf[GVariable] && exp2.asInstanceOf[GVariable].isEnum)) {
          translateEnumGEQ(exp2.asInstanceOf[GVariable], exp1, params)
        } else {
          var res = "(= " +
          translateGExpression(exp1, params & ~ROOT_LEVEL | PADDING) + " " +
          translateGExpression(exp2, params & ~ROOT_LEVEL | PADDING) + ")"
          println(res)
          res
        }
    }

   /* . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

   private def translateLiteral(value:Any, params:Int) : String = {
     if ((params & ROOT_LEVEL) != ROOT_LEVEL) {
            println(value.toString)
            value.toString
         }
         else ""
    }

   /* . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

   private def translateBinaryOperator(binop:String, exp1:GExpression, exp2:GExpression, params:Int) = {
      var res = "(" +  binop + " " +
      translateGExpression(exp1, params & ~ROOT_LEVEL) + " " +
      translateGExpression(exp2, params & ~ROOT_LEVEL) + ")"
      println(res)
      res
   }

  /* . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

   private def translateGBoolFunc(exp:GExpression, params:Int) : String = {
      val expType = inferType(exp)

        if (expType == YicesStringDataType)
           return "(bv-gt " + translateGExpression(exp, params & ~ROOT_LEVEL | PADDING) + " " +
               "0b" + YicesStringDataType.emptyString +  ")"

        if (expType == YicesIntDataType)
          return "(> " + translateGExpression(exp, params & ~ROOT_LEVEL) + " 0)"

        if (expType == YicesBoolDataType)
          return "(> " + translateGExpression(exp, params & ~ROOT_LEVEL) + " 0)"

        throw new Exception("Incorrect usage of GBoolFunc: type expression cannot be " + expType.getClass + " as in " + exp)
    }

    /* . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

    /**
     * Translates GEQ expression to Yices scalar identity
     * (variable_name enum_val)
     */
    private def translateEnumGEQ(exp1: GVariable, exp2: GExpression, params:Int): String =  {
        if (!exp2.isInstanceOf[GEnumLiteral]) {
          throw new Exception("Unknown way of enum usage: " + exp2)
        }

        val realValue = exp1.asInstanceOf[GVariable].getEnums.find(exp => gsd.cdl.formula.Utils.guardEnumValue(exp.asInstanceOf[GEnumLiteral].originalValue).
               equalsIgnoreCase(exp2.asInstanceOf[GEnumLiteral].originalValue)).getOrElse(null);

        if (realValue == null) {
          throw new Exception("realValue does not exist")
        }

        var res = "(= " +
        translateGExpression(exp1, params & ~ROOT_LEVEL) + " " +
        gsd.cdl.formula.Utils.guardEnumValue(realValue.asInstanceOf[GEnumLiteral].realValue) + ")"
        res
    }

    /* . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

    def translateGBoolLiteral(value:Boolean, params:Int) : String = {
        if ((params & ROOT_LEVEL) != ROOT_LEVEL) {
           println(value.toString)
           value.toString
         }
         else {
           if (!value) {
            /* Value is known to be false at the root level. */
            value.toString
           }
          else
           /* Value is known to be true at the root level (ignore it). */
           ""
         }
    }

    /* . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

    def translateGStringLiteral(value:String, params:Int) : String = {

        /* No need to do any translation if curren level is the root level. */
        if ((params & ROOT_LEVEL) == ROOT_LEVEL)
          ""

        /**
         * The "0" string is used to signal a false value. In our translation,
         * it is equal to the empty string (lambda), which is encoded by
         * a sequence of zeros (the size of the sequence corresponds to the size
         * of a string block, as returned by YicesStringDataType.getMaxSizeInBits.
         */

         if (value.size == 0 || value.equals("0"))
             return "0b" + YicesStringDataType.pad("", YicesStringDataType.getMaxSizeInBits)


        /*
         * The output of the literal string in the form of a plain binary string
         * avoids integer overflow resulted by Yices, since it discards
         * the use of the size argument needed by mk-bv. Furthermore, it is more
         * efficient because it introduces an overhead in the translation time,
         * not in the verification time (performed by Yices).
         */

         if ((params & PADDING) == PADDING)
              return "0b" + YicesStringDataType.pad(YicesStringDataType.encodeStringAsBinary(value))
         else
              return "0b" + YicesStringDataType.encodeStringAsBinary(value)

         throw new Exception("Unknown translation for string literal " + value)
    }

    /* . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

    def translateGVariableSubstring(exp1:GExpression, exp2:GExpression, params:Int) : String = {

      exp1 match {

        case GVariable(id1) =>

          exp2 match {

            case GStringLiteral(value2) => {
              val binExp2 = YicesStringDataType.encodeStringAsBinary(value2)

              if (binExp2.length == YicesStringDataType.getMaxSizeInBits)
                "(= " + id1 + " 0b" + binExp2 + ")"
              else
                translateSubstringInterval(id1, binExp2)
            }

            case _ =>
              throw new Exception("Unexpected expression type for the second operand of a GSubstring: " + exp2.getClass)
          }
        case _ => throw new Exception("Unexpected expression type for the first operand of a GSubstring: " + exp1.getClass)
      }
  }

  def translateSubstringInterval(exp1:String, exp2:String) : String = {

      var begin = 0
      val out = new OutputWriter(new StringWriter())
      var end = exp2.length - 1

      out.println()
      out.increaseIdentation

      out.println("(and")
      out.increaseIdentation

      out.println("(bv-gt")
      out.increaseIdentation
      out.println(exp1)
      out.println("0b" + YicesStringDataType.pad(exp2))
      out.decreaseIdentation
      out.println(")")

      out.println("(or ")
      out.increaseIdentation
      do {

         out.println("(=")
         out.increaseIdentation

         out.println("(bv-extract " + end + " " + begin + " " +  exp1 + ")")
         out.println("0b" + exp2)
         out.decreaseIdentation
         out.println(")")

         begin += YicesStringDataType.getCharEncodingLength
         end = end + YicesStringDataType.getCharEncodingLength

      } while(end < YicesStringDataType.getMaxSizeInBits)

      out.decreaseIdentation
      out.println(")") // End-or

      out.decreaseIdentation
      out.println(")") // End-and


      out.getWriter.flush
      out.getWriter.asInstanceOf[StringWriter].toString
    }

    /* . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

    private def inferType(gexp:GExpression) : SmtDataType = {

       gexp match {

        case GIntLiteral(value) => YicesIntDataType

        case GEnumLiteral(originalValue, realValue) => YicesIntDataType

        case GStringLiteral(value) => YicesStringDataType

        case GBoolLiteral(value)  => YicesBoolDataType

        case GVariable(id)  =>
          gexp.asInstanceOf[GVariable].getType match {

            case StringType     => YicesStringDataType

            case BoolType       => YicesBoolDataType

            case IntType       => YicesIntDataType

            case DisjunctiveType(types) => YicesIntDataType
          }
        case GNot(exp) => YicesBoolDataType

        case GConditional(cond, pass, fail) =>
          val typePass = inferType(pass)
          val typeFail = inferType(fail)

          if (typePass != typeFail)
            throw new Exception("Error in conditional statement: pass (" + typePass.getClass + ") does not match fail type (" + typeFail.getClass + ")!")

          typePass

        case GOr(exp1, exp2)  => YicesBoolDataType
        case GAnd(exp1, exp2)  => YicesBoolDataType
        case GEq(exp1, exp2)  => YicesBoolDataType
        case GLessThan(exp1, exp2)  => YicesBoolDataType
        case GGreaterThan(exp1, exp2)  => YicesBoolDataType
        case GLessEqThan(exp1, exp2) => YicesBoolDataType
        case GGreaterEqThan(exp1, exp2) => YicesBoolDataType

        case GPlus(exp1, exp2) => YicesIntDataType
        case GMinus(exp1, exp2) => YicesIntDataType
        case GTimes(exp1, exp2) => YicesIntDataType
        case GDivide(exp1, exp2) => YicesIntDataType
        case GMod(exp1, exp2) => YicesIntDataType

        case GVariableSubstring(exp1, exp2) => YicesStringDataType
        case GBoolFunc(exp) => YicesBoolDataType

        case GLiteral(value) =>
          println("Warning: found an instance of GLiteral in type inference - ignoring it!")
          null

        case GOptionalBoolFunc(exp) =>
          /* Should never occur!! (deprecated) */
          println("Warning: found an instance of GOptionalBoolFunc- ignoring it!")
          null

        case _ => throw new Exception("Unknown GExpression type.")
       }
    }
}
