package org.d.iot.iotserver.file.server;

import lombok.Data;

import java.io.File;
import java.io.Serializable;
/**
 * ClassName: OperateView <br>
 * Description: <br>
 * date: 2019/9/15 12:18<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
@Data
public class FileQo implements Serializable {
  private static final long serialVersionUID = 1L;
  /** 文件 */
  private File file;
  /** md5 */
  private String md5Val;

  /** 文件名 */
  private String fileName;

  /** 文件的索引起始位置 */
  private int starPos;
  /** 文件索引结尾位置 */
  private int endPos;
  /** 文件大小 */
  private long fileSize;
  /** 状态 */
  private String status;
  /** 文件字节数组 */
  private byte[] bytes;
}
