package cn.addenda.component.stacktrace.test;

import cn.addenda.component.stacktrace.*;
import org.junit.Assert;
import org.junit.Test;

public class IdentifierMatherTest {

  @Test
  public void testIdentifier() {
    IdentifierMather identifierMatcher1 = IdentifierMatherFactory.getIdentifierMatcher(
            IdentifierMatherFactory.withHash(IdentifierMatherTest.class, "testIdentifier"));
    System.out.println(identifierMatcher1);
    Assert.assertEquals(ClassNameAndMethodNameMather.class, identifierMatcher1.getClass());
    IdentifierMather identifierMatcher2 = IdentifierMatherFactory.getIdentifierMatcher(IdentifierMatherTest.class);
    System.out.println(identifierMatcher2);
    Assert.assertEquals(ClassNameMather.class, identifierMatcher2.getClass());
    IdentifierMather identifierMatcher3 = IdentifierMatherFactory.getIdentifierMatcher(IdentifierMatherTest.class, true);
    System.out.println(identifierMatcher3);
    Assert.assertEquals(ClassNamePrefixMather.class, identifierMatcher3.getClass());
  }

}
