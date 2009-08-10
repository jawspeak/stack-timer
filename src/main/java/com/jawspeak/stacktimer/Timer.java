package com.jawspeak.stacktimer;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.jawspeak.util.Clock;

public class Timer {

  private final Clock clock;
  private long startTime = -1L;
  private long stopTime = -1L;
  private final String name;
  private final List<Timer> nestedTimers = new ArrayList<Timer>();
  private final StackTimerLogger stackTimerLogger;

  public Timer(String name, Clock clock, StackTimerLogger logger) {
    this.name = name;
    this.clock = clock;
    this.stackTimerLogger = logger;
  }

  public void start() {
    if (startTime > 0) {
      stackTimerLogger.error("You can't start this timer [" + name + "] because it was already started.");
      return;
    }
    startTime = clock.currentTimeMillis();
  }

  public void stop() {
    if (startTime < 0) {
      stackTimerLogger.error("You can't stop this timer [" + name + "] until you first start it.");
      return;
    }
    if (stopTime > 0) {
      stackTimerLogger.error("You can't stop this timer [" + name + "] multiple times, it was already stopped at " + stopTime);
      return;
    }
    stopTime = clock.currentTimeMillis();
  }

  public TimerSummary summarize() {
    return summarize(startTime);
  }

  private TimerSummary summarize(long withStartTimeOffset) {
    if (stopTime < 0) {
      stackTimerLogger.error("You can't complete this timer [" + name + "] until it is first stopped");
      return null; // probably don't want to return null
    }

    List<TimerSummary> nestedSummaries = Lists.newArrayList();
    for (Timer nestedTimer : nestedTimers) {
      nestedSummaries.add(nestedTimer.summarize(withStartTimeOffset));
    }
    return new TimerSummary(name, startTime - withStartTimeOffset, stopTime - withStartTimeOffset, nestedSummaries);
  }

  public Timer nestedStartedTimer(String name) {
    Timer nestedTimer = new Timer(name, clock, stackTimerLogger);
    nestedTimers.add(nestedTimer);
    nestedTimer.start();
    return nestedTimer;
  }
}
