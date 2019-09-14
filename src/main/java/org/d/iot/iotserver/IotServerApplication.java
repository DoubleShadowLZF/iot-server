package org.d.iot.iotserver;

        import org.d.iot.iotserver.config.IotServerProperties;
        import org.d.iot.iotserver.service.IotDoorLockManager;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.ApplicationArguments;
        import org.springframework.boot.ApplicationRunner;
        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;
        import org.springframework.context.ApplicationContext;
        import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IotServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotServerApplication.class, args);
    }


}
