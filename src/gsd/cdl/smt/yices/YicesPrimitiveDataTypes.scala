
package gsd.cdl.smt.yices

import gsd.cdl.smt._
import gsd.cdl.formula._

object YicesIntDataType extends SmtDataType {
  def typeName() : String = "int"
}

/* . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . */

object YicesBoolDataType extends SmtDataType {
    def typeName() : String = "bool"
}
