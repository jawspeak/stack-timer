package com.jawspeak.stacktimer;

import java.util.List;

import com.google.common.collect.Lists;

public class SpyLoggerForTesting implements StackTimerLogger {
  List<String> loggedMessages = Lists.newArrayList();
  List<String> errorMessages = Lists.newArrayList();
  
  public void error(String message) {
    errorMessages.add(message);
  }
  
  public void logMessage(String message) {
    loggedMessages.add(message);
  }
}