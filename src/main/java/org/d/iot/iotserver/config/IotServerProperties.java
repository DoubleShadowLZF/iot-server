package org.d.iot.iotserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: IotServerProperties <br/>
 * Description: 服务器配置文件 <br/>
 * @date: 2019/9/5 23:16<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
@Configuration
@ConfigurationProperties(prefix = "iot.server")
@Data
public class IotServerProperties {

    /**
     * 服务器开启端口
     */
    private Integer port;
    /**
     * 设备超时时间，默认 10 秒
     */
    private Integer heartBeatTimeout = 10 * 1000;

}
