package com.jawspeak.stacktimer;

import java.io.FileWriter;
import java.util.ArrayList;

import org.junit.Test;

public class TimerSummaryHtmlReportTest {
  
  @Test
  public void notSoMuchATestAsASampleGenerator() throws Exception {
    // we could use htmlunit, etc to test this if we want.
    TimerSummary summary = new TimerSummary("timer0", 0, 300, new ArrayList<TimerSummary>());
    TimerSummaryHtmlReport htmlReport = new TimerSummaryHtmlReport(summary);
    
    FileWriter fileWriter = new FileWriter("target/sample-report-output.html");
    fileWriter.append(htmlReport.render());
    fileWriter.close();
  }
}
