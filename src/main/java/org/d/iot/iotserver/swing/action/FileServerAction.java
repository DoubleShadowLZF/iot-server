package org.d.iot.iotserver.swing.action;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ClassName: FileServerAction <br>
 * Description: <br>
 * date: 2019/9/15 12:51<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
@Slf4j
public class FileServerAction implements ActionListener {
  private JDialog dialog;
  private JPanel menuPanel;
  private JPanel textPanel;

  public FileServerAction(JDialog dialog, JPanel menuPanel, JPanel textPanel) {
    this.dialog = dialog;
    this.menuPanel = menuPanel;
    this.textPanel = textPanel;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JMenuItem mi = (JMenuItem) e.getSource();
    System.out.println("您单击的菜单项：" + mi.getText());

    /*JDialog dialog = new JDialog(this.frame);
    int ret =
        JOptionPane.showConfirmDialog(frame, "是否打开文件服务", "提示", JOptionPane.YES_NO_CANCEL_OPTION);
    System.out.println("选择结果：" + ret);*/
    initFileServerView();
  }

  private void initFileServerView() {
    JButton openBtn = new JButton("打开服务");
    //    openBtn.setPreferredSize();
    openBtn.setSize(new Dimension(30, 30));
    //    openBtn.setBounds(0, 0, 30, 30);
    menuPanel.add(openBtn);
    JButton closeBtn = new JButton("关闭服务");
    openBtn.setPreferredSize(new Dimension(30, 30));
    menuPanel.add(closeBtn);
    JButton uploadBtn = new JButton("上传文件");
    menuPanel.add(uploadBtn);
    JButton downloadBtn = new JButton("下载文件");
    menuPanel.add(downloadBtn);
    JButton listBtn = new JButton("文件列表");
    menuPanel.add(listBtn);
    listBtn.addActionListener(
        e -> {
          // TODO 获取文件列表
          System.out.println("获取文件列表");
          showFileList();
        });

    dialog.setVisible(true);
  }

  private void showFileList() {
    //    GridLayout layout = new GridLayout();
    JLabel label = new JLabel("文件列表");
    //    menuPanel.setLayout(layout);
    textPanel.add(label);
    dialog.setVisible(true);
  }
}
