package co.rooam.userfraud.service

import co.rooam.userfraud.model.User
import co.rooam.userfraud.model.VerificationInfo
import org.springframework.stereotype.Service

@Service
class MockFraudUserService implements FraudUserService {

    private final Set fraudNumbers = ["666", '000']
    private final Set fraudEmailTokens = ["joker", "penguin"]

    @Override
    VerificationInfo detectFraudUser(User user) {
        final boolean isValidPhone = fraudNumbers.findAll({ user.phone.contains(it as String) }).isEmpty()
        final boolean isValidEmail = fraudEmailTokens.findAll({ user.email.contains(it as String) }).isEmpty()

        final boolean isValidUser = isValidPhone && isValidEmail
        final VerificationInfo verificationInfo = new VerificationInfo(isValidUser, user)
        if (!isValidUser)
            throw new FraudUserDetectedException(verificationInfo)

        return new VerificationInfo(isValidUser, user)
    }
}
