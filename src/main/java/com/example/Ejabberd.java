package com.example;

import com.ericsson.otp.erlang.OtpAuthException;
import com.ericsson.otp.erlang.OtpErlangAtom;
import com.ericsson.otp.erlang.OtpErlangExit;
import com.ericsson.otp.erlang.OtpErlangList;
import com.ericsson.otp.erlang.OtpErlangObject;
import com.ericsson.otp.erlang.OtpErlangTuple;
import com.ericsson.otp.erlang.OtpPeer;
import com.ericsson.otp.erlang.OtpSelf;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Ejabberd
 */
public class Ejabberd {
  private static final String EjabberdNodename = "ejabberd";
  private static final String SelfNodename = "admin";
  private static final String Cookie = ".erlang.cookie";
  private Erlang erlang;

  public Ejabberd() throws IOException, OtpAuthException {
    OtpPeer peer = new OtpPeer(EjabberdNodename);
    String cookie = new String(Files.readAllBytes(Paths.get(Cookie)));
    OtpSelf self = new OtpSelf(SelfNodename, cookie);
    erlang = new Erlang();
    erlang.connect(self, peer);
  }

  public List<String> connected_users() throws IOException, OtpAuthException, OtpErlangExit {
    ArrayList<String> result = new ArrayList<String>();
    OtpErlangObject cu = erlang.send("ejabberd_sm", "connected_users");
    if (cu instanceof OtpErlangList) {
      for (OtpErlangObject jid : (OtpErlangList)cu) {
        result.add(erlang.send("lists", "flatten", jid).toString());
      }
    }
    return result;
  }

  public String ejabberd_status() throws IOException, OtpAuthException, OtpErlangExit {
    String result = "";
    OtpErlangObject status =  erlang.send("ejabberd_admin", "status");
    if ((status instanceof OtpErlangTuple) && 
        ((OtpErlangTuple)status).arity() == 2 &&
        "ok".equals(((OtpErlangTuple)status).elementAt(0).toString())) {
      result = erlang.send("lists", "flatten", ((OtpErlangTuple)status).elementAt(1)).toString();
    }
    return result;
  }

  public String get_log_path() throws IOException, OtpAuthException, OtpErlangExit {
    return erlang.send("ejabberd_app", "get_log_path").toString();
  }

  public String mnesia_directory() throws IOException, OtpAuthException, OtpErlangExit {
    return erlang.send("mnesia", "system_info", new OtpErlangAtom("directory")).toString();
  }
}
