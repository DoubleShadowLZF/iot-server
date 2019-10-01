package org.d.iot.nbserver.swing.demo;

import javax.swing.*;
import java.awt.*;

/**
 * ClassName: JTextFieldDemo <br>
 * Description: <br>
 * date: 2019/9/26 22:34<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class JTextFieldDemo {
  public static void main(String[] args) {
    JFrame frame = new JFrame();
    JPanel jp = new JPanel();
    JTextField textField1 = new JTextField();
    textField1.setText("A normal text field.");
    JTextField textField2 = new JTextField(28);
    textField2.setFont(new Font("楷体", Font.BOLD, 16));
    textField2.setText("A fixed and initial text field.");
    JTextField textField3 = new JTextField(30);
    textField3.setText("Center alignment");
    textField3.setHorizontalAlignment(JTextField.CENTER);
    JTextField textField4 = new JTextField();
    textField4.setAutoscrolls(true);
    // 给文本框加个滚动条当然没有，要给文本区加上才行
    textField4.scrollRectToVisible(new Rectangle(0, 0, 80, 20));
    textField4.setPreferredSize(new Dimension(150, 20));
    textField4.setText("They should either match cells'vertical alignment or always be centered.");
    jp.add(textField1);
    jp.add(textField2);
    jp.add(textField3);
    jp.add(textField4);
    frame.add(jp);
    frame.setBounds(300, 200, 400, 300);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
