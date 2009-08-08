package com.jawspeak.stacktimer;

import java.util.ArrayList;
import java.util.List;

import com.jawspeak.util.Clock;

public class Timer {

  private final Clock clock;
  private long startTime;
  private long stopTime;
  private boolean isDone;
  private final String name;
  private final List<Timer> nestedTimers = new ArrayList<Timer>();
  private final StackTimerLogger stackTimerLogger;

  public Timer(String name, Clock clock, StackTimerLogger logger) {
    this.name = name;
    this.clock = clock;
    this.stackTimerLogger = logger;
  }

  public void start() {
    verifyNotDoneYet();
    // should we verify not started?
    startTime = clock.currentTimeMillis();
  }

  private void verifyNotDoneYet() {
    if (isDone)
      stackTimerLogger.error("You can't use this timer because it was turned off and summarized.");
  }

  public void stop() {
    verifyNotDoneYet();
    if (stopTime > 0) {
      stackTimerLogger.error("You can't stop this timer [" + name + "] multiple times, but you are attempting to.");
      return;
    }
    if (startTime == 0) {
      stackTimerLogger.error("You can't stop this timer [" + name + "] until you first start it, but you are attempting to.");
      return;
    }
    stopTime = clock.currentTimeMillis();
  }

  public TimerSummary doneUsing() {
    return doneUsing(startTime);
  }

  private TimerSummary doneUsing(long withStartTimeOffset) {
    isDone = true;
    List<TimerSummary> nestedSummaries = new ArrayList<TimerSummary>();
    for (Timer nestedTimer : nestedTimers) {
      nestedSummaries.add(nestedTimer.doneUsing(withStartTimeOffset));
    }
    // TODO more tests needed for offset stuff
    return new TimerSummary(name, startTime - withStartTimeOffset, stopTime - withStartTimeOffset,
        nestedSummaries);
  }

  public Timer nestedStartedTimer(String name) {
    Timer nestedTimer = new Timer(name, clock, null);
    nestedTimers.add(nestedTimer);
    nestedTimer.start();
    return nestedTimer;
  }
}
