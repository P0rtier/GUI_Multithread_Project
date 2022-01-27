package jpsim.animation;

import java.lang.Cloneable;

public interface Animate extends Cloneable {
  public int getX();
  public int getY();
  public boolean step(boolean in);
  public Object clone();
}
