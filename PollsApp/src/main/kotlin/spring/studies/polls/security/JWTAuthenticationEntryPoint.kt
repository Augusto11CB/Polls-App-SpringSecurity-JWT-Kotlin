package spring.studies.polls.security

import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/*
*
* This class is used to return a 401 unauthorized error to clients that try to access a protected resource without proper authentication
*
* */

@Component
class JWTAuthenticationEntryPoint : AuthenticationEntryPoint {
    private val logger = LoggerFactory.getLogger(JWTAuthenticationEntryPoint::class.java)

    override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?) {
        logger.error("Responding with unauthorized error. Message - {}", authException?.message ?: "error")
        response?.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException?.message ?: "error")

    /*
    *
    * This method is called whenever an exception is thrown due to an unauthenticated user trying to access a resource that requires authentication.
    *
    * */

    }

}