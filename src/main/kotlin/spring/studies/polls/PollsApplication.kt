package spring.studies.polls

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PollsApplication

fun main(args: Array<String>) {
	runApplication<PollsApplication>(*args)
}
