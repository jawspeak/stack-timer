package com.jawspeak.stacktimer;

public class TimerSummaryHtmlReport {

  private final TimerSummary timerSummary;
  private StringBuilder sb = new StringBuilder();

  public TimerSummaryHtmlReport(TimerSummary timerSummary) {
    this.timerSummary = timerSummary;
  }
  
  /** Renders an html fragment suitable for including on an html page for reporting timings */
  public String render() {
    renderHeader();
    renderChart();
    return sb.toString();
  }

  private void renderHeader() {
    sb.append("<style>\n" + 
    		"#chart-stats {\n" + 
    		"    background-color: #f1f1f1;\n" + 
    		"    margin: auto;\n" + 
    		"    padding: 3px;\n" + 
    		"    width: 1000px;\n" + 
    		"\n" + 
    		"}\n" + 
    		".timeline {\n" + 
    		"    position: relative;\n" + 
    		"    line-height: 15px;\n" +
    		"    border: thin grey solid;\n" +
    		"    background-color: #ab9;\n" + 
    		"    margin: 1px 0 0 0;\n" + 
    		"    font-size: 10px;\n" + 
    		"}\n" + 
    		".hidden {\n" +
    		"    display: none;\n" +
    		"}\n" +
    		"\n" + 
    		"</style>\n");
  }
  
  
//  public String summaryString() {
//    return summaryString("");
//  }
//
//  private String summaryString(String prefix) {
//    StringBuffer sb = new StringBuffer();
//    sb.append(prefix).append(name).append(" start ").append(startMillis).append("ms\n");
//    for (TimerSummary nestedSummary : nestedSummaries) {
//      sb.append(nestedSummary.summaryString(prefix + "  "));
//    }
//    sb.append(prefix).append(name).append(" stop ").append(stopMillis).append("ms, duration ").append(stopMillis - startMillis).append("ms\n");
//    return sb.toString();
//  }
  
  private void renderChart() {
    sb.append("<div id=\"chart-stats\">\n" + 
    		      "Response Time For Request\n");    
    renderTimerSummary(timerSummary);
    sb.append("</div>\n");
  }

  private void renderTimerSummary(TimerSummary summary) { 
    sb.append("<div class=\"timeline\" style=\"left: ").append(summary.getStartMillis() * 10)
      .append("px; width: ").append(summary.getStopMillis() * 10).append("px;\">")
      .append(summary.getName()).append("<div class=\"hidden\">More Details: x,y,z And more</div>");
    for (TimerSummary nestedSummary : summary.getNestedSummaries()) {
      renderTimerSummary(nestedSummary);
    }
    sb.append("</div>\n");
  }

}
