package com.gaofei.app.anr

import android.os.Environment
import com.gaofei.library.utils.LogUtils
import java.io.*
import java.lang.Exception

object IO {

    fun aa() {
        for (i in 0 until  10000) {
            val rootFile = Environment.getDataDirectory()
            val anrFile = File(rootFile, "app/SoftKeyboard/SoftKeyboard.apk")
            if (anrFile.exists()) {
                var inputStream: InputStreamReader? = null
                var outStream: OutputStreamWriter? = null
                try {
                    inputStream = InputStreamReader(FileInputStream(anrFile))
                    val aa = inputStream.readText()
                    LogUtils.d(111111111111111)
                    val writeFile = File(Environment.getExternalStorageDirectory(), "copy.txt")
                    if (!writeFile.exists()) {
                        writeFile.createNewFile()
                    }
                    outStream= OutputStreamWriter(FileOutputStream(writeFile))
                    outStream.write(aa)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    inputStream?.let {
                        it.close()
                    }
                    outStream?.let {
                        outStream.flush()
                        it.close()
                    }
                }

            }
        }

    }

    private fun getSDCard(): String {
        return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val path = Environment.getExternalStorageDirectory()
            path.path
        } else Environment.getDownloadCacheDirectory().path
    }
}