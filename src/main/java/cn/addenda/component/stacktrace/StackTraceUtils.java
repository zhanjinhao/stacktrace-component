package cn.addenda.component.stacktrace;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.helpers.MessageFormatter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author addenda
 * @since 2023/2/17 9:51
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StackTraceUtils {

  @Getter
  private static final Set<String> defaultExcludedClassNameSet;

  private static final String EXCLUDED_PATH = "META-INF/stacktrace-component.excluded";

  static {
    Set<String> lines = new HashSet<>();
    try {
      ClassLoader classLoader = StackTraceUtils.class.getClassLoader();
      Enumeration<URL> urls = classLoader.getResources(EXCLUDED_PATH);
      while (urls.hasMoreElements()) {
        URL url = urls.nextElement();
        URLConnection urlConnection = url.openConnection();
        urlConnection.setUseCaches(false);
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
          lines.add(line);
        }
      }
    } catch (Exception e) {
      throw new StackTraceException(MessageFormatter.format("Can not open file: [{}].", EXCLUDED_PATH).getMessage(), e);
    }
    lines.removeIf(a -> a == null || a.isEmpty());
    defaultExcludedClassNameSet = Collections.unmodifiableSet(lines);
  }

  /**
   * @param useSimpleClassName 是否按简写的类名输出
   * @param filterClassNames   全类名
   */
  public static String getCallerInfo(
          boolean useSimpleClassName, boolean ifExcludeLambda, boolean ifExcludeAnonymousInnerClass, String... filterClassNames) {
    StackTraceElement stackTraceElement = determineStackTraceElement(ifExcludeLambda, ifExcludeAnonymousInnerClass, filterClassNames);
    String className = stackTraceElement.getClassName();
    String methodName = stackTraceElement.getMethodName();
    if (useSimpleClassName) {
      className = extractSimpleClassName(className);
    }
    return className + "#" + methodName;
  }

  public static String getCallerInfo(boolean useSimpleClassName, boolean ifExcludeLambda, boolean ifExcludeAnonymousInnerClass) {
    return getCallerInfo(useSimpleClassName, ifExcludeLambda, ifExcludeAnonymousInnerClass, (String[]) null);
  }

  public static String getCallerInfo(boolean ifExcludeLambda, boolean ifExcludeAnonymousInnerClass) {
    return getCallerInfo(true, ifExcludeLambda, ifExcludeAnonymousInnerClass);
  }

  public static String getCallerInfo() {
    return getCallerInfo(true, false, false);
  }

  /**
   * @param useSimpleClassName 是否按简写的类名输出
   * @param filterClassNames   全类名
   */
  public static String getDetailedCallerInfo(
          boolean useSimpleClassName, boolean ifExcludeLambda, boolean ifExcludeAnonymousInnerClass, String... filterClassNames) {
    StackTraceElement stackTraceElement = determineStackTraceElement(ifExcludeLambda, ifExcludeAnonymousInnerClass, filterClassNames);
    String className = stackTraceElement.getClassName();
    String fileName = stackTraceElement.getFileName();
    int lineNumber = stackTraceElement.getLineNumber();
    String methodName = stackTraceElement.getMethodName();
    if (useSimpleClassName) {
      className = extractSimpleClassName(className);
    }
    return MessageFormatter.arrayFormat("{} in line[{}] of file[{}]", new Object[]{className + "#" + methodName, lineNumber, fileName}).getMessage();
  }

  public static String getDetailedCallerInfo(
          boolean useSimpleClassName, boolean ifExcludeLambda, boolean ifExcludeAnonymousInnerClass) {
    return getDetailedCallerInfo(useSimpleClassName, ifExcludeLambda, ifExcludeAnonymousInnerClass, (String) null);
  }

  public static String getDetailedCallerInfo(boolean ifExcludeLambda, boolean ifExcludeAnonymousInnerClass) {
    return getDetailedCallerInfo(true, ifExcludeLambda, ifExcludeAnonymousInnerClass, (String[]) null);
  }

  public static String getDetailedCallerInfo() {
    return getDetailedCallerInfo(true, false, false);
  }

  private static StackTraceElement determineStackTraceElement(
          boolean ifExcludeLambda, boolean ifExcludeAnonymousInnerClass, String... filterClassNames) {
    Set<String> filterClassNameSet = defaultExcludedClassNameSet;
    if (filterClassNames != null) {
      filterClassNameSet = new HashSet<>(defaultExcludedClassNameSet);
      filterClassNameSet.addAll(Arrays.stream(filterClassNames).collect(Collectors.toList()));
    }
    return determineStackTraceElement(Thread.currentThread().getStackTrace(), filterClassNameSet, ifExcludeLambda, ifExcludeAnonymousInnerClass);
  }

  private static StackTraceElement determineStackTraceElement(
          StackTraceElement[] stackTraceElements, Set<String> filterClassNameSet,
          boolean ifExcludeLambda, boolean ifExcludeAnonymousInnerClass) {
    if (stackTraceElements == null || stackTraceElements.length == 0) {
      throw new IllegalArgumentException(MessageFormatter.format("The arg `stackTraceElements`[{}] can not null and can not be empty.", stackTraceElements).getMessage());
    }
    for (StackTraceElement stackTraceElement : stackTraceElements) {
      if (ifExcludeLambda && stackTraceElement.getMethodName().matches(".*lambda\\$.*")) {
        continue;
      }
      String className = stackTraceElement.getClassName();
      if (ifExcludeAnonymousInnerClass && className.matches(".*\\$\\d+.*")) {
        continue;
      }
      boolean flag = false;
      for (String filterClassName : filterClassNameSet) {
        if (className.equals(filterClassName)) {
          flag = true;
          break;
        }
      }
      if (!flag) {
        return stackTraceElement;
      }
    }
    String a = "All elements of `stackTraceElements` are excluded. The `stackTraceElements` are [{}]. The `filterClassNameSet` are [{}].";
    throw new StackTraceException(MessageFormatter.arrayFormat(a, new Object[]{toString(stackTraceElements), toString(filterClassNameSet)}).getMessage());
  }

  private static String toString(StackTraceElement[] stackTraceElements) {
    return Arrays.stream(stackTraceElements).map(StackTraceElement::toString).collect(Collectors.joining(",", "[", "]"));
  }

  private static String toString(Set<String> filterClassNameSet) {
    return filterClassNameSet.stream().collect(Collectors.joining(",", "[", "]"));
  }

  private static String extractSimpleClassName(String className) {
    StringBuilder simpleClassName = new StringBuilder();
    for (int i = className.length() - 1; i > -1; i--) {
      char c = className.charAt(i);
      if (c == '.') {
        break;
      }
      simpleClassName.append(c);
    }
    return simpleClassName.reverse().toString();
  }

}
