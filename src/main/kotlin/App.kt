import org.springframework.context.support.ClassPathXmlApplicationContext
import stock.processor.Event
import stock.processor.YahooStockProcessor
import java.time.Clock
import java.time.Duration
import java.util.Timer
import kotlin.concurrent.schedule

class App(
    private val timer: Timer,
    private val clock: Clock,
    private val processor: YahooStockProcessor,
    private val pollingFrequency: Duration
) {
    fun run() {
        println("${this.javaClass}: Running")
        timer.schedule(0, pollingFrequency.toMillis()) {
            runCatching {
                processor.process(Event(clock.instant()))
            }.onFailure {
                println(it.message)
                println(it.stackTraceToString())
            }
        }
    }
}

fun main() {
    val context = ClassPathXmlApplicationContext("classpath:spring-configuration/application.xml")
    val app = context.getBean("app", App::class.java)
    app.run()
}