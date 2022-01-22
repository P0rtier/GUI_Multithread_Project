package jpsim.simulation;

public class Config {
  private int minNewClaimerTime = 100;
  private int maxNewClaimerTime = 600;
  private int minNewClaimers = 1;
  private int maxNewClaimers = 2;

  public Config() {}

  public boolean isValid() {
    return minNewClaimerTime > 0
      && maxNewClaimerTime >= minNewClaimerTime
      && minNewClaimers > 0
      && maxNewClaimers >= minNewClaimers;
  }

  public int getMinNewClaimerTime() { return minNewClaimerTime; }

  public int getMaxNewClaimerTime() { return maxNewClaimerTime; }

  public int getMinNewClaimers() { return minNewClaimers; }

  public int getMaxNewClaimers() { return maxNewClaimers; }

  public boolean setMinNewClaimerTime(final String value) {
    try {
      minNewClaimerTime = Integer.parseInt(value);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  public boolean setMaxNewClaimerTime(final String value) {
    try {
      maxNewClaimerTime = Integer.parseInt(value);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  public boolean setMinNewClaimers(final String value) {
    try {
      minNewClaimers = Integer.parseInt(value);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  public boolean setMaxNewClaimers(final String value) {
    try {
      maxNewClaimers = Integer.parseInt(value);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }
}
