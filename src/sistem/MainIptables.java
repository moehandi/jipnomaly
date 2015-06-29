package sistem;

import java.awt.*;
import javax.swing.UIManager;


public class MainIptables {
  boolean packFrame = false;

  public MainIptables() {
    MainFrame frame = new MainFrame();
    if (packFrame) {
      frame.pack();
    }else{
      frame.validate();
    }
    //posisi tengah Window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 4);
    frame.setVisible(true);
  }
  
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e) {
        e.printStackTrace();
    }
        new MainIptables();
  }
}
