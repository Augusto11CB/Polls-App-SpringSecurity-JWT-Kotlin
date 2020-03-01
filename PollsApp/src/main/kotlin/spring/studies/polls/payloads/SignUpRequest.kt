package spring.studies.polls.payloads

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

public class SignUpRequest(

    @NotBlank
    @Size(min = 4, max = 40)
    val name: String,

    @NotBlank
    @Size(min = 4, max = 40)
    val username: String,

    @NotBlank
    @Size(min = 4, max = 40)
    @Email
    val email: String,

    @NotBlank
    @Size(min = 4, max = 40)
    val password: String
)


