package com.dependentlearners.logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import static org.apache.logging.log4j.core.config.Configurator.setLevel;
import static org.apache.logging.log4j.core.config.Configurator.setRootLevel;

@ManagedResource(objectName = "MyJmx:name=ApplicationLoggingConfiguration")
@Component
public class JMXDynamicApplicationLoggingConfiguration {
    @ManagedOperation(description = "Set package log level")
    public void setLogger(String packageName, String level) {
        setLevel(packageName, Level.toLevel(level));
    }

    @ManagedOperation(description = "Set root log level")
    public void setRootLoggerLevel(String level) {
        setRootLevel(Level.toLevel(level));
    }

    @ManagedOperation(description = "Get root log level")
    public String getLogger() {
        return LogManager.getRootLogger().getLevel().toString();
    }

    @ManagedOperation(description = "Get log level for package")
    public String getLogger(String packageName) {
        return LogManager.getLogger(packageName).getLevel().toString();
    }

}
