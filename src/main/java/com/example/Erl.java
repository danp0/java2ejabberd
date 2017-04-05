package com.example;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;

/**
 * Erl
 */
public class Erl {
  private CommandLine erl;

  public Erl() {
    erl = new CommandLine("erl");
  }

  public Erl withArgs(String... args) {
    for (String arg : args) {
      erl.addArgument(arg);
    }
    return this;
  }

  public List<String> execute() {
    String stdout = "";
    String stderr = "";
    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      ByteArrayOutputStream err = new ByteArrayOutputStream();
      PumpStreamHandler psh = new PumpStreamHandler(out, err);
      Executor executor = new DefaultExecutor();
      executor.setStreamHandler(psh);
      executor.execute(erl);
      stdout = out.toString();
      stderr = err.toString();
    } catch (ExecuteException ex) {
      stderr = ex.getMessage();
    } catch (IOException ex) {
      stderr = ex.getMessage();
    }
    return Arrays.asList(stdout, stderr);
  }
}
