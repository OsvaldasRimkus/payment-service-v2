package lt.rimkus.payments.interceptor;

import lt.rimkus.payments.service.GeoIpService;
import lt.rimkus.payments.utility.IpAddressUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CountryLoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(CountryLoggingInterceptor.class);

    private final GeoIpService geoIpService;

    public CountryLoggingInterceptor(GeoIpService geoIpService) {
        this.geoIpService = geoIpService;
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        String ipAddress = IpAddressUtils.getClientIpAddress(request);
        String country = geoIpService.getCountryByIp(ipAddress);
        String countryCode = geoIpService.getCountryCodeByIp(ipAddress);

        // Log the country information
        logger.info("Request from IP: {} | Country: {} ({}) | Endpoint: {} {}",
                ipAddress, country, countryCode, request.getMethod(), request.getRequestURI());

        // Optional: Add country info to request attributes for use in controllers
//        request.setAttribute("clientCountry", country);
//        request.setAttribute("clientCountryCode", countryCode);
//        request.setAttribute("clientIpAddress", ipAddress);

        return true;
    }
}