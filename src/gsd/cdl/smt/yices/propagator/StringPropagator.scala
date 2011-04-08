/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gsd.cdl.smt.yices.propagator

import gsd.cdl.formula._

protected class Direction {
  def invert() : Direction = 
    if (this == LEFT)
      return RIGHT
    else
      return LEFT
}
protected object LEFT extends Direction
protected object RIGHT extends Direction

abstract class StringPropagator {

  def apply(exp1:GExpression, exp2:GExpression) = propagate(exp1, exp2, RIGHT)

  protected def propagate(exp1:GExpression, exp2:GExpression, dir:Direction) : GExpression 

  protected def eval(exp1:GStringLiteral, exp2:GStringLiteral, dir:Direction) : GExpression


}
