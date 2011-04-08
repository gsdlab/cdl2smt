/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gsd.cdl.smt.yices

import gsd.cdl.formula._
import gsd.cdl.smt._

/*
 * The <code> TranslateToSmtDeclaration </code>  object translates a given
 * variable into a contraint written in Yices input language.
*/
object YicesVariableDeclaration extends SmtVariableDeclaration {
  def apply(id:GVariable) : String = {

    id.getType match {

      case StringType     => "(define " + id + "::" + YicesStringDataType.typeName + ")"

      case BoolType       => "(define " + id + "::" + YicesBoolDataType.typeName + ")"

      case IntType       => "(define " + id + "::" + YicesIntDataType.typeName + ")"

      case DisjunctiveType(types) => {
        if (id.isEnum) {
        "(define " + id + "::" + YicesEnumDataType.setId(id.toString).typeName + ")"
        } else {
            // ";; TODO:  (define " + id + "::disjunctive_type)" + id.getType.toString
           "(define " + id + "::" + YicesIntDataType.typeName + ")"
        }
      }

      case _  =>
        throw new Exception("Unexpected type " + id.getType)
    }
  }
}
