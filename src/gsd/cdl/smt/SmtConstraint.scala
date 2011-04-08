/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gsd.cdl.smt

import gsd.cdl.formula._

abstract class SmtConstraint {
  def apply(exp:GExpression) : String
}
