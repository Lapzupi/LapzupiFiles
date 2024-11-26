package com.lapzupi.dev.files

import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.FileFilter
import java.io.IOException
import java.security.CodeSource
import java.util.function.Predicate
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream


object FileUtil {


    /**
     * Extracts file names from a JAR file based on a specified condition.
     *
     * This method scans the contents of the JAR file associated with the given [CodeSource],
     * returning a list of file names that match the provided condition.
     *
     * Example usage:
     * ```
     * val fileNames = FileUtil.getFileNamesInJar(
     *     plugin.javaClass.protectionDomain.codeSource
     * ) { it?.name?.endsWith(".yml") == true }
     * ```
     *
     * @param source The [CodeSource] representing the JAR file. Must not be null.
     * @param fileCondition A [Predicate] that determines whether a [ZipEntry] should be included in the result.
     * @return A list of file names that satisfy the given condition. Returns an empty list if no matches are found.
     * @throws IllegalArgumentException If the `source` location is null.
     * @throws IOException If an I/O error occurs while reading the JAR file.
     */
    @JvmStatic
    @Throws(IOException::class)
    fun getFileNamesInJar(source: CodeSource, fileCondition: Predicate<ZipEntry?>): List<String> {
        val jar = source.location ?: throw IllegalArgumentException("CodeSource location is null.")
        ZipInputStream(jar.openStream()).use { zip ->
            return generateSequence { zip.nextEntry }
                .filter { fileCondition.test(it) }
                .map { it.name }
                .toList()
        }
    }


    /**
     * Retrieves file names from a specified folder that match a given filter.
     *
     * This method scans the provided folder and applies the specified [FileFilter] to
     * determine which files should be included in the result.
     *
     * Example usage:
     * ```
     * val logFiles = FileUtil.getFileNamesFromFolder(File("logs")) { it.name.endsWith(".log") }
     * ```
     *
     * @param folder The [File] object representing the folder to search. Must not be null.
     * @param pattern The [FileFilter] used to filter files in the folder.
     * @return A list of file names that satisfy the given file filter. Returns an empty list if no files match.
     * @throws NullPointerException If `folder` or `pattern` is null.
     */
    @JvmStatic
    fun getFileNamesFromFolder(folder: File, pattern: FileFilter): List<String> {
        return folder.listFiles(pattern)?.map { file -> file.name } ?: emptyList()
    }

    /**
     * Copies a resource file from the plugin's JAR to the specified folder.
     *
     * This method checks if the specified file already exists in the destination folder.
     * If the file does not exist, it copies the resource file from the plugin's JAR.
     * If the `folder` parameter is `null`, the file will be saved in the plugin's default data folder.
     *
     * Example usage:
     * ```
     * FileUtil.saveFileFromJar(plugin, "resources/", "config.yml", File("configs"))
     * ```
     *
     * @param plugin The [JavaPlugin] instance representing the plugin. Must not be null.
     * @param resourcePath The path to the resource within the plugin's JAR file. Must not be null.
     * @param fileName The name of the file to be saved. Must not be null.
     * @param folder The [File] object representing the destination folder. If null, the plugin's data folder will be used.
     * @throws NullPointerException If `plugin`, `resourcePath`, or `fileName` is null.
     */
    @JvmStatic
    fun saveFileFromJar(plugin: JavaPlugin, resourcePath: String, fileName: String, folder: File?) {
        val destination = File(folder ?: plugin.dataFolder, fileName)
        if (!destination.exists()) {
            plugin.saveResource("$resourcePath$fileName", false)
        }
    }


}