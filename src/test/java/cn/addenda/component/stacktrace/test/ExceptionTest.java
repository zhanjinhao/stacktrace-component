package cn.addenda.component.stacktrace.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ExceptionTest {

  @Test
  public void test1() {
    RuntimeException runtimeException = new RuntimeException();
    runtimeException.printStackTrace();
  }

}
