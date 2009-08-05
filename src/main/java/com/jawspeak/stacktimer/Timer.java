package com.jawspeak.stacktimer;

import java.util.ArrayList;
import java.util.List;

import com.jawspeak.util.Clock;

public class Timer {

  public static class TimerDoneException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TimerDoneException() {
      // TODO do we want to throw an exception, or just log it as an error?
      super("You can't use this timer because it was turned off and summarized.");
    }
  }

  private final Clock clock;
  private long startTime;
  private long stopTime;
  private boolean isDone;
  private final String name;
  private final List<Timer> nestedTimers = new ArrayList<Timer>();

  public Timer(String name, Clock clock) {
    this.name = name;
    this.clock = clock;
  }

  public void start() {
    verifyNotDoneYet();
    // should we verify not started?
    startTime = clock.currentTimeMillis();
  }

  private void verifyNotDoneYet() {
    if (isDone)
      throw new TimerDoneException();
  }

  public void stop() {
    verifyNotDoneYet();
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
    return new TimerSummary(name, startTime - withStartTimeOffset, stopTime - withStartTimeOffset, nestedSummaries);
  }
  
  public Timer nestedStartedTimer(String name) {
    Timer nestedTimer = new Timer(name, clock);
    nestedTimers.add(nestedTimer);
    nestedTimer.start();
    return nestedTimer;
  }
}

