package jpsim.animation;

public class AnimateRight extends Animation {
  public AnimateRight() {
    super(845, 200);
  }

  @Override
  public boolean step(boolean in) {
    y -= 10;
    if (!in)
      return y == 0;
    x += 10;;
    return isInWicket();
  }
}
