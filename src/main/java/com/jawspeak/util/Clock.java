package com.jawspeak.util;

public interface Clock {
  long currentTimeMillis();

  public static class RealtimeClock implements Clock {
    public long currentTimeMillis() {
      return System.currentTimeMillis();
    }
  }

  public static class FakeClock implements Clock {
    private long currentTime;

    public FakeClock(long currentTime) {
      this.currentTime = currentTime;
    }

    public void incrementBy(long milliseconds) {
      currentTime += milliseconds;
    }

    public long currentTimeMillis() {
      return currentTime;
    }
  }

}