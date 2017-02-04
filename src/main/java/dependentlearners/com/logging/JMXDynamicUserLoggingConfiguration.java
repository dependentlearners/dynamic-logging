package dependentlearners.com.logging;

import org.apache.logging.log4j.Level;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.collect.Lists.newArrayList;

@ManagedResource(objectName = "MyJmx:name=UserLoggingConfiguration")
@Component
public class JMXDynamicUserLoggingConfiguration {
    private final Map<String, Level> userLoggingLevel = new ConcurrentHashMap<>();


    @ManagedOperation
    public void addUserForLoggedIn(String userName, String logLevel) {
        userLoggingLevel.put(userName, Level.valueOf(logLevel));
    }

    @ManagedOperation
    public String getUserLogLevel(String userName) {
        final Level userLogLevel = userLoggingLevel.get(userName);
        return userLogLevel == null ? "UNDEFINED" : userLogLevel.name();
    }

    @ManagedOperation
    public List<String> getAvailableLogLevels() {
        return newArrayList(Level.DEBUG.name(), Level.ERROR.name(), Level.INFO.name(), Level.TRACE.name(), Level.WARN.name(), Level.OFF.name());
    }
}
