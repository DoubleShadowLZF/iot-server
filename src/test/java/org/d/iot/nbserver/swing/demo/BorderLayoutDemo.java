package org.d.iot.nbserver.swing.demo;

import javax.swing.*;
import java.awt.*;

/**
 * ClassName: BorderLayoutDemo <br>
 * Description: <br>
 * date: 2019/9/25 22:18<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class BorderLayoutDemo {
  public static void main(String[] args) {
    JFrame frame = new JFrame("This is a frame with border layout.");
    frame.setSize(400, 200);
    frame.setLayout(new BorderLayout());
    JButton northBtn = new JButton("North");
    JButton westBtn = new JButton("West");
    JButton eastBtn = new JButton("East");
    JButton southBtn = new JButton("South");
    JButton centerBtn = new JButton("centerBtn");
    frame.add(northBtn, BorderLayout.NORTH);
    frame.add(westBtn, BorderLayout.WEST);
    frame.add(eastBtn, BorderLayout.EAST);
    frame.add(southBtn, BorderLayout.SOUTH);
    frame.add(centerBtn, BorderLayout.CENTER);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
