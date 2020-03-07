package spring.studies.polls.model.domain

import org.hibernate.annotations.NaturalId
import spring.studies.polls.enums.RoleName
import javax.persistence.*

@Entity
@Table(name = "roles", schema = "auth")
data class Role(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "role_id")
        val id: Long? = 0,

        @Enumerated(EnumType.STRING)
        @NaturalId
        @Column(length = 60)
        val name: RoleName? = null
)
