package co.rooam.userfraud.restapi

import co.rooam.userfraud.model.User
import co.rooam.userfraud.model.VerificationInfo
import co.rooam.userfraud.service.FraudUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class FraudUserController implements FraudUserApi {

    private final FraudUserService service

    FraudUserController(FraudUserService service) {
        this.service = service
    }

    @Override
    ResponseEntity<VerificationInfo> detectFraudUser(User user) {
        final VerificationInfo verificationInfo = service.detectFraudUser(user)

        return new ResponseEntity<VerificationInfo>(verificationInfo, HttpStatus.OK)
    }
}
