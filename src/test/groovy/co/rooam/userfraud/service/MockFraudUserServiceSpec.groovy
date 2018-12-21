package co.rooam.userfraud.service

import co.rooam.userfraud.TestUserData
import co.rooam.userfraud.model.User
import co.rooam.userfraud.model.VerificationInfo
import spock.lang.Specification

class MockFraudUserServiceSpec extends Specification {

    FraudUserService fraudUserService = new MockFraudUserService()

    def "should return correct user info"() {

        when: "user is verified"
        VerificationInfo actualInfo = fraudUserService.detectFraudUser(user)

        then: "correct verification result is returned"
        expectedInfo.equals actualInfo

        where: "user is hero"
        user                                                             | expectedInfo
        new User(TestUserData.BATMAN_PHONE, TestUserData.BATMAN_EMAIL)   | new VerificationInfo(true, user)
        new User(TestUserData.ROBIN_PHONE, TestUserData.ROBIN_EMAIL)     | new VerificationInfo(true, user)
    }

    def "should throw exception when user is fraud"() {

        when: "user is verified"
        fraudUserService.detectFraudUser(user)

        then: "correct exception is thrown"
        exception = thrown(FraudUserDetectedException)

        where: "user is fraud"
        user                                                             | exception
        new User(TestUserData.JOKER_PHONE, TestUserData.JOKER_EMAIL)     | new FraudUserDetectedException(new VerificationInfo(false, user))
        new User(TestUserData.VALID_PHONE, TestUserData.PENGUIN_EMAIL)   | new FraudUserDetectedException(new VerificationInfo(false, user))
        new User(TestUserData.SCARECROW_PHONE, TestUserData.VALID_EMAIL) | new FraudUserDetectedException(new VerificationInfo(false, user))
    }
}
