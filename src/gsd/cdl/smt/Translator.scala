/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gsd.cdl.smt

import gsd.cdl.formula._
import gsd.cdl.formula.TypeGraph._
import java.io.FileWriter


/*
 * The <code>SmtTranslator</code> class denotes the translator that converts
 * a given IML input file into the corresponding smt output file format
 * (eg.: Yices, Z3, etc.).
 */

abstract class SmtTranslator {

 /*
  * Translates an IML input file into the corresponding smt output file format
  * (eg.: Yices, Z3, etc.).
  *
  * @param in input IML file.
  * @param out: output IML file.
  *
  * @return <code>true</code> if the translate resulted in no errors
  *         or <code>false</code> otherwise.
  */
  def translate(in:String, out:String) : Boolean
}

