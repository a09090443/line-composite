package com.zipe.util.file

import java.io.*
import java.nio.charset.Charset
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipException
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

object ZipUtils {
    private const val BUFF_SIZE = 1024 * 1024 // 1M Byte

    /**
     * 压缩目录
     *
     * @param srcPath         被压缩的目录路径
     * @param destZipFilePath 目标zip文件路径
     * @param excludeTopDir   是否排除掉顶层目录
     * @throws IOException
     */
    fun zip(
        srcPath: String,
        destZipFilePath: String,
        excludeTopDir: Boolean
    ) {
        val fos = FileOutputStream(destZipFilePath)
        val zipOut = ZipOutputStream(fos)
        val srcDirFile = File(srcPath)
        if (srcDirFile.isDirectory) {
            if (excludeTopDir) {
                zipDirExcludeTopDir(zipOut, srcDirFile)
            } else {
                zipFile(srcDirFile, srcDirFile.name, zipOut)
            }
        } else {
            zipFile(srcDirFile, srcDirFile.name, zipOut)
        }
        zipOut.close()
        fos.close()
    }

    /**
     * 压缩目录时，去掉最顶层的目录。
     */
    private fun zipDirExcludeTopDir(zipOut: ZipOutputStream, srcDir: File) {
        val subFiles = srcDir.listFiles()
        for (i in subFiles.indices) {
            val subFile = subFiles[i]
            zipFile(subFile, subFile.name, zipOut)
        }
    }

    @Throws(IOException::class)
    private fun zipFile(fileToZip: File, fileName: String, zipOut: ZipOutputStream) {
        if (fileToZip.isDirectory) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(ZipEntry(fileName))
                zipOut.closeEntry()
            } else {
                zipOut.putNextEntry(ZipEntry("$fileName/"))
                zipOut.closeEntry()
            }
            val children = fileToZip.listFiles()
            for (childFile in children) {
                zipFile(childFile, fileName + "/" + childFile.name, zipOut)
            }
            return
        }
        val fis = FileInputStream(fileToZip)
        val zipEntry = ZipEntry(fileName)
        zipOut.putNextEntry(zipEntry)
        val bytes = ByteArray(1024)
        var length: Int
        while (fis.read(bytes).also { length = it } >= 0) {
            zipOut.write(bytes, 0, length)
        }
        fis.close()
    }

    /**
     * 解压缩一个文件
     *
     * @param zipFile    压缩文件
     * @param folderPath 解压缩的目标目录
     * @throws IOException 当解压缩过程出错时抛出
     */
    @Throws(ZipException::class, IOException::class)
    fun upZipFile(zipFile: File?, folderPath: String) {
        val desDir = File(folderPath)
        if (!desDir.exists()) {
            desDir.mkdirs()
        }
        val zf = ZipFile(zipFile)
        val entries: Enumeration<*> = zf.entries()
        while (entries.hasMoreElements()) {
            val entry = entries.nextElement() as ZipEntry
            if (entry.isDirectory) {
                continue
            }
            val inputStream = zf.getInputStream(entry)
            var str = folderPath + File.separator + entry.name
            str = String(str.toByteArray(), Charset.forName("utf-8"))
            val desFile = File(str)
            if (!desFile.exists()) {
                val fileParentDir = desFile.parentFile
                if (!fileParentDir.exists()) {
                    fileParentDir.mkdirs()
                }
                desFile.createNewFile()
            }
            val out: OutputStream = FileOutputStream(desFile)
            val buffer = ByteArray(BUFF_SIZE)
            var realLength: Int
            while (inputStream.read(buffer).also { realLength = it } > 0) {
                out.write(buffer, 0, realLength)
            }
            inputStream.close()
            out.close()
        }
    }

    /**
     * 解压文件名包含传入文字的文件
     *
     * @param zipFile      压缩文件
     * @param folderPath   目标文件夹
     * @param nameContains 传入的文件匹配名
     * @throws ZipException 压缩格式有误时抛出
     * @throws IOException  IO错误时抛出
     */
    @Throws(ZipException::class, IOException::class)
    fun upZipSelectedFile(
        zipFile: File,
        folderPath: String,
        nameContains: String
    ): ArrayList<File> {
        val fileList = ArrayList<File>()
        val desDir = File(folderPath)
        if (!desDir.exists()) {
            desDir.mkdir()
        }
        val zf = ZipFile(zipFile)
        val entries: Enumeration<*> = zf.entries()
        while (entries.hasMoreElements()) {
            val entry = entries.nextElement() as ZipEntry
            if (entry.name.contains(nameContains)) {
                val inputStream = zf.getInputStream(entry)
                var str = folderPath + File.separator + entry.name
                str = String(str.toByteArray(charset("utf-8")), Charset.forName("gbk"))
                val desFile = File(str)
                if (!desFile.exists()) {
                    val fileParentDir = desFile.parentFile
                    if (!fileParentDir.exists()) {
                        fileParentDir.mkdirs()
                    }
                    desFile.createNewFile()
                }
                val out: OutputStream = FileOutputStream(desFile)
                val buffer = ByteArray(BUFF_SIZE)
                var realLength: Int
                while (inputStream.read(buffer).also { realLength = it } > 0) {
                    out.write(buffer, 0, realLength)
                }
                inputStream.close()
                out.close()
                fileList.add(desFile)
            }
        }
        return fileList
    }

    /**
     * 获得压缩文件内文件列表
     *
     * @param zipFile 压缩文件
     * @return 压缩文件内文件名称
     * @throws ZipException 压缩文件格式有误时抛出
     * @throws IOException  当解压缩过程出错时抛出
     */
    @Throws(ZipException::class, IOException::class)
    fun getEntriesNames(zipFile: File?): ArrayList<String> {
        val entryNames = ArrayList<String>()
        val entries =
            getEntriesEnumeration(zipFile)
        while (entries.hasMoreElements()) {
            val entry = entries.nextElement() as ZipEntry
            entryNames.add(
                String(
                    getEntryName(entry).toByteArray(charset("GB2312")),
                    Charset.forName("8859_1")
                )
            )
        }
        return entryNames
    }

    /**
     * 获得压缩文件内压缩文件对象以取得其属性
     *
     * @param zipFile 压缩文件
     * @return 返回一个压缩文件列表
     * @throws ZipException 压缩文件格式有误时抛出
     * @throws IOException  IO操作有误时抛出
     */
    @Throws(ZipException::class, IOException::class)
    fun getEntriesEnumeration(zipFile: File?): Enumeration<*> {
        val zf = ZipFile(zipFile)
        return zf.entries()
    }

    /**
     * 取得压缩文件对象的名称
     *
     * @param entry 压缩文件对象
     * @return 压缩文件对象的名称
     * @throws UnsupportedEncodingException
     */
    @Throws(UnsupportedEncodingException::class)
    fun getEntryName(entry: ZipEntry): String {
        return String(entry.name.toByteArray(charset("GB2312")), Charset.forName("8859_1"))
    }

    @Throws(IOException::class)
    fun unzip(zipFileName: String?, outputDirectory: String) {
        var zipFile: ZipFile? = null
        try {
            zipFile = ZipFile(zipFileName)
            val e: Enumeration<*> = zipFile.entries()
            var zipEntry: ZipEntry? = null
            val dest = File(outputDirectory)
            dest.mkdirs()
            while (e.hasMoreElements()) {
                zipEntry = e.nextElement() as ZipEntry
                val entryName = zipEntry.name
                var inputStream: InputStream? = null
                var out: FileOutputStream? = null
                try {
                    if (zipEntry.isDirectory) {
                        var name = zipEntry.name
                        name = name.substring(0, name.length - 1)
                        val f = File(outputDirectory + File.separator + name)
                        f.mkdirs()
                    } else {
                        var index = entryName.lastIndexOf("\\")
                        if (index != -1) {
                            val df = File(
                                outputDirectory + File.separator + entryName.substring(
                                    0,
                                    index
                                )
                            )
                            df.mkdirs()
                        }
                        index = entryName.lastIndexOf("/")
                        if (index != -1) {
                            val df = File(
                                outputDirectory + File.separator
                                        + entryName.substring(0, index)
                            )
                            df.mkdirs()
                        }
                        val f = File(
                            outputDirectory + File.separator
                                    + zipEntry.name
                        )
                        // f.createNewFile();
                        inputStream = zipFile.getInputStream(zipEntry)
                        out = FileOutputStream(f)
                        var c: Int
                        val by = ByteArray(1024)
                        while (inputStream.read(by).also { c = it } != -1) {
                            out.write(by, 0, c)
                        }
                        out.flush()
                    }
                } catch (ex: IOException) {
                    ex.printStackTrace()
                    throw IOException("解压失败：$ex")
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close()
                        } catch (ex: IOException) {
                        }
                    }
                    if (out != null) {
                        try {
                            out.close()
                        } catch (ex: IOException) {
                        }
                    }
                }
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            throw IOException("解压失败：$ex")
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close()
                } catch (ex: IOException) {
                }
            }
        }
    }
}
