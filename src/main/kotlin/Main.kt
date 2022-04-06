import java.io.File

var commands = mutableListOf<Command>()

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
    ClearArgs(args)
    commands.Compile()

}

private fun MutableList<Command>.Compile() {
    forEach { it ->
        if(it.command == CommandType.file){
            val path = it.param
            File(path).walk().forEach {
                println(it.absoluteFile)
            }
        }
    }
}




fun ClearArgs(args: Array<String>) {
    //выбираем только валидные параметры
    var clearArr = args.filter { item->valid(item) }
    for (item in clearArr){
       var comm = item.split("=").get(0).get(1)
       var param = item.split("=").get(1)
       val command = Command()
       command.command = if(comm == 'p') CommandType.file else CommandType.none
       command.param = param
       commands.add(command)
    }
}

fun valid(item: String):Boolean {
    val r1 = Regex("""^-[fp]=.+$""",RegexOption.IGNORE_CASE)
    return r1.matches(item)
}


