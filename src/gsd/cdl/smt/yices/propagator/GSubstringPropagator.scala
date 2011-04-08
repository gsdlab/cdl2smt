/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gsd.cdl.smt.yices.propagator

import gsd.cdl.formula._
import gsd.cdl.smt.yices.YicesStringDataType

class GSubstringPropagator extends GDotPropagator {

  override protected def eval(exp1:GStringLiteral, exp2:GStringLiteral, dir:Direction) : GExpression = {
    if (dir == LEFT)
      new GBoolLiteral(YicesStringDataType.flat(exp2.value).contains(YicesStringDataType.flat(exp1.value)))
    else
      new GBoolLiteral(YicesStringDataType.flat(exp1.value).contains(YicesStringDataType.flat(exp2.value)))
  }
    
  
  override protected def propagate(exp1:GExpression, exp2:GExpression, dir:Direction) : GExpression = {

   /* Note that there is a conservative approach - can only treat cases where the second
    * operand of a substring operation is known to be a string literal.
    */

    exp1 match {

      /* exp1 is a string literal */
      case GStringLiteral(_) =>

        exp2 match {
          case GVariable(_) =>
            if (dir == LEFT)
              new GVariableSubstring(exp2.asInstanceOf[GVariable], exp1)

            else
              //new GVariableSubstring(exp1, exp2)
            throw new Exception("A variable cannot be a second operand a substring operation!")

          case GStringLiteral(_) =>
            eval(exp1.asInstanceOf[GStringLiteral], exp2.asInstanceOf[GStringLiteral], dir)

          case GDot(a, b) =>
            propagate(GDotPropagator(a, b), exp1, dir.invert)

          case GConditional(cond, pass, fail) =>
            new GConditional(cond, propagate(pass, exp1, dir.invert), propagate(fail, exp1, dir.invert))
        }

      /* exp1 is a string concatenation */
      case GDot(x, y) =>

        exp2 match {
          case GVariable(_) => propagate(super.propagate(x, y, RIGHT), exp2, dir)

         case GStringLiteral(_) =>
            propagate(GDotPropagator(x, y), exp2, dir)

          case GDot(a, b) =>
            propagate(GDotPropagator(x, y), GDotPropagator(a, b), dir)

          case GConditional(_, _, _) =>
            propagate(GDotPropagator(x, y), exp2, dir)
        }

      /* exp1 is a conditional expression */
      case GConditional(cond, pass, fail) =>

        exp2 match {
          case GVariable(_) => 
            new GConditional(cond, propagate(pass, exp2, dir), propagate(fail, exp2, dir))

          case GStringLiteral(_) =>
            new GConditional(cond, propagate(pass, exp2, dir), propagate(fail, exp2, dir))

          case GDot(a, b) =>
            propagate(exp1, GDotPropagator(a, b), dir)

          case GConditional(_, _, _) =>
            new GConditional(cond, propagate(pass, exp2, dir), propagate(fail, exp2, dir))
        }

      case GVariable(_) =>
        if (dir == LEFT)
           //new GVariableSubstring(exp2, exp1)
          throw new Exception("A variable cannot be a second operand a substring operation!")
        else
          new GVariableSubstring(exp1.asInstanceOf[GVariable], exp2)
           
    }
  }
}

object GSubstringPropagator extends GSubstringPropagator
