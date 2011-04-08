/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gsd.cdl.smt.yices

import gsd.cdl.smt._
import gsd.cdl.smt.util._
import gsd.cdl.formula._
import gsd.cdl.formula.TypeGraph._
import gsd.cdl.smt.yices.propagator.StringSimplifier
import java.io.FileWriter
import scala.collection.mutable.LinkedList

/*
 * The <code> YicesTranslator </code> object encapsulates the process of
 * translating an IML file into an Yices SMT file.
 */
object YicesTranslator extends SmtTranslator {

 /*
  *  Translates a given input IML file into the corresponding smt specification
  *  in Yices.
  *
  *  @param in input file (iml).
  *  @param out output file (Yices smt file).
  *
  * @return <code>true</code> if the translate resulted in no errors
  *         or <code>false</code> otherwise.
  *
  * @author Leonardo Passos, Marko Novakovic
  */
 def translate(in:String, out:String) : Boolean = {
    import gsd.cdl.formula.Main._

    val (ids, exps, errs) = parseFile(in)
    val strSimplifiedExps = StringSimplifier(exps)


    val fileWriter = new FileWriter(out)

    val output = new OutputWriter(fileWriter)
    output.println(";; . . . . . . . . . . . . . . . . . .")
    output.println(";; Translation of the file " + in)
    output.println()

    /* Declares each enumeration data type. */
    ids.filter(pairNameGVar => pairNameGVar._2.isEnum()).foreach (enum => {
        output.println(YicesEnumDataType.declare(enum))
        output.println()
    })

    YicesStringDataType.setEncodingMap(
      StringUtils.getStringLiteralsEncodingMap(strSimplifiedExps, YicesTranslatorParameters.getIgnoreChars)
    )

    println("encoding map: "+ YicesStringDataType.getEncodingMap)

    YicesStringDataType.setIgnoreSet(YicesTranslatorParameters.getIgnoreChars)
    YicesStringDataType.setMaxLength(YicesStringDataType.getMaxStrLength(strSimplifiedExps))


    //println("max length: " + YicesStringDataType.getMaxLength)
    //println("max bits/str: " + YicesStringDataType.getMaxSizeInBits)

    /* Declares each identifier. */
    for(gvar <- ids.values) {
      output.println(YicesVariableDeclaration(gvar))
      output.println()
    }

    val atomicExpressions = scala.collection.mutable.Set[GExpression]()

    var falseAtRootLevel = false
    var falseNode = new GBoolLiteral(false)

    /* Encodes all the constraints. */
    for(exp <- strSimplifiedExps) {
      if (exp.equals(falseNode)) {
        falseAtRootLevel = true
      }
      else {
        val smtExp = YicesConstraint(exp)

        if (smtExp.size > 0) {

          output.println(";; . . . . . . . . . . . . . . . . . .")
          output.println(";; " + exp)
          output.println
          output.println("(assert " +  smtExp + ")")
          output.println
        }
      }
    }

    if (falseAtRootLevel) {
      output.println("(assert false)")
      output.println
    }

    output.println("(check)")
    fileWriter.close
    errs.foreach(err => println(err))

    if (errs.size > 0)
      return false

    return true
 }

 def main(args:Array[String]) {
   YicesTranslator.translate("pp850.iml", "pp850.smt")
 }
}
