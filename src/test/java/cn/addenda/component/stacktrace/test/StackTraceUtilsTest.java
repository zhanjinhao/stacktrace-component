package cn.addenda.component.stacktrace.test;

import cn.addenda.component.stacktrace.IdentifierMather;
import cn.addenda.component.stacktrace.StackTraceUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class StackTraceUtilsTest {

  @Test
  public void test() {
    Set<IdentifierMather> defaultFilterPrefixSet = StackTraceUtils.getDefaultExcludeSet();
    System.out.println(defaultFilterPrefixSet);
  }

  @Test
  public void test0() {
    String callerInfo = StackTraceUtils.getCallerInfo();
    System.out.println(callerInfo);
  }

  Supplier<String> a1 = new Supplier<String>() {
    @Override
    public String get() {
      return StackTraceUtils.getCallerInfo(true, true, true);
    }
  };
  Supplier<String> b1 = () -> {
    return StackTraceUtils.getCallerInfo(true, true, true);
  };
  Supplier<String> c1 = () -> StackTraceUtils.getCallerInfo(true, true, true);

  @Test
  public void test1() {
    Supplier<String> a = new Supplier<String>() {
      @Override
      public String get() {
        return StackTraceUtils.getCallerInfo(true, true, true);
      }
    };
    Supplier<String> b = () -> {
      return StackTraceUtils.getCallerInfo(true, true, true);
    };
    Supplier<String> c = () -> StackTraceUtils.getCallerInfo(true, true, true);


    Assert.assertEquals("StackTraceUtilsTest#test1", a.get());
    Assert.assertEquals("StackTraceUtilsTest#test1", b.get());
    Assert.assertEquals("StackTraceUtilsTest#test1", c.get());

    Assert.assertEquals("StackTraceUtilsTest#test1", a1.get());
    Assert.assertEquals("StackTraceUtilsTest#test1", b1.get());
    Assert.assertEquals("StackTraceUtilsTest#test1", c1.get());
  }


  Supplier<String> a2 = new Supplier<String>() {
    @Override
    public String get() {
      return StackTraceUtils.getCallerInfo(true, false, false);
    }
  };
  Supplier<String> b2 = () -> {
    return StackTraceUtils.getCallerInfo(true, false, false);
  };
  Supplier<String> c2 = () -> StackTraceUtils.getCallerInfo(true, false, false);

  @Test
  public void test2() {
    Supplier<String> a = new Supplier<String>() {
      @Override
      public String get() {
        return StackTraceUtils.getCallerInfo(true, false, false);
      }
    };
    Supplier<String> b = () -> {
      return StackTraceUtils.getCallerInfo(true, false, false);
    };

    Supplier<String> c = () -> StackTraceUtils.getCallerInfo(true, false, false);


    Assert.assertEquals("StackTraceUtilsTest$4#get", a.get());
    Assert.assertEquals("StackTraceUtilsTest#lambda$test2$6", b.get());
    Assert.assertEquals("StackTraceUtilsTest#lambda$test2$7", c.get());

    Assert.assertEquals("StackTraceUtilsTest$3#get", a2.get());
    Assert.assertEquals("StackTraceUtilsTest#lambda$new$4", b2.get());
    Assert.assertEquals("StackTraceUtilsTest#lambda$new$5", c2.get());
  }

  Supplier<String> a3 = new Supplier<String>() {
    @Override
    public String get() {
      return StackTraceUtils.getDetailedCallerInfo(true, true, true);
    }
  };
  Supplier<String> b3 = () -> {
    return StackTraceUtils.getDetailedCallerInfo(true, true, true);
  };
  Supplier<String> c3 = () -> StackTraceUtils.getDetailedCallerInfo(true, true, true);

  @Test
  public void test3() {
    Supplier<String> a = new Supplier<String>() {
      @Override
      public String get() {
        return StackTraceUtils.getDetailedCallerInfo(true, true, true);
      }
    };
    Supplier<String> b = () -> {
      return StackTraceUtils.getDetailedCallerInfo(true, true, true);
    };
    Supplier<String> c = () -> StackTraceUtils.getDetailedCallerInfo(true, true, true);

    Assert.assertEquals("StackTraceUtilsTest#test3 of StackTraceUtilsTest.java:120", a.get());
    Assert.assertEquals("StackTraceUtilsTest#test3 of StackTraceUtilsTest.java:121", b.get());
    Assert.assertEquals("StackTraceUtilsTest#test3 of StackTraceUtilsTest.java:122", c.get());

    Assert.assertEquals("StackTraceUtilsTest#test3 of StackTraceUtilsTest.java:124", a3.get());
    Assert.assertEquals("StackTraceUtilsTest#test3 of StackTraceUtilsTest.java:125", b3.get());
    Assert.assertEquals("StackTraceUtilsTest#test3 of StackTraceUtilsTest.java:126", c3.get());
  }

  Supplier<String> a4 = new Supplier<String>() {
    @Override
    public String get() {
      return StackTraceUtils.getDetailedCallerInfo(true, false, false);
    }
  };
  Supplier<String> b4 = () -> {
    return StackTraceUtils.getDetailedCallerInfo(true, false, false);
  };
  Supplier<String> c4 = () -> StackTraceUtils.getDetailedCallerInfo(true, false, false);

  @Test
  public void test4() {
    Supplier<String> a = new Supplier<String>() {
      @Override
      public String get() {
        return StackTraceUtils.getDetailedCallerInfo(true, false, false);
      }
    };
    Supplier<String> b = () -> {
      return StackTraceUtils.getDetailedCallerInfo(true, false, false);
    };
    Supplier<String> c = () -> StackTraceUtils.getDetailedCallerInfo(true, false, false);

    Assert.assertEquals("StackTraceUtilsTest$8#get of StackTraceUtilsTest.java:145", a.get());
    Assert.assertEquals("StackTraceUtilsTest#lambda$test4$14 of StackTraceUtilsTest.java:149", b.get());
    Assert.assertEquals("StackTraceUtilsTest#lambda$test4$15 of StackTraceUtilsTest.java:151", c.get());

    Assert.assertEquals("StackTraceUtilsTest$7#get of StackTraceUtilsTest.java:132", a4.get());
    Assert.assertEquals("StackTraceUtilsTest#lambda$new$12 of StackTraceUtilsTest.java:136", b4.get());
    Assert.assertEquals("StackTraceUtilsTest#lambda$new$13 of StackTraceUtilsTest.java:138", c4.get());
  }


  @Test
  public void test5() throws Exception {
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    Callable<String> callable = new Callable<String>() {
      @Override
      public String call() {
        return StackTraceUtils.getDetailedCallerInfo(false, false);
      }
    };

    Future<String> submit = executorService.submit(callable);

    String o = submit.get();
    Assert.assertEquals("StackTraceUtilsTest$9#call of StackTraceUtilsTest.java:170", o);
    executorService.awaitTermination(10, TimeUnit.SECONDS);
  }

}
