import java.io.File
import java.io.InputStream

val ENDLINE = "\r\n"
var path = "/home"
var filemask = "\\*.txt"
var fileList = mutableListOf<MrskFile>()
fun main(args: Array<String>) {
    println("======================= Find files =========================")
    println("=======================  v.2.0.0   =========================")

    // найти файл параметров программы
    val file = File("params.config")
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
            val line = it
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
            val filename =  it.absoluteFile.toString()
            val inputStream: InputStream = File(filename).inputStream()
            val inputString = inputStream.bufferedReader().use{ it.readText()}
            fileList.add(MrskFile(filename,inputString))
            println(it.absoluteFile.toString())
        }
    }
    println("All found files $counter")
    println("===========================================================================================================\n\r\n\r")
    counter = 0
    val str1 = """error: (e) => {
              console.error(e);
              this.#error(e);"""
    val pstr1 = """error: (e) => {
              this.#error(e);"""
    val str2 = """catchError(() => EMPTY)"""
    val pstr2 = """catchError((e) => {
            this.#error(e);
            return EMPTY;
          })"""
    // обработка файлов
    fileList.forEach {

        var content = it.content
        if(content.contains(str1)&&content.contains(str2)){
            counter++
            content = content.replace(str1,pstr1)
            content = content.replace(str2,pstr2)
            it.content = content
            val filename = it.filename
            val file = File(filename)
            if(file.canWrite()){
                println("=========>>>${it.filename}")
                file.writeText(it.content)
                println("Total space of file: ${file.totalSpace}")
            }

        }
    }
    println("Founded files: $counter")
    println("===========================================================================================================\n\r\n\r")


}





