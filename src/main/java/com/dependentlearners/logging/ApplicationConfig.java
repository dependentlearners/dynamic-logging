package com.dependentlearners.logging;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@EnableMBeanExport
@Controller
public class ApplicationConfig {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);
    private final JMXDynamicUserLoggingConfiguration jmxDynamicLoggingConfiguration;

    @Autowired
    public ApplicationConfig(JMXDynamicUserLoggingConfiguration jmxDynamicLoggingConfiguration) {
        this.jmxDynamicLoggingConfiguration = jmxDynamicLoggingConfiguration;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/log/{userId}")
    @ResponseBody
    public void logToUser(@PathVariable(value = "userId") String userId) {
        final String userLogLevel = jmxDynamicLoggingConfiguration.getUserLogLevel(userId);
        if (!"UNDEFINED".equals(userLogLevel)) {
            ThreadContext.put("level", userLogLevel);
        }
        logger.info("User with id {}", userId);
        logger.debug("User with id {}", userId);
        logger.error("User with id {}", userId);
        logger.trace("User with id {}", userId);
        ThreadContext.remove("level");
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }
}
