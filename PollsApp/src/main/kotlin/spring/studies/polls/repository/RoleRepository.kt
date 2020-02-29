package spring.studies.polls.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import spring.studies.polls.enums.RoleName
import spring.studies.polls.model.domain.Role

@Repository
interface RoleRepository : JpaRepository<Role, Long> {

    fun findByName(roleName: RoleName): Role?
}