package spring.studies.polls.payloads

import javax.validation.constraints.NotBlank

public class LoginRequest(

        @NotBlank
        val userNameOrEmail: String,

        @NotBlank
        val password: String
)