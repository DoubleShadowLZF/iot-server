package org.d.iot.iotserver.file;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: FileProperties <br>
 * Description: <br>
 * date: 2019/9/14 22:53<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
@ConfigurationProperties(prefix = "iot.file")
@Configuration
@Data
public class FileProperties {
  private Integer port = 8001;
  private String name = "File-server";
  private String filePath = "D:\\temp";
}
