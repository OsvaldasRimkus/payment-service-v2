package lt.rimkus.payments.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetAddress;

@Service
public class GeoIpServiceImpl implements GeoIpService {

    private DatabaseReader dbReader;

    @PostConstruct
    public void init() throws IOException {
        // Downloaded GeoLite2-Country.mmdb from MaxMind is placed in src/main/resources
        ClassPathResource resource = new ClassPathResource("GeoLite2-Country.mmdb");
        dbReader = new DatabaseReader.Builder(resource.getInputStream()).build();
    }

    public String getCountryByIp(String ipAddress) {
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            CountryResponse response = dbReader.country(inetAddress);
            return response.getCountry().getName();
        } catch (IOException | GeoIp2Exception e) {
            return "Unknown";
        }
    }

    public String getCountryCodeByIp(String ipAddress) {
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            CountryResponse response = dbReader.country(inetAddress);
            return response.getCountry().getIsoCode();
        } catch (IOException | GeoIp2Exception e) {
            return "XX";
        }
    }

}
