package spring.studies.polls.service.Impl

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import spring.studies.polls.model.domain.UserPrincipal
import spring.studies.polls.repository.UserRepository
import spring.studies.polls.service.CustomUserDetailsService
import javax.transaction.Transactional

/*
*
* In order to authenticate a User or perform various role-based checks, Spring security needs to load users details
*
* */

@Service
class CustomUserDetailsServiceImpl(
        val userRepository: UserRepository
) :  CustomUserDetailsService {

    @Transactional
    override fun loadUserById(id: Long): UserDetails {
        //The first method loadUserByUsername() is used by Spring security.

        val user = userRepository.findById(id).orElseThrow { UsernameNotFoundException("User not found with id : $id") }
        return UserPrincipal.create(user)
    }


    @Transactional
    override fun loadUserByUsername(userNameOrUserEmail: String): UserDetails {
        val user = userRepository.findByUserNameOrEmail(userNameOrUserEmail, userNameOrUserEmail)

        user?.let {
            return UserPrincipal.create(user)
        }
        throw UsernameNotFoundException("User not found with UserName or User email: $userNameOrUserEmail")
    }

}

