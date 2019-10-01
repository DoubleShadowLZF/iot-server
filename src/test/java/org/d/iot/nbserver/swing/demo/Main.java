package org.d.iot.nbserver.swing.demo;

import javax.swing.*;
import java.awt.*;

public class Main {
  public static void main(String[] args) {
    AbstractButton jb = new JToggleButton("Press Me");
    jb.setPressedIcon(new MyIcon());
    System.out.println(jb.getPressedIcon());

    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().add(jb);
    f.pack();
    f.setVisible(true);
  }
}

class MyIcon implements Icon {
  public int getIconWidth() {
    return 32;
  }

  public int getIconHeight() {
    return 32;
  }

  public void paintIcon(Component c, Graphics g, int x, int y) {
    g.setColor(Color.red);
    g.fillRect(0, 0, 33, 33);
    g.drawString("java2s.com", 0, 20);
  }
}
