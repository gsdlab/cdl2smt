/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gsd.cdl.smt.yices

import gsd.cdl.smt._
import gsd.cdl.formula._

/* . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . */

object YicesEnumDataType extends SmtDeclarableDataType {

   override def setId(id:String) : SmtDataType = {
     this.id = id.replace("_scalar_var", "_scalar")
     return this
   }

   def declare(param:Object) : String  = {
     var enum:(String, GVariable) = param.asInstanceOf[(String, GVariable)]
     val buffer = new scala.collection.mutable.StringBuilder
     buffer.append("(define-type " + setId(enum._1).typeName + " (scalar ")
     enum._2.getEnums.foreach((exp: GExpression) => {buffer.append(YicesConstraint(exp)).append(" ")})
     buffer.append("))")
     return buffer.toString
   }
}
