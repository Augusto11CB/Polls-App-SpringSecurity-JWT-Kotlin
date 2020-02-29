package spring.studies.polls.model.domain

import org.hibernate.annotations.NaturalId
import spring.studies.polls.model.domain.audit.DateAudit
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = ["email", "userName"])])
class User(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @NotBlank
        @Size(max = 40)
        val name: String,

        @NotBlank
        @Size(max = 20)
        val userName: String,

        @NaturalId
        @NotBlank
        @Size(max = 40)
        @Email
        val email: String,

        @NotBlank
        @Size(max = 100)
        val password: String,

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "user_roles",
                joinColumns = [JoinColumn(name = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "role_id")])
        var roles: Set<Role>? = mutableSetOf<Role>()
) : DateAudit()
