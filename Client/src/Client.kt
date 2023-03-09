import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class Client(
    private val host: String = "127.0.0.1",
    private val port: Int,
) {
    fun start(){
        var s: Socket? = null
        var pw: PrintWriter? = null
        var br: BufferedReader? = null
        try {
            s = Socket(host, port)
            println("Я клиент ${s.localPort}")
            pw = PrintWriter(s.getOutputStream()).apply {
                println("Привет от клиента ${s.localPort}!")
                flush()
            }
            br = BufferedReader(InputStreamReader(s.getInputStream())).also {
                while (true) {
                    println(it.readLine())
                }
            }
        } catch (e: Exception){
            println("Что-то пошло не так")
            println(e)
        } finally {
            pw?.close()
            br?.close()
            s?.close()
        }
    }
}