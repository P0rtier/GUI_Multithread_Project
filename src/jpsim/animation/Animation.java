package jpsim.animation;

public abstract class Animation implements Animate {
  protected int x = 430, y = 590;
  private final int wicketX, wicketY;

  Animation(int wicketX, int wicketY) {
    this.wicketX = wicketX;
    this.wicketY = wicketY;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  protected boolean isInWicket() {
    return wicketX == x && wicketY == y;
  }

  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

  public abstract boolean step(boolean in);
}
