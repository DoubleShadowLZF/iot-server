package org.d.iot.iotserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: SslConfig <br>
 * Description: SSL 相关的配置类型<br>
 * date: 2019/9/14 16:33<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
@Configuration
@ConfigurationProperties("iot.ssl")
@Data
public class SslConfig {
  /** 是否使用 SSL 连接 */
  private boolean isUseSsl;

  /** 客户端 SSL 证书路径 */
  private String clientFilePath;

  /** 服务端 SSL 证书路径 */
  private String serverFilePath;
}
