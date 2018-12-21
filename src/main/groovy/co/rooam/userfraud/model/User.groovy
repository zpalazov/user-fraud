package co.rooam.userfraud.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern

@EqualsAndHashCode
@ToString
class User {

    @NotEmpty
    @Pattern(regexp="\\d{6,10}", message = "A valid phone number must contain between 6 and 10 digits")
    String phone

    @NotEmpty
    @Email
    String email

    User() {}

    User(String phone, String email) {
        this.phone = phone
        this.email = email
    }
}
