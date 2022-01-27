package jpsim.simulation;

public class Config {
  private int minNewClaimerTime = 1000;
  private int maxNewClaimerTime = 6000;
  private int minNewClaimers = 1;
  private int maxNewClaimers = 2;
  private int minWicketDelay = 8000;
  private int maxWicketDelay = 13000;

  public Config() {}

  public boolean isValid() {
    return minNewClaimerTime > 0
      && maxNewClaimerTime >= minNewClaimerTime
      && minNewClaimers > 0
      && maxNewClaimers >= minNewClaimers
      && minWicketDelay >= 0
      && maxWicketDelay >= minWicketDelay;
  }

  public int getMinNewClaimerTime() { return minNewClaimerTime; }

  public int getMaxNewClaimerTime() { return maxNewClaimerTime; }

  public int getMinNewClaimers() { return minNewClaimers; }

  public int getMaxNewClaimers() { return maxNewClaimers; }

  public int getMinWicketDelay() { return minWicketDelay; }

  public int getMaxWicketDelay() { return maxWicketDelay; }

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

  public boolean setMinWicketDelay(final String value) {
    try {
      minWicketDelay = Integer.parseInt(value);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  public boolean setMaxWicketDelay(final String value) {
    try {
      maxWicketDelay = Integer.parseInt(value);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }
}
