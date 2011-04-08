/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gsd.cdl.smt.yices.propagator

import gsd.cdl.formula._
import gsd.cdl.smt.yices.debugutils.PrettyPrinter
import org.junit._
import Assert._

class StringSimplifierTest {
  @After
  def tearDown: Unit = {
    gsd.cdl.formula.TypeGraph.clear()
  }

  @Test
  def simplifyAndTest() {
    var formula1 = new GAnd(
      new GVariable("x"),
      new GBoolLiteral(true)
    )

    assertTrue(StringSimplifier(formula1).equals(new GVariable("x")))

    var formula2 = new GAnd(
      new GBoolLiteral(true),
      new GVariable("x")
    )

    assertTrue(StringSimplifier(formula2).equals(new GVariable("x")))

    var formula3 = new GAnd(
      new GVariable("x"),
      new GBoolLiteral(false)
    )

    assertTrue(StringSimplifier(formula3).equals(new GBoolLiteral(false)))

    var formula4 = new GAnd(
      new GBoolLiteral(false),
      new GVariable("x")
    )

    assertTrue(StringSimplifier(formula4).equals(new GBoolLiteral(false)))

    var formula5 = new GAnd(
      new GBoolLiteral(false),
      new GBoolLiteral(false)
    )

    assertTrue(StringSimplifier(formula5).equals(new GBoolLiteral(false)))

    var formula6 = new GAnd(
      new GBoolLiteral(false),
      new GBoolLiteral(true)
    )

    assertTrue(StringSimplifier(formula6).equals(new GBoolLiteral(false)))

     var formula7 = new GAnd(
      new GBoolLiteral(true),
      new GBoolLiteral(false)
    )

    assertTrue(StringSimplifier(formula7).equals(new GBoolLiteral(false)))

     var formula8 = new GAnd(
      new GBoolLiteral(true),
      new GBoolLiteral(true)
    )

    assertTrue(StringSimplifier(formula8).equals(new GBoolLiteral(true)))

    var formula9 = new GAnd(
      new GVariable("x"), new GVariable("y")
    )

    assertTrue(StringSimplifier(formula9).equals(formula9))
  }

  @Test
  def simplifyOrTest() {
    var formula1 = new GOr(
      new GVariable("x"),
      new GBoolLiteral(true)
    )

    assertTrue(StringSimplifier(formula1).equals(new GBoolLiteral(true)))

    var formula2 = new GOr(
      new GBoolLiteral(true),
      new GVariable("x")
    )

    assertTrue(StringSimplifier(formula2).equals(new GBoolLiteral(true)))

    var formula3 = new GOr(
      new GVariable("x"),
      new GBoolLiteral(false)
    )

    assertTrue(StringSimplifier(formula3).equals(new GVariable("x")))

    var formula4 = new GOr(
      new GBoolLiteral(false),
      new GVariable("x")
    )

    assertTrue(StringSimplifier(formula4).equals(new GVariable("x")))

    var formula5 = new GOr(
      new GBoolLiteral(false),
      new GBoolLiteral(false)
    )

    assertTrue(StringSimplifier(formula5).equals(new GBoolLiteral(false)))

    var formula6 = new GOr(
      new GBoolLiteral(false),
      new GBoolLiteral(true)
    )

    assertTrue(StringSimplifier(formula6).equals(new GBoolLiteral(true)))

     var formula7 = new GOr(
      new GBoolLiteral(true),
      new GBoolLiteral(false)
    )

    assertTrue(StringSimplifier(formula7).equals(new GBoolLiteral(true)))

     var formula8 = new GOr(
      new GBoolLiteral(true),
      new GBoolLiteral(true)
    )

    assertTrue(StringSimplifier(formula8).equals(new GBoolLiteral(true)))

    var formula9 = new GOr(
      new GVariable("x"), new GVariable("y")
    )

    assertTrue(StringSimplifier(formula9).equals(formula9))
  }

  @Test
  def simplifyGNot() {
    val formula1 = new GNot(new GBoolLiteral(false))
    assertTrue(StringSimplifier(formula1).equals(new GBoolLiteral(true)))

    val formula2 = new GNot(new GBoolLiteral(true))
    assertTrue(StringSimplifier(formula2).equals(new GBoolLiteral(false)))

    val formula3 = new GNot(new GVariable("x"))
    assertTrue(StringSimplifier(formula3).equals(formula3))
  }

  @Test
  def simplifyGBoolFunc() {
    val formula1 = new GBoolFunc(new GStringLiteral("test"))
    assertTrue(StringSimplifier(formula1).equals(new GBoolLiteral(true)))

    val formula2 = new GBoolFunc(new GStringLiteral(""))
    assertTrue(StringSimplifier(formula2).equals(new GBoolLiteral(false)))

    val formula3 = new GBoolFunc(new GStringLiteral("0"))
    assertTrue(StringSimplifier(formula3).equals(new GBoolLiteral(false)))

    val formula4 = new GBoolFunc(new GStringLiteral("1"))
    assertTrue(StringSimplifier(formula4).equals(new GBoolLiteral(true)))

    val formula5 = new GBoolFunc(new GVariable("x"))
    assertTrue(StringSimplifier(formula5).equals(formula5))
  }

  @Test
  def simplifyGConditional() {
    val formula1 = new GConditional(
      new GBoolLiteral(true), new GVariable("x"), new GVariable("y")
    )
    assertTrue(StringSimplifier(formula1).equals(new GVariable("x")))

    val formula2 = new GConditional(
      new GBoolLiteral(false), new GVariable("x"), new GVariable("y")
    )
    assertTrue(StringSimplifier(formula2).equals(new GVariable("y")))

    val formula3 = new GConditional(
      new GVariable("x"), new GVariable("y"), new GVariable("z")
    )
    assertTrue(StringSimplifier(formula3).equals(formula3))
  }

  @Test
  def simplifyGVariableSubstring() {
    val formula1 = new GVariableSubstring(
      new GVariable("x"),
      new GStringLiteral("abc")
    )
    assertTrue(StringSimplifier(formula1).equals(formula1))

    val formula2 = new GVariableSubstring(
      new GVariable("x"),
      new GConditional(
        GSubString(new GStringLiteral("abc"), new GStringLiteral("ab")),
        new GStringLiteral("mm"),
        new GStringLiteral("pp")
      )
    )
    assertTrue(StringSimplifier(formula2).equals(new GVariableSubstring(
      new GVariable("x"),
      new GStringLiteral("mm")
    )))

    val formula3 = new GVariableSubstring(
      new GVariable("x"),
      new GConditional(
        GSubString(new GStringLiteral("abc"), new GStringLiteral("abxxxx")),
        new GStringLiteral("mm"),
        new GStringLiteral("pp")
      )
    )
    assertTrue(StringSimplifier(formula3).equals(new GVariableSubstring(
      new GVariable("x"),
      new GStringLiteral("pp")
    )))
  }

  @Test
  def simplificationTest1() {
    println("Formula 1\n")
    val formula1 =
          new GDot(
            new GStringLiteral("libab.so "),
            new GConditional(
              new GNot(new GBoolFunc(new GVariable("OPTION_data_var"))),
              new GStringLiteral("libc.so"),
              new GStringLiteral("libxyzabc.so")
            )
          )
    PrettyPrinter.traverseExp(StringSimplifier(formula1), 0)
    assertTrue(StringSimplifier(formula1).equals(
      new GConditional(
        new GNot(new GBoolFunc(new GVariable("OPTION_data_var"))),
        new GStringLiteral("libab.so libc.so"),
        new GStringLiteral("libab.so libxyzabc.so")
      )
    ))
  }

  @Test
  def simplificationTest2() {
    println("Formula 2\n")
    val formula2:GDot =
      new GDot(
        new GConditional(
          GBoolLiteral(true),
          new GDot(
            new GStringLiteral("pre "),
            new GConditional(
              new GBoolLiteral(true),
              new GDot(new GStringLiteral("abc "), new GStringLiteral("cde ")),
              new GDot(new GStringLiteral("fgh "), new GStringLiteral("ijk ")
              )
            )
          ),
          new GDot(new GStringLiteral("xxx "), new GStringLiteral("yyy "))
      ), // end gconditional
      new GConditional(
        new GSubString(new GStringLiteral("xyz "), new GStringLiteral("yz ")),
        new GDot(new GStringLiteral("abd "), new GStringLiteral("fge ")),
        new GStringLiteral("~~~ ")
      )
    )
   PrettyPrinter.traverseExp(StringSimplifier(formula2), 0)
   assertTrue(StringSimplifier(formula2).equals(new GStringLiteral("pre abc cde abd fge ")))
  }

  @Test
  def simplificationTest3() {
   println("Formula 3\n")
    var formula3 = new GConditional(
      new GSubString(
        new GStringLiteral("abcd"),
        new GDot(
          new GConditional(
            new GSubString(
              new GStringLiteral("xxxy"),
              new GStringLiteral("xy")
            ),
            new GStringLiteral("00"),
            new GStringLiteral("011111")
          ),
          new GStringLiteral("z")
        )
      ),
      new GDot(
        new GConditional(
          new GSubString(
            new GDot(new GStringLiteral("xxx"), new GStringLiteral("yyy")),
            new GStringLiteral("~~~")
          ),
          new GStringLiteral("yyy"),
          new GStringLiteral("~")
        ),
        new GStringLiteral("kk")
      ),
      new GStringLiteral("jjjjj")
    )
   PrettyPrinter.traverseExp(StringSimplifier(formula3), 0)
   assertTrue(StringSimplifier(formula3).equals(new GStringLiteral("jjjjj")))
  }

  @Test
  def simplificationTest4() {
   println("Formula 4\n")
    var formula4 = new GConditional(
      new GSubString(
        new GStringLiteral("abcd00z"),
        new GDot(
          new GConditional(
            new GSubString(
              new GStringLiteral("xxxy"),
              new GStringLiteral("xy")
            ),
            new GStringLiteral("00"),
            new GStringLiteral("011111")
          ),
          new GStringLiteral("z")
        )
      ),
      new GDot(
        new GConditional(
          new GSubString(
            new GDot(new GStringLiteral("xxx"), new GStringLiteral("yyy")),
            new GStringLiteral("~~~")
          ),
          new GStringLiteral("yyy"),
          new GStringLiteral("~")
        ),
        new GStringLiteral("kk")
      ),
      new GStringLiteral("jjjjj")
    )
    PrettyPrinter.traverseExp(StringSimplifier(formula4), 0)
    assertTrue(StringSimplifier(formula4).equals(new GStringLiteral("~kk")))
  }

  @Test
  def simplificationTest5() {
  println("Formula 5\n")
    val formula5:GDot =
      new GDot(
        new GConditional(
          GBoolLiteral(true),
          new GDot(
            new GStringLiteral("pre "),
            new GConditional(
              new GBoolLiteral(true),
              new GDot(new GStringLiteral("abc "), new GStringLiteral("cde ")),
              new GDot(new GStringLiteral("fgh "), new GStringLiteral("ijk ")
              )
            )
          ),
          new GDot(new GStringLiteral("xxx"), new GStringLiteral("yyy"))
      ), // end gconditional
      new GConditional(
        new GSubString(new GVariable("xyz"), new GStringLiteral("yz")),
        new GDot(new GStringLiteral("abd"), new GStringLiteral("fge")),
        new GStringLiteral("~~~")
      )
    )
   PrettyPrinter.traverseExp(StringSimplifier(formula5), 0)
   assertTrue(StringSimplifier(formula5).equals(
       new GConditional(
         new GVariableSubstring(
           new GVariable("xyz"),
           new GStringLiteral("yz")
         ),
         new GStringLiteral("pre abc cde abdfge"),
         new GStringLiteral("pre abc cde ~~~")
       )
     )
   )
  }

  @Test
  def simplificationTest6() {
    println("Formula 6\n")
    var formula6 =
      new GDot(
      new GConditional(
        new GVariable("z"),
        new GDot(
          new GConditional(
            new GSubString(
              new GVariable("d"),
              new GDot(
                new GConditional(
                  new GSubString(
                    new GVariable("p"),
                    new GDot(
                      new GStringLiteral("mm"),
                      new GStringLiteral("zz")
                    )
                  ),
                  new GStringLiteral("5"),
                  new GStringLiteral("6")
                ),
                new GConditional(
                  new GSubString(
                    new GVariable("p"),
                    new GStringLiteral("1")
                  ),
                  new GDot(
                    new GStringLiteral("2"),
                    new GConditional(
                      new GVariable("d"),
                      new GStringLiteral("3"),
                      new GStringLiteral("4")
                    )
                  ),
                  new GStringLiteral("zz")
                )
              )
            ),
            new GStringLiteral("cc"),
            new GConditional(
              new GSubString(
                new GVariable("d"),
                new GDot(new GStringLiteral("f"), new GStringLiteral("g"))
              ),
              new GStringLiteral("--"),
              new GStringLiteral("~~~")
            )
          ),
          new GStringLiteral("ff")
        ),
        new GDot(new GStringLiteral("<"), new GStringLiteral(">"))
      ),
      new GDot(
        new GConditional(
          new GVariable("x"),
          new GDot(new GStringLiteral("10"), new GStringLiteral("11")),
          new GConditional(
            new GVariable("y"),
            new GStringLiteral("p"),
            new GStringLiteral("q")
          )
        ),
        new GStringLiteral("de")
      )
    )
    PrettyPrinter.traverseExp(StringSimplifier(formula6), 0)
    assertTrue(StringSimplifier(formula6).equals(
      new GConditional( //1
        new GVariable("z"), // 2
        new GConditional( // 4
          new GVariableSubstring( // 5
            new GVariable("d"), //6
            new GConditional( //8
              new GVariableSubstring( //9
                new GVariable("p"),
                new GStringLiteral("mmzz")
              ), // end-9
              new GConditional( //13
                new GVariableSubstring( // 14
                  new GVariable("p"),
                  new GStringLiteral("1")
                ), // end-14
                new GConditional( //18
                  new GVariable("d"),
                  new GStringLiteral("523"),
                  new GStringLiteral("524")
                ), // end-18
                new GStringLiteral("5zz") // 23
             ), // end-13
             new GConditional( // 24
                new GVariableSubstring( // 25
                  new GVariable("p"),
                  new GStringLiteral("1")
                ), // end-25
                new GConditional( // 29
                  new GVariable("d"),
                  new GStringLiteral("623"),
                  new GStringLiteral("624")
                ), // end-29
                new GStringLiteral("6zz") // 34
              ) // end-24
           ) // end-8
         ), // end-5
         new GConditional( // 35
              new GVariable("x"), // 36
              new GStringLiteral("ccff1011de"), // 38
              new GConditional( // 39
                new GVariable("y"),
                new GStringLiteral("ccffpde"),
                new GStringLiteral("ccffqde")
              ) // end-39
           ), // end-35
          new GConditional( // 44
            new GVariableSubstring( // 45
              new GVariable("d"),
              new GStringLiteral("fg")
            ), // end-45
            new GConditional( // 49
              new GVariable("x"), // 50
              new GStringLiteral("--ff1011de"), // 52
              new GConditional( // 53
                new GVariable("y"),
                new GStringLiteral("--ffpde"),
                new GStringLiteral("--ffqde")
              ) // end-53
            ), // end-49
            new GConditional( // 58
              new GVariable("x"),
              new GStringLiteral("~~~ff1011de"),
              new GConditional( // 62
                new GVariable("y"),
                new GStringLiteral("~~~ffpde"),
                new GStringLiteral("~~~ffqde")
              ) // end-62
            ) // end-58
           ) // end-44
          ), // end-4
          new GConditional( // 67
            new GVariable("x"),
            new GStringLiteral("<>1011de"),
            new GConditional( // 71
              new GVariable("y"),
              new GStringLiteral("<>pde"),
              new GStringLiteral("<>qde")
            ) // end-71
          ) // end-67
       ) // end-1
    ))
  }
}
