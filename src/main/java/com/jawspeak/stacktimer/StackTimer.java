package com.jawspeak.stacktimer;

import com.jawspeak.util.Clock;

public class StackTimer {

  private static final ThreadLocal<Timer> timers = new ThreadLocal<Timer>();
  private static Clock clock = new Clock.RealtimeClock();
  private static StackTimerLogger logger; // TODO default implementation
  
  public static void start(String name) {
    if (timers.get() == null) { 
      Timer timer = new Timer(name, clock, logger);
      timers.set(timer);
      timer.start();
    }
  }

  public static void stop(String name) {
    Timer timer = timers.get();
    if (timer == null) {
      logger.error("Attempted to stop a timer that was never started: " + name);
      return;
    }
    timer.stop();
  }

  static void setClockForTest(Clock fakeClock) {
    clock = fakeClock;
  }

  public static TimerSummary doneUsing() {
    Timer timer = timers.get();
    if (timer == null) {
      logger.error("Attempted to be done using a timer that was never started");
      return null; // TODO probably don't want to return null?
    }
    timers.remove();
    return timer.doneUsing();
  }

  public static void setLogger(StackTimerLogger logger) {
    StackTimer.logger = logger;
  }
}
