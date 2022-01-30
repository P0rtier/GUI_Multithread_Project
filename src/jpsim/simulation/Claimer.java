package jpsim.simulation;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

import jpsim.animation.*;

public class Claimer {
  final private Animate animateTemplate;
  private Animate animate = null;
  private boolean inDirection = true;
  private Image claimerImage;
  private final static Random random = new Random();

  public Claimer(Animate animate) {
    animateTemplate = animate;
    generateSex();
  }

  public void animate() {
    if (animate == null)
      animate = (Animate)animateTemplate.clone();
  }

  public boolean isEmpty() {
    return animate == null;
  }

  public void draw(Graphics2D g2D) {
    if (animate == null)
      return;
    g2D.drawImage(claimerImage, animate.getX(), animate.getY(), 100, 100, null);
  }

  public boolean step() {
    if (animate == null)
      return false;
    boolean result = animate.step(inDirection);
    if (result) {
      if (!inDirection)
        animate = null;
      inDirection = !inDirection;
    }
    return result;
  }

  private void generateSex() {
    if (random.nextInt(0,2) == 0)
      claimerImage = new ImageIcon("assets/man.png").getImage();
    else
      claimerImage = new ImageIcon("assets/woman.png").getImage();
  }
}
