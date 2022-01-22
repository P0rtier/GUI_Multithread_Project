package jpsim;

import java.awt.HeadlessException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import jpsim.simulation.Config;

public class Office extends JFrame {
  final private Config config;
  private JPanel content;

  Office(final Config config) throws HeadlessException {
    this.config = config;
    setWindow();
  }

  private void setWindow() {
    setTitle("Kolejka w urzędzie");


    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(960, 640);

    content = new JPanel();
    content.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(content);
    content.setLayout(null);
  }
}
