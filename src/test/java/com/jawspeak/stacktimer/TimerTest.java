package com.jawspeak.stacktimer;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.jawspeak.stacktimer.Timer.TimerDoneException;
import com.jawspeak.util.Clock.FakeClock;

public class TimerTest {

  FakeClock clock = new FakeClock(1000000L);
  
  @Test
  public void startStopOneLevelTimer() throws Exception {
    Timer timer = new Timer("name", clock);
    timer.start();
    clock.incrementBy(1234L);
    timer.stop();
    TimerSummary summary = timer.doneUsing();
    assertEquals(new TimerSummary("name", 0L, 1234L, new ArrayList()), summary);
  }

  @Test
  public void startStopThreeLevelTimer() throws Exception {
    Timer timer0 = new Timer("timer0", clock);
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
    TimerSummary summary = timer0.doneUsing();
    TimerSummary expectedSummary = new TimerSummary("timer0", 0L, 15L, Lists.newArrayList(
        new TimerSummary("timer01", 1L, 15L, Lists.newArrayList(
            new TimerSummary("timer011", 3L, 7L, new ArrayList())))));
    
    assertEquals(expectedSummary, summary);
  }
  
  @Test(expected=TimerDoneException.class)
  public void cantStartAfterDoneUsing() throws Exception {
    Timer timer = new Timer("", clock);
    timer.doneUsing();
    timer.start();
  }

  @Test(expected=TimerDoneException.class)
  public void cantStopAfterDoneUsing() throws Exception {
    Timer timer = new Timer("", clock);
    timer.doneUsing();
    timer.stop();
  }
  
  @Test
  public void simpleHierarchy() throws Exception {
    Timer timer0 = new Timer("timer0", clock);
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
    TimerSummary timerSummary = timer0.doneUsing();
    
    // do we want to merge a sub-timer i.e. when we have a call over the remote wire.
  }

}
