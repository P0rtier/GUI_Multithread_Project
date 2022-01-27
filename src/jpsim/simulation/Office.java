package jpsim;

import java.awt.HeadlessException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import jpsim.simulation.Config;
import jpsim.simulation.Kanwa;

public class Office extends JFrame {
  final private Config config;
  public Kanwa content;

  Office(final Config config) throws HeadlessException {
    this.config = config;
    setWindow();
    pack();
    setVisible(true);
  }

  private synchronized void setWindow() {
    setTitle("Kolejka w urzędzie");
    content = new Kanwa(config);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    this.add(content);
  }
}
