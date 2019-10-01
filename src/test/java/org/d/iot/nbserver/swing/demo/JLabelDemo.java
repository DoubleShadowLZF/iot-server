package org.d.iot.nbserver.swing.demo;

import javax.swing.*;

/**
 * ClassName: JLabelDemo <br>
 * Description: <br>
 * date: 2019/9/26 21:51<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class JLabelDemo {
  public static void main(String[] args) {
    JFrame frame = new JFrame();
    JPanel jp = new JPanel();
    JLabel label1 = new JLabel("text");
    JLabel label2 = new JLabel();
    label2.setText("set text");
    ImageIcon icon = new ImageIcon("C:\\Users\\Double\\Pictures\\Java\\favicon.ico");
    JLabel label3 = new JLabel("Email", icon, JLabel.CENTER);
    jp.add(label1);
    jp.add(label2);
    jp.add(label3);
    frame.add(jp);
    frame.setBounds(300, 200, 400, 1000);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
