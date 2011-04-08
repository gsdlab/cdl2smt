/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gsd.cdl.smt

/*
 * The <code>SmtDataType</code> class denotes a data type in a SMT input
 * language.
 */
abstract class SmtDataType {
  def typeName() : String
}

/*
 * The <code>SmtDeclarableDataType</code> class denotes a data type that can
 * be declared in a SMT input language.
 */
abstract class SmtDeclarableDataType extends SmtDataType {

  protected var id:String = ""

  def declare() : String =  declare(null)
  def declare(param:Object) : String

  def setId(id:String) : SmtDataType = {
    this.id = id ;
    return this
  }

  def typeName() = id
}

