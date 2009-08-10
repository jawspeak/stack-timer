package com.jawspeak.stacktimer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.jawspeak.util.Clock.FakeClock;

public class TimerTest {

  FakeClock clock = new FakeClock(1000000L);
  SpyLoggerForTesting spyLogger = new SpyLoggerForTesting();
  
  @Test
  public void startStopOneLevelTimer() throws Exception {
    Timer timer = new Timer("name", clock, spyLogger);
    timer.start();
    clock.incrementBy(1234L);
    timer.stop();
    TimerSummary summary = timer.summarize();
    assertEquals(new TimerSummary("name", 0L, 1234L), summary);
    assertEquals(0, spyLogger.errorMessages.size());
  }

  @Test
  public void startStopThreeLevelTimer() throws Exception {
    Timer timer0 = new Timer("timer0", clock, null);
    timer0.start();
    clock.incrementBy(1L);
    Timer timer01 = timer0.nestedStartedTimer("timer01"); 
    clock.incrementBy(2L);
    Timer timer011 = timer01.nestedStartedTimer("timer011");
    clock.incrementBy(4L);
    timer011.stop();
    clock.incrementBy(8L);
    timer01.stop();
    timer0.stop();
    TimerSummary summary = timer0.summarize();
    TimerSummary expectedSummary = new TimerSummary("timer0", 0L, 15L, Lists.newArrayList(
        new TimerSummary("timer01", 1L, 15L, Lists.newArrayList(
            new TimerSummary("timer011", 3L, 7L)))));
    
    assertEquals(expectedSummary, summary);
    assertEquals(0, spyLogger.errorMessages.size());
  }
  
  @Test
  public void cantStartAftersummarize() throws Exception {
    Timer timer = new Timer("", clock, spyLogger);
    timer.summarize();
    timer.start();
    assertEquals(1, spyLogger.errorMessages.size());
  }

  @Test
  public void cantStopAftersummarize() throws Exception {
    Timer timer = new Timer("", clock, spyLogger);
    timer.summarize();
    timer.stop();
    assertEquals(2, spyLogger.errorMessages.size());
  }
  
  @Test
  public void simpleHierarchy() throws Exception {
    Timer timer0 = new Timer("timer0", clock, spyLogger);
    timer0.start();
    clock.incrementBy(1);
    Timer timer01 = timer0.nestedStartedTimer("timer0_1");
    clock.incrementBy(2);
    Timer timer02 = timer0.nestedStartedTimer("timer0_2");
    clock.incrementBy(4);
    Timer timer021 = timer02.nestedStartedTimer("timer0_2_1");
    clock.incrementBy(8);
    // do we want to stop all of the timers when the outer timer ends, or is that a problem condition?
    timer0.stop();
    TimerSummary timerSummary = timer0.summarize();
  }

  // TODO(jwolter) do we want to merge a sub-timer i.e. when we have a call over the remote wire.
  
}
