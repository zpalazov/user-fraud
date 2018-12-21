package co.rooam.userfraud.model


import spock.lang.Specification

import static co.rooam.userfraud.TestUserData.*

class VerificationInfoSpec extends Specification {

    VerificationInfo verificationInfo

    def "should return success message when user is valid"() {

        given: "user is valid"
        User batman = new User(BATMAN_PHONE, BATMAN_EMAIL)

        when: "user info is requested"
        verificationInfo = new VerificationInfo(true, batman)

        then: "correct info is returned"
        "SUCCESS User with phone:0096697 and email:batman@gotham.com is valid".equals verificationInfo.report
    }

    def "should return warning message when user is fraud"() {

        given: "user is fraud"
        User joker = new User(JOKER_PHONE, JOKER_EMAIL)

        when: "user info is requested"
        verificationInfo = new VerificationInfo(false, joker)

        then: "correct info is returned"
        "WARNING Detected User with phone:006667 and email:joker@gotham.com is fraud".equals verificationInfo.report
    }

}
