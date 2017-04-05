package com.example;

import com.ericsson.otp.erlang.OtpAuthException;
import com.ericsson.otp.erlang.OtpErlangExit;
import java.io.IOException;
import java.util.List;

/**
 * java2ejabberd
 *
 */
public class App {
  public static void main( String[] args ) {
    try {
      Ejabberd ejabberd = new Ejabberd();
      System.out.println("status: " + ejabberd.ejabberd_status());
      List<String> cu = ejabberd.connected_users();
      System.out.println("connected users:");
      for (String u : cu) {
        System.out.println(" " + u);
      }
      System.out.println("mnesia directory: " + ejabberd.mnesia_directory());
      System.out.println("log path: " + ejabberd.get_log_path());
    } catch (IOException ex) {
      System.out.println("io exception: " + ex.getMessage());
    } catch (OtpAuthException ex) {
      System.out.println("auth exception: " + ex.getMessage());
    } catch (OtpErlangExit ex) {
      System.out.println("erlang exit exception: " + ex.getMessage());
    }
  } 
}
