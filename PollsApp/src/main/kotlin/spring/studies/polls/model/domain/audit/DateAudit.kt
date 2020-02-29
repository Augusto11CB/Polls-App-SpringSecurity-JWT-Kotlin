package spring.studies.polls.model.domain.audit

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.Instant
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

/*
*
* JPA’s AuditingEntityListener to automatically populate createdAt and updatedAt values when we persist an entity.
*
* */

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
@JsonIgnoreProperties(
        value = ["createdAt", "updatedAt"],
        allowGetters = true
)
abstract class DateAudit(

        @CreatedDate
        @Column(nullable = false, updatable = false)
        var createdAt: Instant? = null,

        @LastModifiedDate
        @Column(nullable = false)
        var updatedAt: Instant? = null

) : Serializable
