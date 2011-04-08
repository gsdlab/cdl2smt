/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gsd.cdl.smt.yices

object YicesTranslatorParameters {
  
  private var ignoreChars = Set('"')

  def getIgnoreChars() = ignoreChars  
  def setIgnoreChars(ignoreChars:Set[Char]) = this.ignoreChars = ignoreChars 
}
