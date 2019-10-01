package org.d.iot.nbserver.swing.demo;

import javax.swing.*;
import java.awt.*;

/**
 * ClassName: GridBagLayoutDemo <br>
 * Description: <br>
 * date: 2019/9/26 0:02<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class GridBagLayoutDemo {
  public static void makeBtn(
      String title, JFrame frame, GridBagLayout layout, GridBagConstraints constraints) {
    JButton button = new JButton(title);
    layout.setConstraints(button, constraints);
    frame.add(button);
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("Dial Number");
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints constraints = new GridBagConstraints();
    frame.setLayout(layout);
    constraints.fill = GridBagConstraints.BOTH;
    constraints.weightx = 0.0;
    // 结束行
    constraints.gridwidth = GridBagConstraints.REMAINDER;
    JTextField tf = new JTextField("13929708255");
    layout.setConstraints(tf, constraints);
    frame.add(tf);
    constraints.weightx = 0.5;
    constraints.weighty = 0.2;
    constraints.gridwidth = 1;

    makeBtn("7", frame, layout, constraints);
    makeBtn("8", frame, layout, constraints);
    constraints.gridwidth = GridBagConstraints.REMAINDER;
    makeBtn("9", frame, layout, constraints);
    // 重新设置 gridwidth 的值
    constraints.gridwidth = 1;

    makeBtn("4", frame, layout, constraints);
    makeBtn("5", frame, layout, constraints);
    constraints.gridwidth = GridBagConstraints.REMAINDER;
    makeBtn("6", frame, layout, constraints);
    // 重新设置 gridwidth 的值
    constraints.gridwidth = 1;

    makeBtn("1", frame, layout, constraints);
    makeBtn("2", frame, layout, constraints);
    constraints.gridwidth = GridBagConstraints.REMAINDER;
    makeBtn("3", frame, layout, constraints);
    // 重新设置 gridwidth 的值
    constraints.gridwidth = 1;

    makeBtn("Return", frame, layout, constraints);
    constraints.gridwidth = GridBagConstraints.REMAINDER;
    makeBtn("Dial", frame, layout, constraints);
    constraints.gridwidth = 1;

    frame.setBounds(400, 400, 400, 400);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
