package org.d.iot.nbserver.swing.demo;

import javax.swing.*;
import java.awt.*;

/**
 * ClassName: JPanelDemo <br>
 * Description: <br>
 * date: 2019/9/24 23:36<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class JPanelDemo {
  public static void main(String[] args) {
    JFrame frame = new JFrame();
    // 设置窗口大小和位置
    frame.setBounds(300, 100, 400, 200);
    JPanel panel = new JPanel();
    JLabel label = new JLabel("This is a label on panel.");
    panel.setBackground(Color.white);
    panel.add(label);
    frame.add(panel);
    frame.setVisible(true);
  }
}
