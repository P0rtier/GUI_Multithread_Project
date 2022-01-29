package jpsim.simulation;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class WicketsManager {
  private final AtomicBoolean[] wicketsState = new AtomicBoolean[3]; // true - open, false - closed
  private final AtomicBoolean[] runningTask = new AtomicBoolean[3];
  private final Timer[] taskRunner = new Timer[3];
  private final AtomicInteger counter;
  private final int minWicketDelay, maxWicketDelay;
  private static final Random rng = new Random();

  public WicketsManager(final Config config) {
    this.counter = createCounter(config);
    this.minWicketDelay = config.getMinWicketDelay();
    this.maxWicketDelay = config.getMaxWicketDelay();
    for (int i = 0; i < 3; ++i) {
      runningTask[i] = new AtomicBoolean();
      wicketsState[i] = new AtomicBoolean(true);
      taskRunner[i] = new Timer();
      scheduleWaitTask(i);
    }
  }

  private AtomicInteger createCounter(final Config config) {
    AtomicInteger aInteger = new AtomicInteger(0);
    Thread counter = new Thread(() -> {
      while (true) {
        try {
          Thread.sleep(rng.nextInt(config.getMinNewClaimerTime(), config.getMaxNewClaimerTime() + 1), 0);
        } catch (InterruptedException e) {
          break;
        }
        aInteger.addAndGet(rng.nextInt(config.getMinNewClaimers(), config.getMaxNewClaimers() + 1));
        synchronized (aInteger) {
          aInteger.notifyAll();
        }
      }
    });
    counter.start();
    return aInteger;
  }

  private TimerTask wrap(Runnable r) {
    return new TimerTask() {
      @Override
      public void run() {
        r.run();
      }
    };
  }

  void scheduleSleepTask(int index) {
    runningTask[index].set(true);
    taskRunner[index].schedule(wrap(() -> {
      try {
        Thread.sleep(generateWicketDelay());
        wicketsState[index].set(true);
      } catch (InterruptedException e) {
      }
      runningTask[index].set(false);
    }), 0);
  }

  void scheduleWaitTask(int index) {
    runningTask[index].set(true);
    taskRunner[index].schedule(wrap(() -> {
      try {
        synchronized (counter) {
          while (counter.getAndUpdate(x -> x > 0 ? x - 1 : x) == 0)
            counter.wait();
        }
        wicketsState[index].set(false);
      } catch (InterruptedException e) {
      }
      runningTask[index].set(false);
    }), 0);
  }

  boolean runningTask(int index) {
    return runningTask[index].get();
  }

  int generateWicketDelay() {
    return rng.nextInt(minWicketDelay, maxWicketDelay);
  }

  Integer getCounter() {
    return counter.get();
  }

  boolean get(int index) {
    return wicketsState[index].get();
  }

  void setOpen(int index) {
    wicketsState[index].set(true);
  }

  void setClosed(int index) {
    wicketsState[index].set(false);
  }
}
