package com.example;

import com.ericsson.otp.erlang.OtpAuthException;
import com.ericsson.otp.erlang.OtpErlangExit;
import com.ericsson.otp.erlang.OtpErlangInt;
import com.ericsson.otp.erlang.OtpErlangObject;
import com.ericsson.otp.erlang.OtpPeer;
import com.ericsson.otp.erlang.OtpSelf;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Erlang
 */
public class ErlangTest extends TestCase {
  private ExecutorService service;

  public ErlangTest(String testName) {
    super(testName);
  }

  public static Test suite() {
    return new TestSuite(ErlangTest.class);
  }

  @Override
  protected void setUp() {
    service = Executors.newSingleThreadExecutor();
  }

  @Override
  protected void tearDown() {
    service.shutdown();
  }

  public void testErlang() {
    File factorialBeam = new File("factorial/ebin/factorial.beam");
    if (factorialBeam.exists()) {
      try {
        Erlang erlang = new Erlang();
        String hostname = erlang.getHostname();
        service.execute(new Runnable() {
          public void run() {
            Erl erl = new Erl();
            erl
              .withArgs("-pa", "factorial/ebin")
              .withArgs("-sname", "one@" + hostname)
              .withArgs("-noshell")
              .execute();
          }
        });
        OtpPeer peer = new OtpPeer("one@" + hostname);
        OtpSelf self = new OtpSelf("two@" + hostname);
        IOException ioException = null;
        for (int i = 0; i < 10; ++i) {
          try {
            Thread.sleep(500);
            erlang.connect(self, peer);
            break;
          } catch (IOException ex) {
            ioException = ex;
          }
        }
        if (ioException != null) {
          throw ioException;
        }
        OtpErlangObject result = erlang.send("factorial", "f", new OtpErlangInt(0));
        assertEquals("1", result.toString());
        result = erlang.send("factorial", "f", new OtpErlangInt(5));
        assertEquals("120", result.toString());
        erlang.send("init", "stop");
      } catch (IOException ex) {
        System.out.println("io exception: " + ex.getMessage());
        assertFalse(true);
      } catch (OtpAuthException ex) {
        System.out.println("auth exception: " + ex.getMessage());
        assertFalse(true);
      } catch (OtpErlangExit ex) {
        System.out.println("erlang exit: " + ex.getMessage());
        assertFalse(true);
      } catch (InterruptedException ex) {
        System.out.println("interrupted: " + ex.getMessage());
        assertFalse(true);
      } 
    } else {
      System.out.println(factorialBeam.toString() + " does not exist.");
    }
  }
}
