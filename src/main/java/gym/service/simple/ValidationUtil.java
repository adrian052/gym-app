package gym.service.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

public class ValidationUtil {
    private static final Logger logger = LoggerFactory.getLogger(ValidationUtil.class);
    private ValidationUtil(){}

    public static void validateNotNull(Map<String, Object> params) {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() == null) {
                String paramName = entry.getKey();
                String errorMessage = String.format("%s cannot be null", paramName);
                logger.error(errorMessage);
                throw new IllegalArgumentException(errorMessage);
            }
        }
    }
}
