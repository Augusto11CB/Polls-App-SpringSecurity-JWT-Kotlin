package spring.studies.polls.security

import io.jsonwebtoken.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import spring.studies.polls.model.domain.UserPrincipal
import java.util.*

/*
*
* This class will be used for generating a JWT after a user logs in successfully,
* and validating the JWT sent in the Authorization header of the requests -
*
* */

@Component
class JWTTokenProvider {

    private val logger = LoggerFactory.getLogger(JWTTokenProvider::class.java)

    @Value("\${jwt-secret}")
    lateinit var jwtSecret: String

    @Value("\${jwt-expiration-in-seconds}")
    var jwtExpirationInSeconds: Long = 360

    fun generateToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as UserPrincipal

//        val now = LocalDateTime.now()
//        val expiryDate = now.plusSeconds(jwtExpirationInSeconds.toLong())

        val now = Date()
        val expiryDate = Date(now.time + jwtExpirationInSeconds)

        return Jwts.builder()
                .setSubject(userPrincipal.id.toString())
                .setIssuedAt(Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact()

    }

    fun getUserIdFromJWT(authToken: String): Long {
        val claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(authToken)
                .body

        return claims.subject.toLong()
    }

    fun validateToken(authToken: String): Boolean {
        
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(authToken)
            return true
        } catch (ex: SignatureException) {
            logger.error("Invalid JWT Signature")
        } catch (ex: MalformedJwtException) {
            logger.error("Invalid JWT token")
        } catch (ex: ExpiredJwtException) {
            logger.error("Expired JWT token")
        } catch (ex: UnsupportedJwtException) {
            logger.error("Unsupported JWT token")
        } catch (ex: IllegalArgumentException) {
            logger.error("JWT claims string is empty.")
        }
        return false
    }

}