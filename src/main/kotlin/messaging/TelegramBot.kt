import messaging.BotConfig.API_TOKEN
import messaging.BotConfig.CHAT_TOKEN
import java.net.HttpURLConnection
import java.net.URL


class TelegramBot {

    val token = API_TOKEN
    val chat = CHAT_TOKEN

    fun sendMessage(message : String){
        val send="https://api.telegram.org/bot" +
                token +
                "/sendMessage?parse_mode=MarkdownV2&disable_notification=true&chat_id=" +
                chat +
                "&text=" +
                message

        URL(send).readText()
    }

}