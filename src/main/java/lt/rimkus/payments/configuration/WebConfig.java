package lt.rimkus.payments.configuration;

import lt.rimkus.payments.interceptor.CountryLoggingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final CountryLoggingInterceptor countryLoggingInterceptor;

    public WebConfig(CountryLoggingInterceptor countryLoggingInterceptor) {
        this.countryLoggingInterceptor = countryLoggingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(countryLoggingInterceptor)
                .addPathPatterns("/api/**"); // Adjust path patterns as needed
//                .excludePathPatterns("/api/health", "/api/actuator/**"); // Exclude health checks
    }
}
