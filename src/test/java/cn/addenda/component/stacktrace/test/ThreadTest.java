package cn.addenda.component.stacktrace.test;

import org.junit.Test;

public class ThreadTest {

  @Test
  public void test() {
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    for (StackTraceElement stackTraceElement : stackTrace) {
      System.out.println(stackTraceElement);
    }
  }

}
