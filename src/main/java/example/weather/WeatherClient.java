package example.weather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class WeatherClient {

    private static final String LATITUDE = "49.0141";
    private static final String LONGITUDE = "8.4044";
    private final RestTemplate restTemplate;
    private final String weatherServiceUrl;
    private final String weatherServiceApiKey;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public WeatherClient(final RestTemplate restTemplate,
                         @Value("${weather.url}") final String weatherServiceUrl,
                         @Value("${weather.api_secret}") final String weatherServiceApiKey) {
        this.restTemplate = restTemplate;
        this.weatherServiceUrl = weatherServiceUrl;
        this.weatherServiceApiKey = weatherServiceApiKey;
    }

    public Optional<WeatherResponse> fetchWeather() {
        String url = String.format("%s/%s/%s,%s", weatherServiceUrl, weatherServiceApiKey, LATITUDE, LONGITUDE);

        try {
            return Optional.ofNullable(restTemplate.getForObject(url, WeatherResponse.class));
        } catch (RestClientException e) {
            log.error("WeatherClient::fetchWeather failed", e);
            return Optional.empty();
        }
    }
}
