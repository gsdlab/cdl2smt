/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gsd.cdl.smt.yices.propagator

import gsd.cdl.formula._
import gsd.cdl.smt.yices.YicesStringDataType

class GDotPropagator extends StringPropagator {

 protected def eval(exp1:GStringLiteral, exp2:GStringLiteral, dir:Direction) : GExpression = {
      if (dir == LEFT)
        new GStringLiteral(YicesStringDataType.flat(exp2.value) + YicesStringDataType.flat(exp1.value))
      else
        new GStringLiteral(YicesStringDataType.flat(exp1.value) + YicesStringDataType.flat(exp2.value))
  }

  protected def propagate(exp1:GExpression, exp2:GExpression, dir:Direction) : GExpression = {
    exp1 match {

      case GStringLiteral(_) => {
          exp2 match {
            case GStringLiteral(_) =>
              eval(exp1.asInstanceOf[GStringLiteral], exp2.asInstanceOf[GStringLiteral], dir)

            case GDot(a, b) =>
              propagate(propagate(a, b, RIGHT), exp1, dir.invert)

            case GConditional(cond, pass, fail) =>
              new GConditional(cond, propagate(pass, exp1, dir.invert), propagate(fail, exp1, dir.invert))
          }
      }

      case GDot(x, y) => {

          exp2 match {
            case GStringLiteral(_) =>
              propagate(propagate(x, y, RIGHT), exp2, dir)

            case GDot(a, b) =>
              propagate(propagate(x, y, RIGHT), propagate(a, b, RIGHT), dir)

            case GConditional(_, _, _) =>
              propagate(propagate(x, y, RIGHT), exp2, dir)
          }
      }

      case GConditional(cond, pass, fail) => {
          exp2 match {
            case GStringLiteral(_) =>
              new GConditional(cond, propagate(pass, exp2, dir), propagate(fail, exp2, dir))

            case GDot(a, b) =>
              propagate(exp1, propagate(a, b, RIGHT), dir)

            case GConditional(_, _, _) =>
              new GConditional(cond, propagate(pass, exp2, dir), propagate(fail, exp2, dir))
          }
      }
    }
  }
}

object GDotPropagator extends GDotPropagator
