class Command {
    var command:CommandType = CommandType.none
    var param:String = ""
    override fun toString(): String {
        return "Command name:"+command.name + " Parammeter:" +param
    }
    fun getCommand():String{
        return command.name
    }
}

enum class CommandType {
    file ,
    none
}

