package cn.addenda.component.stacktrace.test;

import cn.addenda.component.stacktrace.IdentifierMatherFactory;
import cn.addenda.component.stacktrace.StackTraceUtils;
import org.junit.Assert;
import org.junit.Test;

public class StackTraceUtilsInnerClassTest {

  @Test
  public void test1() {
    Assert.assertEquals("StackTraceUtilsInnerClassTest#test1 of StackTraceUtilsInnerClassTest.java:12", new InnerClass().testInnerClass());
    Assert.assertEquals("StackTraceUtilsInnerClassTest#test1 of StackTraceUtilsInnerClassTest.java:13", new StaticInnerClass().testStaticInnerClass());
  }

  class InnerClass {
    public String testInnerClass() {
      return StackTraceUtils.getDetailedCallerInfo(IdentifierMatherFactory.withHash(InnerClass.class));
    }
  }

  static class StaticInnerClass {
    public String testStaticInnerClass() {
      return StackTraceUtils.getDetailedCallerInfo(IdentifierMatherFactory.withHash(StaticInnerClass.class));
    }
  }


}
