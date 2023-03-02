import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import kotlin.Exception
import kotlin.concurrent.thread

class Server {
    companion object{
        const val PORT = 5004
    }

    fun start(){
        val ss = ServerSocket(PORT)
        val clients = mutableListOf<Socket>()
        try {
            while (true) {
                val s = ss.accept().also { clients.add(it) }
                thread {
                    var br: BufferedReader? = null
                    try {
                        println("Подключен очередной клиент")
                        val iStream = s.getInputStream()
                        br = BufferedReader(InputStreamReader(iStream))
                        val inputString = br.readLine()

                        clients.forEach {
                            if (it == s) return@forEach
                            val oStream = it.getOutputStream()
                            var pw: PrintWriter? = null
                            try {
                                pw = PrintWriter(oStream)
                                pw.println(inputString)
                                pw.flush()
                            }catch (e: Exception){
                                println("Что-то пошло не так... ")
                                println(e)
                            } finally {
                                pw?.close()
                            }
                        }

                    } catch (e: Exception){
                        println("Что-то пошло не так... ")
                        println(e)
                    } finally {
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