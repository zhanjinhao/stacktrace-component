package cn.addenda.component.stacktrace;

public interface IdentifierMather {
  String HASH = "#";

  boolean match(StackTraceElement stackTraceElement);

}
