package com.jawspeak.stacktimer;

public class DefaultStackTimerLogger implements StackTimerLogger {

  public static final StackTimerLogger DEFAULT = new DefaultStackTimerLogger();
  
  public void error(String message) {
    System.err.println(message);
  }

  public void logMessage(String message) {
    System.out.println(message);
  }

}
