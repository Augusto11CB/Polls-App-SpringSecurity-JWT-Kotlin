package spring.studies.polls.model.domain

import org.hibernate.annotations.NaturalId
import spring.studies.polls.enums.RoleName
import javax.persistence.*

@Entity
@Table(name = "roles")
data class Role(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = 0,

        @Enumerated(EnumType.STRING)
        @NaturalId
        @Column(length = 60)
        val name: RoleName? = null
)
