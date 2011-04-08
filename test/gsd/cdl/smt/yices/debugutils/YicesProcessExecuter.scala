/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gsd.cdl.smt.yices.debugutils

import gsd.cdl.formula._

object YicesProcessExecuter {

  def YICES_EXEC:String = "yices"

  def isSatisfiable(fileName: String): Boolean = {
    if (getResult(fileName).trim().equalsIgnoreCase("sat")) {
            true
    } else
            false
  }

  def getResult(arg:String): String = {
    val	proc = Runtime.getRuntime().exec(YICES_EXEC + " " + arg)
    val ins = new java.io.BufferedReader(new java.io.InputStreamReader(proc.getInputStream))
    var stringBuilder = new java.lang.StringBuilder()
    try {
      var line = "";
      while (line != null){
        line = ins.readLine()
        if (line != null) {
          stringBuilder.append(line);
          stringBuilder.append(System.getProperty("line.separator"));
        }
      }
    }
    finally {
      ins.close();
    }

    stringBuilder.toString.trim()
  }
}
