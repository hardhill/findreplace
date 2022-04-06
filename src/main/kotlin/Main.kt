import java.io.File
import java.io.FileFilter
import java.io.InputStream
import java.nio.file.FileSystem

val ENDLINE = "\r\n"
var path = "/home"
var filemask = "\\*.txt"
var fileList = mutableListOf<String>()
fun main(args: Array<String>) {
    println("======================= Find files =========================")
    println("=======================  v.2.0.0   =========================")

    // найти файл параметров программы
    val file:File = File("params.config")
    if(!file.exists()){
        println("${file} - not found")
        file.createNewFile()
        println("Create new file ${file.absolutePath}")
        file.writeText("path="+path+ENDLINE+"filemask="+filemask)
    }
    if(!file.canRead()){
        println("Can`t read file. Close")
        return
    }else{
        println("Read file ${file.absoluteFile}")
        file.readLines().forEach {
            val line = it.toString()
            if(line.split("=").get(0).equals("path",true)){
                path = line.split("=").get(1)
            }
            if(line.split("=").get(0).equals("filemask",true)){
                filemask = line.split("=").get(1)
            }
        }
    }
    val a = filemask.toRegex()
    var counter = 0

    //output list of files
    File(path).walk().forEach {
       val result = a.matches(it.absoluteFile.toString())
        if(result){
            counter++
            fileList.add(it.absoluteFile.toString())
            println(it.absoluteFile.toString())
        }
    }
    println("All found files $counter")

    // обработка файлов
    fileList.forEach {
        val filename = it.toString()
        val inputStream: InputStream = File(filename).inputStream()
        val inputString = inputStream.bufferedReader().use{ it.readText()}
        println("===========================================================================================================================================================")
        println(inputString)
    }
}





