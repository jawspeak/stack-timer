package com.jawspeak.stacktimer;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.google.common.collect.Lists;

public class TimerSummaryTest {

  
  @Test
  public void visualizeSummary() throws Exception {
    String timeline = "timer0 start 0 ms\n" +
                      "  timer01 start 2 ms\n" +
                      "  timer01 end 4 ms\n" +
                      "  timer02 start 8 ms\n" +
                      "    timer021 start 10 ms\n" +
                      "    timer021 end 22 ms\n" +
                      "  timer02 end 24 ms\n" +
                      "timer0 end 25 ms\n";
    // it is possible it would also be useful to have a label at certain points. but that could also
    // be accomplished by nesting a timer, and having the ending durations at the same. So a staggered
    // start and simultaneous ending.
  }
  
  @Test
  public void noHierarchy() throws Exception {
    TimerSummary summary = new TimerSummary("timer0", 0L, 100L, new ArrayList());
    assertEquals("timer0 start 0 ms\ntimer0 stop 100 ms\n", summary.summaryString());
  }
  
  @Test
  public void simpleHierarchy() throws Exception {
    TimerSummary nestedSummary = new TimerSummary("timer01", 5L, 15L, new ArrayList());
    TimerSummary summary = new TimerSummary("timer0", 0L, 100L, Lists.newArrayList(nestedSummary));
    assertEquals(
        "timer0 start 0 ms\n" +
    		"  timer01 start 5 ms\n" +
    		"  timer01 stop 15 ms\n" +
    		"timer0 stop 100 ms\n", summary.summaryString());
  }
  
  @Test
  public void walkTwoTimers() throws Exception {
    TimerSummary nestedSummary = new TimerSummary("timer01", 5L, 15L, new ArrayList());
    TimerSummary summary = new TimerSummary("timer0", 0L, 100L, Lists.newArrayList(nestedSummary));
    assertEquals(Lists.newArrayList(new TimerSummary("timer0", 0L, 100L, null), new TimerSummary("timer01", 5L, 15L, null)), summary.walkTimers());
  }
  
//  TODO(jwolter) checkin in so I can get initial checkin, then fix this. 
//  @Test
//  public void walkThreeTimers() throws Exception {
//    TimerSummary nestedSummary1 = new TimerSummary("timer01", 5L, 15L, Lists.newArrayList(new TimerSummary("timer001", 5L, 10L, Lists.<TimerSummary>newArrayList())));
//    TimerSummary nestedSummary2 = new TimerSummary("timer02", 5L, 15L, new ArrayList());
//    TimerSummary summary = new TimerSummary("timer0", 0L, 100L, Lists.newArrayList(nestedSummary1, nestedSummary2));
//    // TODO not sure if correct
//    assertEquals(Lists.newArrayList(new TimerSummary("timer0", 0L, 100L, null), new TimerSummary("timer01", 5L, 15L, null), new TimerSummary("timer001", 5L, 15L, null)), summary.walkTimers());
//  }

  @Test
  public void walkDeepTimers() throws Exception {
    
  }

  @Test
  public void walkBroadTimers() throws Exception {
    
  }
  
}
