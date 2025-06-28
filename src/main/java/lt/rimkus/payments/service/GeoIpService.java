package lt.rimkus.payments.service;

public interface GeoIpService {
    String getCountryByIp(String ipAddress);
    String getCountryCodeByIp(String ipAddress);
}
