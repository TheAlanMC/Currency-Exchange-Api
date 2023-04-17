package bo.edu.ucb.currencykt

import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableDiscoveryClient
class CurrencyKtApplication{
	@Bean
	fun runner(cf: ConnectionFactory): ApplicationRunner {
		return ApplicationRunner {
			var open = false
			while (!open) {
				try {
					cf.createConnection().close()
					open = true
				} catch (e: Exception) {
					Thread.sleep(5000)
				}
			}
		}
	}
}

fun main(args: Array<String>) {
	runApplication<CurrencyKtApplication>(*args)
}
