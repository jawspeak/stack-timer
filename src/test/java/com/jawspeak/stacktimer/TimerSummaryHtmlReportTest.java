package com.jawspeak.stacktimer;

import java.io.FileWriter;
import java.util.ArrayList;

import org.junit.Test;

import com.google.common.collect.Lists;

public class TimerSummaryHtmlReportTest {
  
  @Test
  public void notSoMuchATestAsASampleGenerator() throws Exception {
    // we could use htmlunit, etc to test this if we want.
    TimerSummary summary = new TimerSummary("timer0", 0, 300, new ArrayList<TimerSummary>());
    TimerSummaryHtmlReport htmlReport = new TimerSummaryHtmlReport(summary);
    
    FileWriter fileWriter = new FileWriter("target/sample-report-output-1.html");
    fileWriter.append(htmlReport.render());
    fileWriter.close();
  }

  @Test
  public void notSoMuchATestAsASampleGenerator2() throws Exception {
    TimerSummary nestedSummary1 = new TimerSummary("timer01", 5L, 11L, Lists.newArrayList(new TimerSummary("timer001", 5L, 10L, Lists.<TimerSummary>newArrayList(new TimerSummary("timer0001" ,6L, 7L, new ArrayList())))));
    TimerSummary nestedSummary2 = new TimerSummary("timer02", 11L, 15L, new ArrayList());
    TimerSummary summary = new TimerSummary("timer0", 0L, 100L, Lists.newArrayList(nestedSummary1, nestedSummary2));
    TimerSummaryHtmlReport htmlReport = new TimerSummaryHtmlReport(summary);
    
    FileWriter fileWriter = new FileWriter("target/sample-report-output-2.html");
    fileWriter.append(htmlReport.render());
    fileWriter.close();
  }
}
