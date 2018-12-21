package integration

import co.rooam.userfraud.UserFraudApplication
import co.rooam.userfraud.model.User
import co.rooam.userfraud.model.VerificationInfo
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner

import static co.rooam.userfraud.TestUserData.*
import static org.assertj.core.api.Assertions.assertThat

@Ignore
@SpringBootTest(classes = [UserFraudApplication.class, IntegrationTestConfig.class])
@RunWith(SpringRunner.class)
class DetectFraudUserIntegrationTest {

    public static final String DETECT_FRAUD_USER = "http://localhost:8080/users/detectFraud"

    @Autowired
    private TestRestTemplate restTemplate

    @Test
    void testUserIsValid() {
        User batman = new User(BATMAN_PHONE, BATMAN_EMAIL)
        ResponseEntity<VerificationInfo> response = restTemplate
                .postForEntity DETECT_FRAUD_USER, batman, VerificationInfo

        assertThat(response.statusCode).isEqualTo HttpStatus.OK
        assertThat(response.body).isEqualTo new VerificationInfo(true, batman)
    }

    @Test
    void testDetectFraudUser() {
        User joker = new User(JOKER_PHONE, JOKER_EMAIL)
        ResponseEntity<VerificationInfo> response = restTemplate
                .postForEntity DETECT_FRAUD_USER, joker, VerificationInfo

        assertThat(response.statusCode).isEqualTo HttpStatus.UNPROCESSABLE_ENTITY
        assertThat(response.body).isEqualTo new VerificationInfo(false, joker)
    }

    @Test
    void testInvalidUserData() {
        User invalidUser = new User()
        ResponseEntity<String> response = restTemplate
                .postForEntity DETECT_FRAUD_USER, invalidUser, String

        assertThat response.statusCode isEqualTo HttpStatus.BAD_REQUEST
    }
}
