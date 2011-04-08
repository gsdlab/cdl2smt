/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gsd.cdl.smt.yices.propagator

import gsd.cdl.formula._

case class GVariableSubstring(whole:GVariable, sub:GExpression) extends GStaticTypedBinaryExpression(BoolType, StringType, whole, sub, "s-contains")

