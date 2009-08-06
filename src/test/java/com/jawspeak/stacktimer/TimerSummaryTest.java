package com.jawspeak.stacktimer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.Lists;

public class TimerSummaryTest {
  
  @Test
  public void noHierarchy() throws Exception {
    TimerSummary summary = new TimerSummary("timer0", 0L, 100L);
    assertEquals(
        "timer0 start 0ms\n" +
    		"timer0 stop 100ms, duration 100ms\n", summary.summaryString());
  }
  
  @Test
  public void simpleHierarchy() throws Exception {
    TimerSummary nestedSummary = new TimerSummary("timer01", 5L, 15L);
    TimerSummary summary = new TimerSummary("timer0", 0L, 100L, Lists.newArrayList(nestedSummary));
    assertEquals(
        "timer0 start 0ms\n" +
    		"  timer01 start 5ms\n" +
    		"  timer01 stop 15ms, duration 10ms\n" +
    		"timer0 stop 100ms, duration 100ms\n", summary.summaryString());
  }
  
  @Test
  public void walkTwoTimers() throws Exception {
    TimerSummary nestedSummary = new TimerSummary("timer01", 5L, 15L);
    TimerSummary summary = new TimerSummary("timer0", 0L, 100L, Lists.newArrayList(nestedSummary));
    assertEquals(Lists.newArrayList(new TimerSummary("timer0", 0L, 100L, null), new TimerSummary("timer01", 5L, 15L, null)), summary.walkTimers());
  }
  
  @Test
  public void walkThreeTimers() throws Exception {
    TimerSummary nestedSummary1 = new TimerSummary("timer01", 5L, 11L, Lists.newArrayList(new TimerSummary("timer001", 5L, 10L, Lists.<TimerSummary>newArrayList(new TimerSummary("timer0001" ,6L, 7L)))));
    TimerSummary nestedSummary2 = new TimerSummary("timer02", 11L, 15L);
    TimerSummary summary = new TimerSummary("timer0", 0L, 100L, Lists.newArrayList(nestedSummary1, nestedSummary2));

    // TODO(jwolter) the problem this exposes to me is it isn't so elegant to have a TimerSummary object as I thought it would be. The summary could be constructed inappropriately, and then it is in error. Maybe I want the Timers to hold all the data needed for a summary. (start, stop, name, duration, nested) Or nested could be stored on a stack... Also it creates one more object than we may need (think of performance later!)
    assertEquals(
        "timer0 start 0ms\n" +
        "  timer01 start 5ms\n" +
        "    timer001 start 5ms\n" +
        "      timer0001 start 6ms\n" +
        "      timer0001 stop 7ms, duration 1ms\n" +
        "    timer001 stop 10ms, duration 5ms\n" +
        "  timer01 stop 11ms, duration 6ms\n" +
        "  timer02 start 11ms\n" +
        "  timer02 stop 15ms, duration 4ms\n" +
        "timer0 stop 100ms, duration 100ms\n", summary.summaryString());
  }
  
}
