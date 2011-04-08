/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gsd.cdl.smt.yices.propagator

import gsd.cdl.formula.GAnd
import gsd.cdl.formula.GBoolFunc
import gsd.cdl.formula.GBoolLiteral
import gsd.cdl.formula.GConditional
import gsd.cdl.formula.GDivide
import gsd.cdl.formula.GDot
import gsd.cdl.formula.GEq
import gsd.cdl.formula.GExpression
import gsd.cdl.formula.GGreaterEqThan
import gsd.cdl.formula.GGreaterThan
import gsd.cdl.formula.GLessEqThan
import gsd.cdl.formula.GLessThan

import gsd.cdl.formula.GMinus
import gsd.cdl.formula.GMod
import gsd.cdl.formula.GNot
import gsd.cdl.formula.GOptionalBoolFunc
import gsd.cdl.formula.GOr
import gsd.cdl.formula.GPlus
import gsd.cdl.formula.GStringLiteral
import gsd.cdl.formula.GSubString
import gsd.cdl.formula.GTimes
import gsd.cdl.formula.GVariable
import scala.collection.immutable.List

object StringSimplifier {

  def apply(gexps:List[GExpression]) : List[GExpression] = simplifyAll(gexps)

  private def simplifyAll(exps:List[GExpression]) : List[GExpression] =
    if (exps == Nil)
      Nil
    else
      StringSimplifier(exps.head) :: simplifyAll(exps.tail)

  def apply(gexp:GExpression) : GExpression = {

     /* Continues in this loop until a fix point is reached. */
     var simplification:(GExpression, Boolean) = (gexp, false)
     do {
       simplification = simplify(simplification._1)
     } while(simplification._2) ;

     println("****************************************************")
     println("Simplification process")
     println("Expression changed? " + (if (gexp.equals(simplification._1)) "no" else "yes"))
     println("Original expression: " + gexp)
     println("Simplification:      " + simplification._1)
     println("****************************************************")

     return simplification._1
  }

  private def simplify(gexp:GExpression) : (GExpression, Boolean) = {

    gexp match {

      case GNot(exp) => simplifyGNot(exp)

      case GConditional(cond, pass, fail) => simplifyGConditional(cond, pass, fail)

      case GOr(exp1, exp2)  =>  simplifyGOr(exp1, exp2)

      case GAnd(exp1, exp2)  => simplifyGAnd(exp1, exp2)

      case GEq(exp1, exp2)  =>
        val sexp1 = simplify(exp1)
        val sexp2 = simplify(exp2)
        (new GEq(sexp1._1, sexp2._1), sexp1._2 || sexp2._2)

      case GLessThan(exp1, exp2)  =>
        val sexp1 = simplify(exp1)
        val sexp2 = simplify(exp2)
        (new GLessThan(sexp1._1, sexp2._1), sexp1._2 || sexp2._2)

      case GGreaterThan(exp1, exp2)  =>
        val sexp1 = simplify(exp1)
        val sexp2 = simplify(exp2)
        (new GGreaterThan(sexp1._1, sexp2._1), sexp1._2 || sexp2._2)

      case GLessEqThan(exp1, exp2) =>
        val sexp1 = simplify(exp1)
        val sexp2 = simplify(exp2)
        (new GLessEqThan(sexp1._1, sexp2._1), sexp1._2 || sexp2._2)

      case GGreaterEqThan(exp1, exp2) =>
        val sexp1 = simplify(exp1)
        val sexp2 = simplify(exp2)
        (new GGreaterEqThan(sexp1._1, sexp2._1), sexp1._2 || sexp2._2)

      case GPlus(exp1, exp2) =>
        val sexp1 = simplify(exp1)
        val sexp2 = simplify(exp2)
        (new GPlus(sexp1._1, sexp2._1), sexp1._2 || sexp2._2)

      case GMinus(exp1, exp2) =>
        val sexp1 = simplify(exp1)
        val sexp2 = simplify(exp2)
        (new GMinus(sexp1._1, sexp2._1), sexp1._2 || sexp2._2)

      case GTimes(exp1, exp2) =>
        val sexp1 = simplify(exp1)
        val sexp2 = simplify(exp1)
        (new GTimes(sexp1._1, sexp2._1), sexp1._2 || sexp2._2)

      case GDivide(exp1, exp2) =>
        val sexp1 = simplify(exp1)
        val sexp2 = simplify(exp2)
        (new GDivide(sexp1._1, sexp2._1), sexp1._2 || sexp2._2)

      case GMod(exp1, exp2) =>
        val sexp1 = simplify(exp1)
        val sexp2 = simplify(exp2)
        (new GMod(sexp1._1, sexp2._1), sexp1._2 || sexp2._2)

      case GDot(exp1, exp2) =>
        //simplify(GDotPropagator(exp1, exp2))
        (GDotPropagator(exp1, exp2), true)

      case GSubString(exp1, exp2) =>
        //simplify(GSubstringPropagator(exp1, exp2))
        (GSubstringPropagator(exp1, exp2), true)

       case GVariableSubstring(whole, str) =>
        simplifyGVariableSubstring(whole, str)

      case GBoolFunc(exp) => simplifyGBoolFunc(exp)

      case GOptionalBoolFunc(exp) =>
        throw new Exception("GOptionalBool func has been deprecated!")

      case _ => (gexp, false)
    }
  }

  private def simplifyGNot(exp:GExpression) : (GExpression, Boolean) = {
      val sexp = simplify(exp)
      if (sexp._1.isInstanceOf[GBoolLiteral]) {
        if (sexp._1.asInstanceOf[GBoolLiteral].value)
          (new GBoolLiteral(false), true)
        else
          (new GBoolLiteral(true), true)
      }
      else (new GNot(sexp._1), sexp._2)
  }

  private def simplifyGOr(exp1:GExpression, exp2:GExpression) : (GExpression, Boolean) = {
        val sexp1 = simplify(exp1)
        val sexp2 = simplify(exp2)

        if (sexp1._1.isInstanceOf[GBoolLiteral]) {
          if (sexp1._1.asInstanceOf[GBoolLiteral].value)
            (sexp1._1, true)
          else
            (sexp2._1, true)
        }
        else if (sexp2._1.isInstanceOf[GBoolLiteral]) {
          if (sexp2._1.asInstanceOf[GBoolLiteral].value)
             (sexp2._1, true)
          else
            (sexp1._1, true)
        }
        else
          (new GOr(sexp1._1, sexp2._1),  sexp1._2 || sexp2._2)
  }

  private def simplifyGAnd(exp1:GExpression, exp2:GExpression)  : (GExpression, Boolean) = {
        val sexp1 = simplify(exp1)
        val sexp2 = simplify(exp2)

        if (sexp1._1.isInstanceOf[GBoolLiteral]) {
          if (sexp1._1.asInstanceOf[GBoolLiteral].value)
            (sexp2._1, true)
          else
            (sexp1._1, true)
        }
        else if (sexp2._1.isInstanceOf[GBoolLiteral]) {
          if (sexp2._1.asInstanceOf[GBoolLiteral].value)
             (sexp1._1, true)
          else
            (sexp2._1, true)
        }
        else
          (new GAnd(sexp1._1, sexp2._1),  sexp1._2 || sexp2._2)
  }

  private def simplifyGBoolFunc(exp:GExpression)  : (GExpression, Boolean) = {
    val sexp = simplify(exp)
    if (sexp._1.isInstanceOf[GBoolLiteral])
      (new GBoolLiteral(true), true)
    else if (sexp._1.isInstanceOf[GStringLiteral]) {
      val str = sexp._1.asInstanceOf[GStringLiteral].value
      val value = str.length > 0 && !str.equals("0")
      (new GBoolLiteral(value), true)
    }
    else
      (new GBoolFunc(sexp._1), sexp._2)
  }

  private def simplifyGConditional(cond:GExpression, pass:GExpression, fail:GExpression)  : (GExpression, Boolean) = {
    val scond = simplify(cond)
    if (scond._1.isInstanceOf[GBoolLiteral]) {
      if (scond._1.asInstanceOf[GBoolLiteral].value)
        simplify(pass)
      else
        simplify(fail)
    }
    else {
      var spass = simplify(pass)
      var sfail = simplify(fail)
      (new GConditional(scond._1, spass._1, sfail._1), scond._2 || spass._2 || sfail._2)
    }
  }

  private def simplifyGVariableSubstring(whole:GVariable, sub:GExpression) : (GExpression, Boolean) = {

    //val sexp2 = simplify(exp.sub)
    //return (new GVariableSubstring(exp.whole, sexp2._1), sexp2._2)

    /*val sexp1 =
      if (exp.whole.isInstanceOf[GVariable])
        (exp.whole, false)
      else
        simplify(exp.whole)
      */


   /*val sexp2 =
     if (sub.isInstanceOf[GVariable])
        (exp.sub, false)
      else
        simplify(sub)
      */

    val sexp2 = simplify(sub)
    return (new GVariableSubstring(whole, sexp2._1), sexp2._2)
  }
}
