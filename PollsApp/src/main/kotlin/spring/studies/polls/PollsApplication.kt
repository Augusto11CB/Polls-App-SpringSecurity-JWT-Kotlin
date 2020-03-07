package spring.studies.polls

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import java.util.*
import javax.annotation.PostConstruct


@SpringBootApplication
@EntityScan(basePackages = ["spring.studies.polls.model.domain"])
class PollsApplication

fun main(args: Array<String>) {
    runApplication<PollsApplication>(*args)
}

@PostConstruct
fun init() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
}
