package jpsim;

import java.awt.HeadlessException;
import java.util.Map;
import static java.util.Map.entry;
import javax.swing.JFrame;

import jpsim.simulation.Config;
import jpsim.simulation.Office;
import jpsim.utils.Args;

public class App extends JFrame {
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
                + "\t--min-new-claimers N\t\tN > 0\n"
                + "\t--max-new-claimers N\t\tN >= --min-new-claimers\n"
                + "\t--min-wicket-delay N\t\tN >= 0\n"
                + "\t--max-wicket-delay N\t\tN > --min-wicket-delay\n";
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
      entry("min-new-claimer-time", config::setMinNewClaimerTime),
      entry("max-new-claimer-time", config::setMinNewClaimerTime),
      entry("min-new-claimers", config::setMinNewClaimers),
      entry("max-new-claimers", config::setMaxNewClaimers),
      entry("min-wicket-delay", config::setMinWicketDelay),
      entry("max-wicket-delay", config::setMaxWicketDelay));

    for (Map.Entry<String, SetConfigEntry> entry : configSetUp.entrySet())
      if (!App.setConfigEntry(entry.getValue(), parser, entry.getKey()))
        return false;

    if (!config.isValid()) {
      System.out.println("Error: Invalid range values.");
      return false;
    }

    return true;
  }

  private Office office;

  App(final Config config) throws HeadlessException {
    setWindow(config);
    pack();
    setVisible(true);
  }

  private synchronized void setWindow(final Config config) {
    setTitle("Kolejka w urzędzie");
    office = new Office(config);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    add(office);
  }

  public static void main(String[] args) {
    Config config = new Config();
    if (!App.parseArgs(args, config))
      System.exit(1);

    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        try {
          App office = new App(config);
        } catch (Exception e) {
          e.printStackTrace(System.err);
        }
      }
    });
  }
}
