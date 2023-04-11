package bo.edu.ucb.currencykt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableDiscoveryClient
class CurrencyKtApplication

fun main(args: Array<String>) {
	runApplication<CurrencyKtApplication>(*args)
}
