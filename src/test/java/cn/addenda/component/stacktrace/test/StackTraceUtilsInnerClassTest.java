package cn.addenda.component.stacktrace.test;

import cn.addenda.component.stacktrace.IdentifierMatherFactory;
import cn.addenda.component.stacktrace.StackTraceUtils;
import org.junit.Test;

public class StackTraceUtilsInnerClassTest {

  @Test
  public void test1() {
    System.out.println(new InnerClass().testInnerClass());
    System.out.println(new StaticInnerClass().testStaticInnerClass());
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
