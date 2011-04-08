/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gsd.cdl.smt

import gsd.cdl.formula._

/*
 * The <code>SmtDeclaration</code> class denotes a variable declaration that
 * can be declared in Yices input language.
 */

abstract class SmtVariableDeclaration {
  def apply(id:GVariable) : String
}

