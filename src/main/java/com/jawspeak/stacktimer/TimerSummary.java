package com.jawspeak.stacktimer;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.google.common.collect.Lists;

public class TimerSummary {
  private final String name;
  private final long startMillis;
  private final long stopMillis;
  private final List<TimerSummary> nestedSummaries;

  public TimerSummary(String name, long startMillis, long stopMillis,
      List<TimerSummary> nestedSummaries) {
    this.name = name;
    this.startMillis = startMillis;
    this.stopMillis = stopMillis;
    this.nestedSummaries = nestedSummaries;
  }

  public String toString() {
    return name + " " + startMillis + "-" + stopMillis + " [" + (nestedSummaries != null ? nestedSummaries.size() : "") + "]";
  }
  
  public String summaryString() {
    return summaryString("");
  }

  private String summaryString(String prefix) {
    StringBuffer sb = new StringBuffer();
    sb.append(prefix).append(name).append(" start ").append(startMillis).append("ms\n");
    for (TimerSummary nestedSummary : nestedSummaries) {
      sb.append(nestedSummary.summaryString(prefix + "  "));
    }
    sb.append(prefix).append(name).append(" stop ").append(stopMillis).append("ms, duration ").append(stopMillis - startMillis).append("ms\n");
    return sb.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((nestedSummaries == null) ? 0 : nestedSummaries.hashCode());
    result = prime * result + (int) (startMillis ^ (startMillis >>> 32));
    result = prime * result + (int) (stopMillis ^ (stopMillis >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    TimerSummary other = (TimerSummary) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (nestedSummaries == null) {
      if (other.nestedSummaries != null)
        return false;
    } else if (!nestedSummaries.equals(other.nestedSummaries))
      return false;
    if (startMillis != other.startMillis) {
      System.err.println("My start: " + startMillis + " but their start: " + other.startMillis);
      return false;
    }
    if (stopMillis != other.stopMillis)
      return false;
    return true;
  }

  public List<TimerSummary> walkTimers() {
    ArrayList<TimerSummary> timersInStartingOrder = Lists.newArrayList(new TimerSummary(name, startMillis, stopMillis, null));

    Stack<TimerSummary> stack = new Stack<TimerSummary>();
    for (int i = 0; i < nestedSummaries.size(); i++) {
      stack.push(nestedSummaries.get(i));
    }
    while (!stack.isEmpty()) {
      TimerSummary summary = stack.pop();
      timersInStartingOrder.add(new TimerSummary(summary.name, summary.startMillis, summary.stopMillis, null));
      for (int i = 0; i < summary.nestedSummaries.size(); i++) {
        stack.push(summary.nestedSummaries.get(i));  
      }
    } 
    return timersInStartingOrder;
  }
}
