package com.example;

import java.io.File;
import java.util.List;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Erl
 */
public class ErlTest extends TestCase {
  public ErlTest(String testName) {
    super(testName);
  }

  public static Test suite() {
    return new TestSuite(ErlTest.class);
  }

  public void testErl() {
    File factorialBeam = new File("factorial/ebin/factorial.beam");
    if (factorialBeam.exists()) {
      Erl erl = new Erl();
      List<String> result =
        erl
        .withArgs("-pa", "factorial/ebin")
        .withArgs("-s", "factorial", "main", "5")
        .withArgs("-s", "init", "stop")
        .withArgs("-noshell")
        .execute();
      assertEquals("5! = 120\n", result.get(0));
      assertEquals("", result.get(1));
    } else {
      System.out.println(factorialBeam.toString() + " does not exist.");
    }
  }
}
