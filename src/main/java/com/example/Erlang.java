package com.example;

import com.ericsson.otp.erlang.OtpAuthException;
import com.ericsson.otp.erlang.OtpConnection;
import com.ericsson.otp.erlang.OtpErlangExit;
import com.ericsson.otp.erlang.OtpErlangObject;
import com.ericsson.otp.erlang.OtpPeer;
import com.ericsson.otp.erlang.OtpSelf;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Erlang
 */
public class Erlang {
  private OtpConnection connection;

  public Erlang() {
  }

  public String getHostname() throws UnknownHostException {
    return InetAddress.getLocalHost().getHostName();
  }

  public void connect(OtpSelf self, OtpPeer peer) throws IOException, OtpAuthException {
    connection = self.connect(peer);
  }

  public OtpErlangObject send(String mod, String fun, OtpErlangObject... args) throws IOException, OtpErlangExit, OtpAuthException {
    connection.sendRPC(mod, fun, args);
    return connection.receiveRPC();
  }
}
