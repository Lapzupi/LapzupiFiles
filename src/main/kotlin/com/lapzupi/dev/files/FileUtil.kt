package com.lapzupi.dev.files

import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.FileFilter
import java.io.IOException
import java.security.CodeSource
import java.util.function.Predicate
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream


class FileUtil private constructor() {
    init {
        throw UnsupportedOperationException()
    }
    
    companion object {
        /**
         * plugin.getClass().getProtectionDomain().getCodeSource()
         */
        @Throws(IOException::class)
        fun getFileNamesInJar(source: CodeSource, fileCondition: Predicate<ZipEntry?>): List<String> {
            val fileNames: MutableList<String> = ArrayList()
            val jar = source.location
            
            ZipInputStream(jar.openStream()).use { zip ->
                while (true) {
                    val entry = zip.nextEntry ?: break
                    if (fileCondition.test(entry)) {
                        fileNames.add(entry.name)
                    }
                }
            }
            
            return fileNames
        }
        
        fun getFileNamesFromFolder(folder: File, pattern: FileFilter): List<String> {
            return folder.listFiles(pattern)?.map{ file -> file.name} ?: emptyList()
        }
        
        fun saveFileFromJar(plugin: JavaPlugin, resourcePath: String, fileName: String, folder: File?) {
            val file = File(folder, fileName)
            if (!file.exists()) {
                plugin.saveResource(resourcePath + fileName, false)
            }
        }
    }
}