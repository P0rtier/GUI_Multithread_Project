/**
 * @author Damian Zimon <damianzim>
 * @date 11.10.2021
 */


package jpsim.utils;

import java.util.HashMap;
import java.util.HashSet;

public class Args {
  private HashSet<String> flags;
  private HashMap<String, String> options;

  public Args() {
    flags = new HashSet<String>();
    options = new HashMap<String, String>();
  }

  public Args(final String[] args) {
    this();
    resolveArgs(args);
  }

  public boolean isFlag(final String name) {
    return flags.contains(name);
  }

  public boolean isOption(final String name) {
    return options.containsKey(name);
  }

  public boolean resolveArgs(final String[] args) {
    flags.clear();
    options.clear();
    if (args.length == 0 || args[0].length() == 0)
      return false;
    for (int i = 0; i < args.length; ++i) {
      String arg = args[i];
      if (!arg.startsWith("--"))
        continue;
      arg = arg.substring(2);
      if (arg.length() == 0)
        continue;
      String next = i + 1 < args.length ? args[i + 1] : null;
      if (next == null) {
        flags.add(arg);
        break;
      } else if (next.equals("-")) {
      } else if (next.startsWith("--")) {
        flags.add(arg);
      } else {
        options.put(arg, next);
      }
    }
    return true;
  }

  public String value(final String name) {
    return options.get(name);
  }

  public String value(final String name, final String replacement) {
    String value = options.get(name);
    return value != null ? value : replacement;
  }
}
