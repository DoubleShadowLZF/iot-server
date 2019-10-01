package org.d.iot.nbserver.swing.demo;

import javax.swing.*;
import java.awt.*;

/**
 * ClassName: CardLayoutDemo <br>
 * Description: <br>
 * date: 2019/9/25 22:34<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class CardLayoutDemo {
  public static void main(String[] args) {
    JFrame frame = new JFrame();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel cards = new JPanel(new CardLayout());
    panel1.add(new JButton("Sign In"));
    panel1.add(new JButton("Sign Up"));
    panel1.add(new JButton("Forgot Password"));
    panel2.add(new JTextField("Username: ", 20));
    panel2.add(new JTextField("Password: ", 20));
    panel2.add(new JTextField("Captcha", 20));
    cards.add(panel1, "card1");
    cards.add(panel2, "card2");
    CardLayout c1 = (CardLayout) cards.getLayout();
    //    c1.show(cards, "card1");
    c1.show(cards, "card2");
    frame.add(cards);
    frame.setBounds(300, 200, 400, 200);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
