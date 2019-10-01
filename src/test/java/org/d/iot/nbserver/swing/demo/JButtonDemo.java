package org.d.iot.nbserver.swing.demo;

import javax.swing.*;
import java.awt.*;

/**
 * ClassName: JButtonDemo <br>
 * Description: <br>
 * date: 2019/9/26 22:00<br>
 *
 * @author Double <br>
 * @link https://www.iconfont.cn/collections/detail?spm=a313x.7781069.1998910419.de12df413&cid=16472
 * @since JDK 1.8
 */
public class JButtonDemo {
  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.setSize(400, 200);
    JPanel jp = new JPanel();
    JButton btn1 = new JButton("None");
    JButton btn2 = new JButton("Color");
    JButton btn3 = new JButton("Disable");
    JButton btn4 = new JButton("Align");
    // 注意设置图标时，需要设置一个默认的图表
    JButton btn5 =
        new JButton("Icon", new ImageIcon("C:\\Users\\Double\\Pictures\\Java\\close.png"));
    jp.add(btn1);
    btn2.setBackground(Color.YELLOW);
    jp.add(btn2);
    btn3.setEnabled(false);
    jp.add(btn3);
    Dimension preferredSize = new Dimension(160, 60);
    btn4.setPreferredSize(preferredSize);
    btn4.setVerticalAlignment(SwingConstants.BOTTOM);
    jp.add(btn4);
    btn5.setRolloverEnabled(true);
    btn5.setPreferredSize(new Dimension(400, 300));
    btn5.setPressedIcon(new ImageIcon("C:\\Users\\Double\\Pictures\\Java\\check.png"));
    btn5.setSelectedIcon(new ImageIcon("C:\\Users\\Double\\Pictures\\Java\\view.png"));
    jp.add(btn5);
    frame.add(jp);
    frame.setBounds(300, 200, 600, 300);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
