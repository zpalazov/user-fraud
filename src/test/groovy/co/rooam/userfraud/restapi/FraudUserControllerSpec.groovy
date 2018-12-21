package co.rooam.userfraud.restapi


import co.rooam.userfraud.model.User
import co.rooam.userfraud.model.VerificationInfo
import co.rooam.userfraud.service.FraudUserDetectedException
import co.rooam.userfraud.service.FraudUserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import java.nio.charset.StandardCharsets

import static co.rooam.userfraud.TestUserData.BATMAN_EMAIL
import static co.rooam.userfraud.TestUserData.BATMAN_PHONE
import static co.rooam.userfraud.TestUserData.JOKER_EMAIL
import static co.rooam.userfraud.TestUserData.JOKER_PHONE
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class FraudUserControllerSpec extends Specification {

    FraudUserService service = Mock()
    FraudUserController controller = new FraudUserController(service)

    MockMvc mockMvc = standaloneSetup(controller)
            .setControllerAdvice(new ErrorHandler())
            .build()

    def "should return 200 Success when user is valid"() {

        given: "user is valid"
        User batman = new User(BATMAN_PHONE, BATMAN_EMAIL)
        service.detectFraudUser(batman) >> new VerificationInfo(true, batman)

        when: "user validity is checked"
        String requestBody = writeJsonFromObject(batman)
        def response = callDetectFraudEndpoint(requestBody)

        then: "response is success"
        HttpStatus.OK.value == response.status
        VerificationInfo responseBody = deserializeJsonToObject(response.contentAsString, VerificationInfo.class)
        new VerificationInfo(true, batman).equals responseBody
    }

    def "should return 422 Unprocessable Entity when user is fraud"() {

        given: "user is fraud"
        User joker = new User(JOKER_PHONE, JOKER_EMAIL)
        service.detectFraudUser(joker) >> {
            throw new FraudUserDetectedException(new VerificationInfo(false, joker))
        }

        when: "user validity is checked"
        String requestBody = writeJsonFromObject(joker)
        def response = callDetectFraudEndpoint(requestBody)

        then: "response is 422"
        HttpStatus.UNPROCESSABLE_ENTITY.value == response.status
        VerificationInfo responseBody = deserializeJsonToObject(response.contentAsString, VerificationInfo.class)
        new VerificationInfo(false, joker).equals responseBody
    }

    def "should return 400 Bad Request when user data is invalid"() {

        when: "user data is passed detect fraud endpoint"
        String requestBody = writeJsonFromObject(user)
        def response = callDetectFraudEndpoint(requestBody)

        then: "response is 400"
        status == response.status

        where: "different invalid user data combinations"
        user                                   | status
        new User(null, null)                   |  400
        new User("", null)                     |  400
        new User(null, "")                     |  400
        new User("", "")                       |  400
        new User(BATMAN_PHONE, "invalid-mail") |  400
        new User("345", BATMAN_EMAIL)          |  400
    }

    private String writeJsonFromObject(User user) {
        new ObjectMapper().writeValueAsString(user)
    }

    private VerificationInfo deserializeJsonToObject(String json, Class clazz) {
        new ObjectMapper().readValue(json, clazz)
    }

    private MockHttpServletResponse callDetectFraudEndpoint(String requestBody) {
        mockMvc.perform(post("/users/detectFraud")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(requestBody))
                .andDo(print())
                .andReturn()
                .response
    }
}