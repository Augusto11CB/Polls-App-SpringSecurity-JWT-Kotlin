package spring.studies.polls.security

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import spring.studies.polls.service.CustomUserDetailsService
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/*
*
*  The JWTAuthenticationFilter gets the JWT token from the request, validate it, load the user associated with the token, and pass it to Spring Security
*
* */

class JWTAuthenticationFilter : OncePerRequestFilter() {

    private val AUTHORIZATION = "Authorization"
    private val BEARER = "Bearer "

    private val LOGGER = LoggerFactory.getLogger(JWTAuthenticationFilter::class.java)

    @Autowired
    lateinit var tokenProvider: JWTTokenProvider

    @Autowired
    lateinit var customUserDetailsService: CustomUserDetailsService


    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            val jwt = this.getJWTFromRequest(request) // parsing the JWT retrieved from the Authorization header

            jwt?.let {
                when {
                    tokenProvider.validateToken(it) -> {
                        val userId = tokenProvider.getUserIdFromJWT(jwt)

                        val userDetails = customUserDetailsService.loadUserById(userId)

                        val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities);

                        SecurityContextHolder.getContext().authentication = authentication //setting the authentication inside spring security’s context
                    }
                }
            }
        } catch (ex: Exception) {
            LOGGER.error("Could not set user authentication in security context", ex)
        }

        filterChain.doFilter(request, response)

        /*
        *
        * alternative implementation:  Also would be possible encode the user’s username and roles inside JWT claims and create the UserDetails object by parsing those claims from the JWT. That would avoid the database hit.
        *
        * */
    }

    private fun getJWTFromRequest(request: HttpServletRequest): String? {

        val bearerToken = request.getHeader(AUTHORIZATION)

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length)
        }
        return null
    }
}