package spring.studies.polls.payloads

class JWTAuthenticationResponse(
        val accessToken: String,
        val tokenType: String = "Bearer"
) {
}