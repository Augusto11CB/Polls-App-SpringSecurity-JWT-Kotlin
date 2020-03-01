package spring.studies.polls.config.Security

import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/*
*
* This class is used to return a 401 unauthorized error to clients that try to access a protected resource without proper authentication
*
* */

class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
    private val logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint::class.java)

    override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?) {
        logger.error("Responding with unauthorized error. Message - {}", authException?.message ?: "error")
        response?.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException?.message ?: "error")
    }

}