package com.example.lib.file

import java.io.File
import java.io.FileWriter

object FileUtils {
    fun write(str: String) {
        val fileRoot = "/Users/gaofei/Documents"
        val file = File(fileRoot, "myClass.txt")
        if (!file.exists()) {
            file.createNewFile()
        }
        val fileWriter = FileWriter(file, true)
        fileWriter.write(str)
        fileWriter.flush()
        fileWriter.close()
    }
}
