package integration

import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class IntegrationTestConfig {

    @Bean
    TestRestTemplate restTemplate() {
        return new TestRestTemplate()
    }
}
