package spring.studies.polls.model.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

public class UserPrincipal(

        val id: Long = 0,

        val name: String? = null,

        val userName: String? = null,

        @JsonIgnore
        val email: String? = null,

        @JsonIgnore
        val password2: String? = null,

        val authority: MutableCollection<GrantedAuthority> = mutableListOf()


) : UserDetails {

    companion object {

        fun create(user: User): UserPrincipal {
            var authorities = user.roles.stream()
                    .map { x -> SimpleGrantedAuthority(x.name!!.name) }
                    .collect(Collectors.toList())

            return UserPrincipal(user.id, user.name, user.userName, user.email, user.password, authorities.toMutableList())
        }
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authority
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        return userName!!
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun getPassword(): String {
        return password2!!
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}