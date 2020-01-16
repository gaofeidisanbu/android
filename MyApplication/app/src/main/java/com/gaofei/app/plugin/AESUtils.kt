package com.gaofei.app.plugin

import android.text.TextUtils
import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 *
 * @Author gaofei
 * @Date 2019-12-31-18:07
 * @Email gaofei@guanghe.tv
 */

object AesUtils {

    @Throws(Exception::class)
    private fun decrypt(content: ByteArray, password: String): ByteArray {
        // 创建AES秘钥
        val key = SecretKeySpec(password.toByteArray(), "AES/CBC/PKCS5PADDING")
        // 创建密码器
        val cipher = Cipher.getInstance("AES")
        // 初始化解密器
        cipher.init(Cipher.DECRYPT_MODE, key)
        // 解密
        return cipher.doFinal(content)
    }


    /*
 * 加密
 */
    fun encrypt(cleartext: String): String? {
        if (TextUtils.isEmpty(cleartext)) {
            return cleartext
        }
        try {
            val result = encrypt("1234567890123456".toByteArray(), cleartext)
            return String(Base64.encode(result, Base64.DEFAULT))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    @Throws(Exception::class)
    private fun encrypt(password: ByteArray, clear: String): ByteArray {
        // 创建AES秘钥
        val secretKeySpec = SecretKeySpec(password, "AES/CBC/PKCS5PADDING")
        // 创建密码器
        val cipher = Cipher.getInstance("AES")
        // 初始化加密器
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        // 加密
        return cipher.doFinal(clear.toByteArray(charset("UTF-8")))
    }

}