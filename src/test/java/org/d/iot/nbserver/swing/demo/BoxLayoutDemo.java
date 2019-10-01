package org.d.iot.nbserver.swing.demo;

import javax.swing.*;
import java.awt.*;

/**
 * ClassName: BoxLayoutDemo <br>
 * Description: <br>
 * date: 2019/9/26 0:18<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class BoxLayoutDemo {
  public static void main(String[] args) {
    JFrame frame = new JFrame("This is a frame with box layout.");
    // 创建横向的Box容器
    Box b1 = Box.createHorizontalBox();
    // 创建纵向Box容器
    Box b2 = Box.createVerticalBox();
    // 将外层横向Box添加窗体
    frame.add(b1);
    // 添加高度为200的垂直框架
    b1.add(Box.createVerticalStrut(200));
    b1.add(new JButton("West"));
    // 添加长度为140的水平框架
    b1.add(Box.createHorizontalStrut(140));
    b1.add(new JButton("East"));
    // 添加水平胶水
    b1.add(Box.createHorizontalGlue());
    b1.add(b2);

    // 添加宽度为100，高度为20的固定区域
    b1.add(Box.createRigidArea(new Dimension(100, 20)));
    b2.add(new JButton("North"));
    b2.add(Box.createVerticalGlue());
    b2.add(new JButton("South"));
    // 添加长度为40的垂直框架
    b2.add(Box.createVerticalStrut(40));

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBounds(100, 100, 400, 200);
    frame.setVisible(true);
  }
}
