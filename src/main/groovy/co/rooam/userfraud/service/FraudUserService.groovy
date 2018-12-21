package co.rooam.userfraud.service

import co.rooam.userfraud.model.User
import co.rooam.userfraud.model.VerificationInfo

interface FraudUserService {

    VerificationInfo detectFraudUser(User user)
}
