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
    		"    height: 15px;\n" + 
    		"    background-color: #ab9;\n" + 
    		"    margin: 1px 0 0 0;\n" + 
    		"    font-size: 50%;\n" + 
    		"}\n" + 
    		"\n" + 
    		"</style>\n");
  }
  
  private void renderChart() {
    sb.append("<div id=\"chart-stats\">\n" + 
    		"Response Time For Request\n");    
    sb.append("<div class=\"timeline\" style=\"left: 0; width: 400px;\">timer0<div class=\"hidden\">More Details: x,y,z And more</div></div>\n");
    sb.append("</div>\n");
  }

}
