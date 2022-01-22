package jpsim;

import java.util.Map;
import static java.util.Map.entry;

import jpsim.simulation.Config;
import jpsim.utils.Args;

public class App {
  @FunctionalInterface
  interface SetConfigEntry {
    public boolean set(final String value);
  }

  private static void printHelp() {
    String help = "PROGRUNCMD [OPTIONS...]\n"
                + "\n"
                + "OPTIONS:\n"
                + "\t--min-new-claimer-time N\tN > 0\n"
                + "\t--max-new-claimer-time N\tN >= --min-new-claimer-time\n"
                + "\t--min-new-calimers N\t\tN > 0\n"
                + "\t--max-new-claimers N\t\tN >= --min-new-claimers\n";
    System.out.print(help);

  }

  private static boolean setConfigEntry(SetConfigEntry fn, final Args parser, final String key) {
    final String value = parser.value(key);
    if (value != null && !fn.set(value)) {
      System.out.format("Error: Invalid value for \"--%s\" option.\n", key);
      return false;
    }
    return true;
  }

  private static boolean parseArgs(final String[] args, Config config) {
    Args parser = new Args(args);
    if (parser.isFlag("help")) {
      App.printHelp();
      return false;
    }

    Map<String, SetConfigEntry> configSetUp = Map.ofEntries(
      entry("min-new-claimer-time", (value) -> config.setMinNewClaimerTime(value)),
      entry("max-new-claimer-time", (value) -> config.setMinNewClaimerTime(value)),
      entry("min-new-calimers", (value) -> config.setMinNewClaimers(value)),
      entry("max-new-claimers", (value) -> config.setMaxNewClaimers(value)));

    for (Map.Entry<String, SetConfigEntry> entry : configSetUp.entrySet())
      if (!App.setConfigEntry(entry.getValue(), parser, entry.getKey()))
        return false;

    if (!config.isValid()) {
      System.out.println("Error: Invalid range values.");
      return false;
    }

    return true;
  }

  public static void main(String[] args) {
    Config config = new Config();
    if (!App.parseArgs(args, config))
      System.exit(1);

    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        try {
          System.out.println("Running app...");
          Office office = new Office(config);
          office.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace(System.err);
        }
      }
    });
  }
}
