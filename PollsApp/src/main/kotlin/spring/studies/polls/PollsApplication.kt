package spring.studies.polls

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.TimeZone
import javax.annotation.PostConstruct


@SpringBootApplication
class PollsApplication

fun main(args: Array<String>) {
	runApplication<PollsApplication>(*args)
}

@PostConstruct
fun init() {
	TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
}
