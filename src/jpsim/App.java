package jpsim;

public class App {
  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        try {
          System.out.println("Running app...");
          App app = new App();
        } catch (Exception e) {
          e.printStackTrace(System.err);
        }
      }
    });
  }

  public App() {
  }
}
