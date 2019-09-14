package org.d.iot.iotserver;

import org.d.iot.iotserver.config.IotServerProperties;
import org.d.iot.iotserver.service.IotDoorLockManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;

/**
 * ClassName: IotServerInit <br/>
 * Description: <br/>
 * date: 2019/9/5 22:17<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
public class IotServerInit implements ApplicationRunner {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private IotServerProperties properties;

    /**
     * 初始化线程池
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        IotDoorLockManager.init(context, properties);
    }

}
