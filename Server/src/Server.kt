import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import kotlin.Exception
import kotlin.concurrent.thread

class Server {
    companion object{
        const val PORT = 5004
    }

    fun start(){
        val ss = ServerSocket(PORT)
        try {
            while (true) {
                val s = ss.accept()
                thread {
                    var br: BufferedReader? = null
                    var pw: PrintWriter? = null
                    try {
                        println("Подключен очередной клиент")
                        val iStream = s.getInputStream()
                        br = BufferedReader(InputStreamReader(iStream))
                        val inputString = br.readLine()
                        println(inputString)
                        Thread.sleep(20000)
                        val oStream = s.getOutputStream()
                        pw = PrintWriter(oStream)
                        pw.println("Привет от сервера в ответ!")
                        pw.flush()
                    } catch (e: Exception){
                        println("Что-то пошло не так... ")
                        println(e)
                    } finally {
                        pw?.close()
                        br?.close()
                        s?.close()
                    }
                }
            }
        } catch (e: Exception){
            println("Что-то пошло не так... ")
            println(e)
        } finally {
            ss.close()
        }
    }
}