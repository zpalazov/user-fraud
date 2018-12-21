package co.rooam.userfraud.restapi

import co.rooam.userfraud.model.User
import co.rooam.userfraud.model.VerificationInfo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

import javax.validation.Valid

@RequestMapping("/users")
interface FraudUserApi {

    @PostMapping("/detectFraud")
    ResponseEntity<VerificationInfo> detectFraudUser(@RequestBody @Valid User user)
}