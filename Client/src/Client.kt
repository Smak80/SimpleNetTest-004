import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class Client(
    private val host: String = "127.0.0.1",
    private val port: Int,
) {
    companion object {
        var counter = 0
    }

    init {
        counter++
    }

    val id = counter

    fun start(){
        var s: Socket? = null
        var pw: PrintWriter? = null
        var br: BufferedReader? = null
        println("Я клиент $id")
        try {
            s = Socket(host, port)
            pw = PrintWriter(s.getOutputStream()).apply {
                Thread.sleep(20000)
                println("Привет от клиента $id!")
                flush()
            }
            br = BufferedReader(InputStreamReader(s.getInputStream())).also {
                println(it.readLine())
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