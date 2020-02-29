package spring.studies.polls.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import spring.studies.polls.model.domain.User

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findByEmail(email: String): User?

    fun findByUserNameOrEmail(username: String, email: String): User?

    fun findByIdIn(userIds: List<Long>): List<User>

    fun existsByUserName(username: String): Boolean

    fun existsByEmail(email: String): Boolean


}