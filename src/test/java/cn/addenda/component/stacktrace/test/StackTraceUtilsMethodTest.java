package cn.addenda.component.stacktrace.test;

import cn.addenda.component.stacktrace.IdentifierMatherFactory;
import cn.addenda.component.stacktrace.StackTraceUtils;
import org.junit.Assert;
import org.junit.Test;

public class StackTraceUtilsMethodTest {

  @Test
  public void test1() {
    Assert.assertEquals("StackTraceUtilsMethodTest#test2 of StackTraceUtilsMethodTest.java:16", test2());
  }

  private String test2() {
    return test3();
  }

  private String test3() {
    System.out.println(IdentifierMatherFactory.withHash(StackTraceUtilsMethodTest.class, "test3"));
    return StackTraceUtils.getDetailedCallerInfo(IdentifierMatherFactory.withHash(StackTraceUtilsMethodTest.class, "test3"));
  }

}
