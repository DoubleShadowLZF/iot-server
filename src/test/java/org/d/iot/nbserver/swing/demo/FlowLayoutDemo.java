package org.d.iot.nbserver.swing.demo;

import javax.swing.*;
import java.awt.*;

/**
 * ClassName: FlowLayoutDemo <br>
 * Description: <br>
 * date: 2019/9/25 22:25<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class FlowLayoutDemo {
  public static void main(String[] args) {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JButton btn1 = new JButton("1");
    JButton btn2 = new JButton("2");
    JButton btn3 = new JButton("3");
    JButton btn4 = new JButton("4");
    JButton btn5 = new JButton("5");
    JButton btn6 = new JButton("6");
    JButton btn7 = new JButton("7");
    JButton btn8 = new JButton("8");
    JButton btn9 = new JButton("9");
    panel.add(btn1);
    panel.add(btn2);
    panel.add(btn3);
    panel.add(btn4);
    panel.add(btn5);
    panel.add(btn6);
    panel.add(btn7);
    panel.add(btn8);
    panel.add(btn9);
    panel.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 20));
    panel.setBackground(Color.gray);
    frame.add(panel);
    frame.setBounds(300, 200, 350, 150);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
