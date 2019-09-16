package org.d.iot.iotserver.swing;

import org.d.iot.iotserver.swing.action.FileServerAction;

import javax.swing.*;
import java.awt.*;

/**
 * ClassName: OperateView <br>
 * Description: <br>
 * date: 2019/9/15 12:18<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class OperateView extends JDialog {

  protected JPanel centerPanel;
  protected JPanel toolPanel;
  protected JPanel menuPanel;
  protected JPanel southPanel;
  protected JScrollPane centerScrollPane;

  public OperateView(JFrame parent) {
    super(parent, true);
    init();
  }

  public void init() {
    BorderLayout mainLayout = new BorderLayout();
    // 返回此对话框的 contentPane对象,
    // 并设置此容器的布局管理器。
    getContentPane().setLayout(mainLayout);
    toolPanel = new JPanel();
    //    toolPanel.setLayout(null);
    toolPanel.setPreferredSize(new Dimension(750, 30));
    menuPanel = new JPanel();
    // 将此组件的首选大小设置为常量值。 随后电话getPreferredSize总是返回此值。 将首选大小设置为null可恢复默认行为。
    // Dimension类封装单个对象中组件的宽度和高度（以整数精度）。
    menuPanel.setPreferredSize(new Dimension(100, 30));
    centerPanel = new JPanel();
    centerScrollPane = new JScrollPane();
    centerScrollPane.setViewportView(centerPanel);
    menuPanel.setPreferredSize(new Dimension(100, 30));
    southPanel = new JPanel();
    menuPanel.setPreferredSize(new Dimension(100, 30));
    // 初始化上半部分界面
    initNorthView();
    /*----------------------------界面设置-----------------------------*/
    this.setResizable(true);
    this.setSize(800, 800);
    // 屏幕居中
    // 获取窗口宽
    int windowWidth = this.getWidth();
    // 获取窗口高
    int windowHeight = this.getHeight();
    // 定义工具包
    Toolkit kit = Toolkit.getDefaultToolkit();
    // 获取屏幕尺寸
    Dimension screenSize = kit.getScreenSize();
    // 获取屏幕的宽
    int screenWidth = screenSize.width;
    // 获取屏幕的高
    int screenHeight = screenSize.height;
    this.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);

    // 当点击窗口的关闭按钮是退出程序（没有这句，程序不会退出）
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
  }

  private void initNorthView() {
    /*-----------------------上部部分内容----------------------------------*/
    GridLayout northLayout = new GridLayout(20, 1);
    menuPanel.setLayout(northLayout);
    // 服务菜单

    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("服务");
    menuBar.add(menu);

    /*JMenu sonMenu = new JMenu("文件服务");
    menu.add(sonMenu);*/

    JMenuItem menuItem = new JMenuItem("文件服务");
    menuItem.addActionListener(new FileServerAction(this, menuPanel, centerPanel));
    //    sonMenu.add(menuItem);
    menu.add(menuItem);
    // 设置工具栏从左到右排版，水平和垂直间隔都是10
    toolPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
    toolPanel.add(menuBar);

    // end 服务菜单
    Container container = getContentPane();
    container.add(toolPanel, BorderLayout.NORTH);
    getContentPane().add(menuPanel, BorderLayout.WEST);
    getContentPane().add(centerScrollPane, BorderLayout.CENTER);
    getContentPane().add(southPanel, BorderLayout.SOUTH);
  }

  public static void main(String[] args) {
    OperateView operateView = new OperateView(null);
    operateView.setVisible(true);
  }
}
