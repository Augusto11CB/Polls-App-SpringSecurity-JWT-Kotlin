package spring.studies.polls.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import spring.studies.polls.custom.exception.AppException
import spring.studies.polls.enums.RoleName
import spring.studies.polls.model.domain.User
import spring.studies.polls.payloads.ApiResponse
import spring.studies.polls.payloads.JWTAuthenticationResponse
import spring.studies.polls.payloads.LoginRequest
import spring.studies.polls.payloads.SignUpRequest
import spring.studies.polls.repository.RoleRepository
import spring.studies.polls.repository.UserRepository
import spring.studies.polls.security.JWTTokenProvider
import java.net.URI
import java.util.*
import javax.validation.Valid


@RestController
@RequestMapping("/api/auth")
class AuthController(

        val authenticationManager: AuthenticationManager,

        val userRepository: UserRepository,

        val roleRepository: RoleRepository,

        val passwordEncoder: PasswordEncoder,

        val tokenProvider: JWTTokenProvider
) {

    @PostMapping("/signin")
    fun authenticateUser(@RequestBody @Valid loginRequest: /*@Valid*/ LoginRequest): ResponseEntity<*> {

        val authentication: Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        loginRequest.userNameOrEmail,
                        loginRequest.password
                )
        )

        SecurityContextHolder.getContext().authentication = authentication

        val jwt: String = tokenProvider.generateToken(authentication)

        return ResponseEntity.ok(JWTAuthenticationResponse(jwt))
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody signUpRequest: @Valid SignUpRequest): ResponseEntity<*> {

        if (userRepository.existsByUserName(signUpRequest.username)) {
            return ResponseEntity (ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST)
        }

        if (userRepository.existsByEmail(signUpRequest.email)) {
            return ResponseEntity(ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST)
        }

        // Creating user's account
        val user = User(name = signUpRequest.name, userName = signUpRequest.username,
                email = signUpRequest.email, password = passwordEncoder.encode(signUpRequest.password))

        val userRole = roleRepository.findByName(RoleName.ROLE_USER)

        userRole?.let {

            user.roles = Collections.singleton(it)

            val userResult = userRepository.save(user)

            val location: URI = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/api/users/{username}")
                    .buildAndExpand(userResult.userName).toUri()

            return ResponseEntity.created(location).body(ApiResponse(true, "User registered successfully"))
        }


        throw AppException("User Role not set.")

    }
}