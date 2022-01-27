package jpsim.simulation;

import java.awt.Graphics2D;

import jpsim.animation.*;

public class Claimer {
  final private Animate animateTemplate;
  private Animate animate = null;
  private boolean inDirection = true;

  public Claimer(Animate animate) {
    animateTemplate = animate;
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
    g2D.fillOval(animate.getX(), animate.getY(), 50, 50);
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
}
