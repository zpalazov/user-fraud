package co.rooam.userfraud.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class VerificationInfo {

    boolean isValid

    User user

    String report

    VerificationInfo() {
    }

    VerificationInfo(boolean isValid, User user) {
        this.isValid = isValid
        this.user = user
        report = report()
    }

    private String report() {
        return String.format("%s User with phone:%s and email:%s is %s",
                isValid ? "SUCCESS" : "WARNING Detected",
                user.phone,
                user.email,
                isValid ? "valid" : "fraud")
    }
}
