package jpsim.simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jpsim.animation.*;

public class Office extends JPanel implements ActionListener {
  private final Claimer[][] claimers = new Claimer[3][2];
  private final Timer timer;
  private volatile WicketsManager manager;
  private final JLabel counterLabel = new JLabel("Waiting: 0", SwingConstants.CENTER);

  public Office(Config config) {
    super();
    createClaimers();
    manager = new WicketsManager(config);
    counterLabel.setBounds(350, 590, 100, 50);
    counterLabel.setForeground(Color.BLACK);
    add(counterLabel);
    setPreferredSize(new Dimension(960, 640));
    setBackground(Color.WHITE);
    setLayout(null);
    timer = new Timer(100, this);
    timer.start();
  }

  private void createClaimers() {
    claimers[0][0] = new Claimer(new AnimateLeft());
    claimers[0][1] = new Claimer(new AnimateLeft());
    claimers[1][0] = new Claimer(new AnimateForward());
    claimers[1][1] = new Claimer(new AnimateForward());
    claimers[2][0] = new Claimer(new AnimateRight());
    claimers[2][1] = new Claimer(new AnimateRight());
  }

  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D g2D = (Graphics2D) g;
    g2D.setColor(Color.GREEN);
    g2D.fillRect(65, 200, 50, 50);
    g2D.fillRect(455, 200, 50, 50);
    g2D.fillRect(845, 200, 50, 50);
    g2D.setColor(Color.BLUE);
    for (Claimer[] pair : claimers)
      for (Claimer claimer : pair)
        claimer.draw(g2D);
    g2D.setColor(Color.BLACK);
    counterLabel.setText("Waiting: " + manager.getCounter().toString());
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    for (int i = 0; i < 3; ++i) {
      var pair = claimers[i];
      if (manager.runningTask(i)) {
        pair[1].step();
        continue;
      }
      boolean wicketOpen = manager.get(i);
      boolean empty = pair[0].isEmpty();
      pair[1].step();
      if (wicketOpen) {
        pair[0].step();
        if (!empty) {
          manager.setOpen(i);
          Claimer tmp = pair[0];
          pair[0] = pair[1];
          pair[1] = tmp;
          manager.scheduleWaitTask(i);
        }
        continue;
      }
      if (empty) {
        manager.setClosed(i);
        pair[0].animate();
        continue;
      }
      if (pair[0].step())
        manager.scheduleSleepTask(i);
    }
    repaint();
  }
}
