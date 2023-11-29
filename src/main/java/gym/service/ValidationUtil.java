package gym.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

public class ValidationUtil {
    private static final Logger logger = LoggerFactory.getLogger(ValidationUtil.class);
    private ValidationUtil(){}

    public static void validateNotNull(Object... keyValuePairs) {
        if (keyValuePairs.length % 2 != 0) {
            throw new IllegalArgumentException("Number of arguments must be even");
        }

        for (int i = 0; i < keyValuePairs.length; i += 2) {
            String key = (String) keyValuePairs[i];
            Object value = keyValuePairs[i + 1];

            if (value == null) {
                String errorMessage = String.format("%s cannot be null", key);
                logger.error(errorMessage);
                throw new IllegalArgumentException(errorMessage);
            }
        }
    }
}
