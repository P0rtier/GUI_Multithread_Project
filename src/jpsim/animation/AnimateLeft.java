package jpsim.animation;

public class AnimateLeft extends Animation {
  public AnimateLeft() {
    super(40, 200);
  }

  @Override
  public boolean step(boolean in) {
    y -= 10;
    if (!in)
      return y == 0;
    x -= 10;
    return isInWicket();
  }
}

