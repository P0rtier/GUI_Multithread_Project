package jpsim.animation;

public class AnimateForward extends Animation {
  public AnimateForward() {
    super(430, 200);
  }

  @Override
  public boolean step(boolean in) {
    y -= 10;
    return in ? isInWicket() : y == 0;
  }
}
