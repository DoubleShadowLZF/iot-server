package org.d.iot.nbserver.swing.demo;

import javax.swing.*;
import java.awt.*;

/**
 * ClassName: JFrameDemo <br>
 * Description: <br>
 * date: 2019/9/24 23:30<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class JFrameDemo extends JFrame {

  public JFrameDemo() throws HeadlessException {
    setTitle("Java First GUI Program");
    setSize(400, 200);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    JLabel label = new JLabel("This is a label with JFrame creating");
    Container container = getContentPane();
    container.add(label);
    setVisible(true);
  }

  public static void main(String[] args) {
    new JFrameDemo();
  }
}
