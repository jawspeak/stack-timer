package com.jawspeak.stacktimer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jawspeak.util.Clock.FakeClock;
import com.jawspeak.util.Clock.RealtimeClock;

public class StackTimerTest {
  long someDayInAugust2009 = 1249569531L;
  FakeClock fakeClock = new FakeClock(someDayInAugust2009);
  SpyLoggerForTesting spyLogger = new SpyLoggerForTesting();
  
  @Before
  public void setup() {
    StackTimer.setClockForTest(fakeClock);
    StackTimer.setLogger(spyLogger);
  }
  
  @After
  public void teardown() {
    // clean up the global state from the statics
    StackTimer.doneUsing();
    StackTimer.setClockForTest(new RealtimeClock()); 
    StackTimer.setLogger(null);
  }
  
  
  @Test
  public void startAndRecordHappyPath() throws Exception {
    StackTimer.start("timer0");
    StackTimer.stop("timer0");
//    assertEquals(1, spyLogger.loggedMessages.size()); // ???? or empty?
    assertEquals(0, spyLogger.errorMessages.size());
    TimerSummary summary = StackTimer.doneUsing();
    assertNotNull(summary);
  }
  
  @Test
  public void startButStopTooManyTimesLogsError() throws Exception {
    StackTimer.start("timer0");
    StackTimer.stop("timer0");
    StackTimer.stop("timer0");
    StackTimer.stop("timer0");
    assertEquals(0, spyLogger.loggedMessages.size()); // ???? or empty?
    assertEquals(2, spyLogger.errorMessages.size());
  }

  @Test
  public void doneUsingThenStopLogsError() throws Exception {
    StackTimer.start("timer0");
    StackTimer.stop("timer0");
    StackTimer.doneUsing();
    StackTimer.stop("timer0");
    assertEquals(0, spyLogger.loggedMessages.size()); // ???? or empty?
    assertEquals(1, spyLogger.errorMessages.size());
  }
  
  @Test
  public void startTooManyTimesIsIgnored() throws Exception {
    StackTimer.start("timer0");
    StackTimer.start("timer0");
    StackTimer.start("timer0");
    StackTimer.stop("timer0");
    assertEquals(0, spyLogger.loggedMessages.size()); // ???? or empty?
    assertEquals(0, spyLogger.errorMessages.size());    
  }
  
  @Test
  public void stopWhenNotStartedLogsError() throws Exception {
    StackTimer.stop("timer0");
    assertEquals(0, spyLogger.loggedMessages.size()); // ???? or empty?
    assertEquals(1, spyLogger.errorMessages.size());
  }
}
