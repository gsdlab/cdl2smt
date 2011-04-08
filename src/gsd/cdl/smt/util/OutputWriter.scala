/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gsd.cdl.smt.util

import java.io.Writer

/*
 * The <code>OutputWriter</code> class is meant to facilitate the output of
 * ths translation process.
 */
class OutputWriter(writer:Writer) {

  protected var identLevel = 0 ;

  /*
   * Increases the identation level (by two space units).
   */
  def increaseIdentation() = identLevel +=  2

  /* . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/
  /*
   * Decreases the identation level (by two space units).
   */
  def decreaseIdentation() = if (identLevel > 0) identLevel -= 2

  /* . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /*
   * Returns the current identation level.
   *
   * @return current identation level.
   */
  def getIndentLevel() : Int = identLevel 

 /* . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/
 /*
  * Indents a given string according to the identation level.
  *
  * @param string to be idented.
  *
  * @return return the idented string.
  */
  private def ident() = new String(Array.fill(identLevel){' '})


 /* . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/
 /*
  * Prints a string, possibly followed by a new line character.
  *
  * @param output the string to be output.
  * @param newline indicates whether a newline is to be output.
  */
  private def print(output:String, newline:Boolean) : Unit =
      writer.write(ident + output + (if (newline) "\n" else ""))


 /* . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/
 /*
  * Prints a string (not followed by a newline character).
  * 
  * @param output the string to be output.
  */
  def print(output:String) : Unit = print(output, false)

 /* . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/
 /*
  * Prints a newline character.
  */
  def println() : Unit = println("")

 /* . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/
 /*
  * Prints a string followed by a new line character.
  *
  * @param output the string to be output.
  */
  def println(output:String) : Unit = print(output, true)

  def getWriter() = writer
}
