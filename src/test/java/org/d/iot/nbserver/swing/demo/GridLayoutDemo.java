package org.d.iot.nbserver.swing.demo;

import javax.swing.*;
import java.awt.*;

/**
 * ClassName: GridLayoutDemo <br>
 * Description: <br>
 * date: 2019/9/25 23:55<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class GridLayoutDemo {
  public static void main(String[] args) {
    JFrame frame = new JFrame("This is a frame with grid layout");
    JPanel panel = new JPanel(new GridLayout(4, 4, 5, 5));
    panel.add(new JButton("7"));
    panel.add(new JButton("8"));
    panel.add(new JButton("9"));
    panel.add(new JButton("/"));
    panel.add(new JButton("4"));
    panel.add(new JButton("5"));
    panel.add(new JButton("6"));
    panel.add(new JButton("*"));
    panel.add(new JButton("1"));
    panel.add(new JButton("2"));
    panel.add(new JButton("3"));
    panel.add(new JButton("-"));
    panel.add(new JButton("0"));
    panel.add(new JButton("."));
    panel.add(new JButton("="));
    panel.add(new JButton("+"));
    frame.add(panel);
    frame.setBounds(300, 200, 400, 300);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
