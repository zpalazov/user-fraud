package co.rooam.userfraud.service

import co.rooam.userfraud.model.VerificationInfo

class FraudUserDetectedException extends RuntimeException {

    final VerificationInfo verificationInfo

    FraudUserDetectedException(VerificationInfo verificationInfo) {
        this.verificationInfo = verificationInfo
    }

}
