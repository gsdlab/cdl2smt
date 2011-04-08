/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gsd.cdl.smt.yices.debugutils

import gsd.cdl.formula._
import gsd.cdl.smt.yices.propagator.GVariableSubstring

object PrettyPrinter {
  
  private def printSpace(i:Int) = new String(Array.fill(i){ ' '})

  def traverseExp(gexp:GExpression, i:Int) {
    gexp match {

      case GIntLiteral(value) =>
        println(printSpace(i) + "GIntLiteral: " + value)

      case GEnumLiteral(originalValue, realValue) =>
        println(printSpace(i) + "GStringLiteral original value: " + originalValue + ", real value:" + realValue)

      case GStringLiteral(value) =>

        println(printSpace(i) + "GStringLiteral: " + value)

      case GBoolLiteral(value)  =>
        println(printSpace(i) + "GBoolLiteral: " + value)

      case GLiteral(value) =>
        println(printSpace(i) + "GLiteral: " + value)

      case GVariable(id)  =>
        println(printSpace(i) + "GVariable: " + id)

      case GNot(exp) =>
        println(printSpace(i) + "GNot")
        traverseExp(exp, i + 1)

      case GConditional(cond, pass, fail) =>
        println(printSpace(i) + "GConditional")
        println(printSpace(i) + "cond: ")
        traverseExp(cond, i + 1)

        println(printSpace(i) + "pass: ")
        traverseExp(pass, i + 1)

        println(printSpace(i) + "fail: ")
        traverseExp(fail, i + 1)

      case GOr(exp1, exp2)  =>
        println(printSpace(i) + "GOr")

        println(printSpace(i) + "exp1: ")
        traverseExp(exp1, i + 1)

        println(printSpace(i) + "exp2: ")
        traverseExp(exp2, i + 1)

      case GAnd(exp1, exp2)  =>
        println(printSpace(i) + "GAnd")

        println(printSpace(i) + "exp1: ")
        traverseExp(exp1, i + 1)

        println(printSpace(i) + "exp2: ")
        traverseExp(exp2, i + 1)


      case GEq(exp1, exp2)  =>
        println(printSpace(i) + "GEq")

        println(printSpace(i) + "exp1: ")
        traverseExp(exp1, i + 1)

        println(printSpace(i) + "exp2: ")
        traverseExp(exp2, i + 1)

      case GLessThan(exp1, exp2)  =>
        println(printSpace(i) + "GLessThan")

        println(printSpace(i) + "exp1: ")
        traverseExp(exp1, i + 1)

        println(printSpace(i) + "exp2: ")
        traverseExp(exp2, i + 1)

      case GGreaterThan(exp1, exp2)  =>
        println(printSpace(i) + "GGreaterThan")

        println(printSpace(i) + "exp1: ")
        traverseExp(exp1, i + 1)

        println(printSpace(i) + "exp2: ")
        traverseExp(exp2, i + 1)


      case GLessEqThan(exp1, exp2) =>
        println(printSpace(i) + "GLessEqThan")

        println(printSpace(i) + "exp1: ")
        traverseExp(exp1, i + 1)

        println(printSpace(i) + "exp2: ")
        traverseExp(exp2, i + 1)

      case GGreaterEqThan(exp1, exp2) =>
        println(printSpace(i) + "GGreaterEqThan")

        println(printSpace(i) + "exp1: ")
        traverseExp(exp1, i + 1)

        println(printSpace(i) + "exp2: ")
        traverseExp(exp2, i + 1)

      case GPlus(exp1, exp2) =>
        println(printSpace(i) + "GPlus")

        println(printSpace(i) + "exp1: ")
        traverseExp(exp1, i + 1)

        println(printSpace(i) + "exp2: ")
        traverseExp(exp2, i + 1)


      case GMinus(exp1, exp2) =>
        println(printSpace(i) + "GMinus")

        println(printSpace(i) + "exp1: ")
        traverseExp(exp1, i + 1)

        println(printSpace(i) + "exp2: ")
        traverseExp(exp2, i + 1)


      case GTimes(exp1, exp2) =>
        println(printSpace(i) + "GTimes")

        println(printSpace(i) + "exp1: ")
        traverseExp(exp1, i + 1)

        println(printSpace(i) + "exp2: ")
        traverseExp(exp2, i + 1)


      case GDivide(exp1, exp2) =>
        println(printSpace(i) + "GDivide")

        println(printSpace(i) + "exp1: ")
        traverseExp(exp1, i + 1)

        println(printSpace(i) + "exp2: ")
        traverseExp(exp2, i + 1)


      case GMod(exp1, exp2) =>
        println(printSpace(i) + "GMod")

        println(printSpace(i) + "exp1: ")
        traverseExp(exp1, i + 1)

        println(printSpace(i) + "exp2: ")
        traverseExp(exp2, i + 1)


      case GDot(exp1, exp2) =>
        println(printSpace(i) + "GDot")

        println(printSpace(i) + "exp1: ")
        traverseExp(exp1, i + 1)

        println(printSpace(i) + "exp2: ")
        traverseExp(exp2, i + 1)

      case GVariableSubstring(exp1, exp2) =>
        println(printSpace(i) + "GVariableSubstring")

        println(printSpace(i) + "exp1: ")
        traverseExp(exp1, i + 1)

        println(printSpace(i) + "exp2: ")
        traverseExp(exp2, i + 1)

      case GSubString(exp1, exp2) =>
        println(printSpace(i) + "GSubString")

        println(printSpace(i) + "exp1: ")
        traverseExp(exp1, i + 1)

        println(printSpace(i) + "exp2: ")
        traverseExp(exp2, i + 1)


      case GBoolFunc(exp) =>
        println(printSpace(i) + "GBoolFunc")

        println(printSpace(i) + "exp: ")
        traverseExp(exp, i + 1)


      case GOptionalBoolFunc(exp) =>
        println(printSpace(i) + "GOptionalBoolFunc")
        println(printSpace(i) + "exp: ")
        traverseExp(exp, i + 1)
    }
  }
}
