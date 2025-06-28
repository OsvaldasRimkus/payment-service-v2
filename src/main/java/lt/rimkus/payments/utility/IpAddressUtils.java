package lt.rimkus.payments.utility;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

public class IpAddressUtils {

    public static String getClientIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (StringUtils.hasText(xForwardedForHeader) && !"unknown".equalsIgnoreCase(xForwardedForHeader)) {
            return xForwardedForHeader.split(",")[0].trim();
        }

        String xRealIpHeader = request.getHeader("X-Real-IP");
        if (StringUtils.hasText(xRealIpHeader) && !"unknown".equalsIgnoreCase(xRealIpHeader)) {
            return xRealIpHeader;
        }

        String xForwardedHeader = request.getHeader("X-Forwarded");
        if (StringUtils.hasText(xForwardedHeader) && !"unknown".equalsIgnoreCase(xForwardedHeader)) {
            return xForwardedHeader;
        }

        String xClusterClientIpHeader = request.getHeader("X-Cluster-Client-IP");
        if (StringUtils.hasText(xClusterClientIpHeader) && !"unknown".equalsIgnoreCase(xClusterClientIpHeader)) {
            return xClusterClientIpHeader;
        }

        return request.getRemoteAddr();
    }
}