package day7

const val fileName = "input7.txt"

var pwd = ""
var currentFolder: Folder = Folder("", "", mutableListOf(), mutableListOf())

var folders = mutableListOf<Folder>()
fun main() {
    val readLines = java.io.File(fileName).readLines()

    folders.add(currentFolder)

    readLines.forEach { cmd ->
        if (cmd.startsWith('$')) {
            parseCmd(cmd)
        } else if (cmd.startsWith("dir ")) {
            parseFolder(cmd)
        } else {
            parseFile(cmd)
        }
    }

    val res = folders.map { it.calcSize() }.filter { it < 100000 }.sum()

    println(res)

    val root = folders.find { f->f.path.isEmpty() }

    val space = 30000000 - (70000000 - (root?.calcSize()?:0))

    val res2 = folders.filter { f -> f.calcSize() >= space }.sortedBy { f->f.calcSize() }.first()

    println(res2.calcSize())
}

fun parseFile(cmd: String) {
    val size = cmd.split(" ")[0].toInt()
    val name = cmd.split(" ")[1]

    currentFolder.files.add(File(name, pwd, size))
}

fun parseFolder(cmd: String) {
    val name = cmd.substring(4)
    val element = Folder(name, pwd.split("/").plus(name).joinToString("/"), mutableListOf(), mutableListOf())
    folders.add(element)
    currentFolder.folders.add(element)
    println("create folder ${element.path}")
}

fun parseCmd(cmd: String) {
    if (cmd == "$ cd ..") {
        val l = pwd.split("/").toMutableList()
        l.removeLast()
            pwd = l.joinToString("/")
        println("cd .. into $pwd")
    } else if (cmd.startsWith("$ cd /"))
    {
        pwd = ""
        println("cd into $pwd")
    }
    else if (cmd.startsWith("$ cd "))
    {
        val l = pwd.split("/").toMutableList()
        l.add(cmd.substring(5))
        pwd = l.joinToString("/")
        println("cd into $pwd")
    }
    currentFolder = folders.find { it.path == pwd}!!

}

data class Folder (val name: String, val path:String, val folders: MutableList<Folder>, val files: MutableList<File>) {
    fun calcSize():Int {
        return files.sumOf { it.size } + folders.sumOf { it.calcSize() }
    }
}

data class File (val name: String, val path:String, val size: Int)
