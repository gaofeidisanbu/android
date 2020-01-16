package com.gaofei.app.plugin

import android.util.Base64
import com.gaofei.library.utils.LogUtils
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.security.*
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.*
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


/**
 * @author: drawf
 * @date: 2019-07-19
 * @see: <a href=""></a>
 * @description: 加密工具类
 */
object EncryptUtil {

    ///////////////////////////////////////////////////////////////////////////
    // hash encryption
    ///////////////////////////////////////////////////////////////////////////

    /**
     * MD2 加密
     * Return the hex string of MD2 encryption.
     *
     * @param data The data.
     * @return the hex string of MD2 encryption
     */
    fun encryptMD2ToString(data: String?): String {
        return if (data == null || data.isEmpty()) "" else encryptMD2ToString(data.toByteArray())
    }

    /**
     * MD2 加密
     * Return the hex string of MD2 encryption.
     *
     * @param data The data.
     * @return the hex string of MD2 encryption
     */
    fun encryptMD2ToString(data: ByteArray): String {
        return bytes2HexString(encryptMD2(data))
    }

    /**
     * MD2 加密
     * Return the bytes of MD2 encryption.
     *
     * @param data The data.
     * @return the bytes of MD2 encryption
     */
    fun encryptMD2(data: ByteArray): ByteArray? {
        return hashTemplate(data, "MD2")
    }

    /**
     * MD5 加密
     * Return the hex string of MD5 encryption.
     *
     * @param data The data.
     * @return the hex string of MD5 encryption
     */
    fun encryptMD5ToString(data: String?): String {
        return if (data == null || data.isEmpty()) "" else encryptMD5ToString(data.toByteArray())
    }

    /**
     * MD5 加密
     * Return the hex string of MD5 encryption.
     *
     * @param data The data.
     * @param salt The salt.
     * @return the hex string of MD5 encryption
     */
    fun encryptMD5ToString(data: String?, salt: String?): String {
        if (data == null && salt == null) return ""
        if (salt == null) return bytes2HexString(encryptMD5(data!!.toByteArray()))
        return if (data == null) bytes2HexString(encryptMD5(salt.toByteArray())) else bytes2HexString(encryptMD5((data + salt).toByteArray()))
    }

    /**
     * MD5 加密
     * Return the hex string of MD5 encryption.
     *
     * @param data The data.
     * @return the hex string of MD5 encryption
     */
    fun encryptMD5ToString(data: ByteArray): String {
        return bytes2HexString(encryptMD5(data))
    }

    /**
     * MD5 加密
     * Return the hex string of MD5 encryption.
     *
     * @param data The data.
     * @param salt The salt.
     * @return the hex string of MD5 encryption
     */
    fun encryptMD5ToString(data: ByteArray?, salt: ByteArray?): String {
        if (data == null && salt == null) return ""
        if (salt == null) return bytes2HexString(encryptMD5(data!!))
        if (data == null) return bytes2HexString(encryptMD5(salt))
        val dataSalt = ByteArray(data.size + salt.size)
        System.arraycopy(data, 0, dataSalt, 0, data.size)
        System.arraycopy(salt, 0, dataSalt, data.size, salt.size)
        return bytes2HexString(encryptMD5(dataSalt))
    }

    /**
     * MD5 加密
     * Return the bytes of MD5 encryption.
     *
     * @param data The data.
     * @return the bytes of MD5 encryption
     */
    fun encryptMD5(data: ByteArray): ByteArray? {
        return hashTemplate(data, "MD5")
    }

    /**
     * MD5 加密文件
     * Return the hex string of file's MD5 encryption.
     *
     * @param filePath The path of file.
     * @return the hex string of file's MD5 encryption
     */
    fun encryptMD5File2String(filePath: String): String {
        val file = if (isSpace(filePath)) null else File(filePath)
        return encryptMD5File2String(file)
    }

    /**
     * MD5 加密文件
     * Return the bytes of file's MD5 encryption.
     *
     * @param filePath The path of file.
     * @return the bytes of file's MD5 encryption
     */
    fun encryptMD5File(filePath: String): ByteArray? {
        val file = if (isSpace(filePath)) null else File(filePath)
        return encryptMD5File(file)
    }

    /**
     * MD5 加密文件
     * Return the hex string of file's MD5 encryption.
     *
     * @param file The file.
     * @return the hex string of file's MD5 encryption
     */
    fun encryptMD5File2String(file: File?): String {
        return bytes2HexString(encryptMD5File(file))
    }

    /**
     * MD5 加密文件
     * Return the bytes of file's MD5 encryption.
     *
     * @param file The file.
     * @return the bytes of file's MD5 encryption
     */
    fun encryptMD5File(file: File?): ByteArray? {
        if (file == null) return null
        var fis: FileInputStream? = null
        val digestInputStream: DigestInputStream
        try {
            fis = FileInputStream(file)
            var md = MessageDigest.getInstance("MD5")
            digestInputStream = DigestInputStream(fis, md)
            val buffer = ByteArray(256 * 1024)
            while (true) {
                if (digestInputStream.read(buffer) <= 0) break
            }
            md = digestInputStream.messageDigest
            return md.digest()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            return null
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            try {
                if (fis != null) {
                    fis!!.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    /**
     * SHA1 加密
     * Return the hex string of SHA1 encryption.
     *
     * @param data The data.
     * @return the hex string of SHA1 encryption
     */
    fun encryptSHA1ToString(data: String?): String {
        return if (data == null || data.isEmpty()) "" else encryptSHA1ToString(data.toByteArray())
    }

    /**
     * SHA1 加密
     * Return the hex string of SHA1 encryption.
     *
     * @param data The data.
     * @return the hex string of SHA1 encryption
     */
    fun encryptSHA1ToString(data: ByteArray): String {
        return bytes2HexString(encryptSHA1(data))
    }

    /**
     * SHA1 加密
     * Return the bytes of SHA1 encryption.
     *
     * @param data The data.
     * @return the bytes of SHA1 encryption
     */
    fun encryptSHA1(data: ByteArray): ByteArray? {
        return hashTemplate(data, "SHA-1")
    }

    /**
     * SHA1 加密文件
     * Return the hex string of file's SHA1 encryption.
     *
     * @param file The file.
     * @return the hex string of file's SHA1 encryption
     */
    fun encryptSHA1File2String(file: File?): String {
        return bytes2HexString(encryptSHA1File(file))
    }

    /**
     * SHA1 加密文件
     * Return the bytes of file's SHA1 encryption.
     *
     * @param file The file.
     * @return the bytes of file's SHA1 encryption
     */
    fun encryptSHA1File(file: File?): ByteArray? {
        if (file == null) return null
        var fis: FileInputStream? = null
        val digestInputStream: DigestInputStream
        try {
            fis = FileInputStream(file)
            var md = MessageDigest.getInstance("SHA-1")
            digestInputStream = DigestInputStream(fis, md)
            val buffer = ByteArray(256 * 1024)
            while (true) {
                if (digestInputStream.read(buffer) <= 0) break
            }
            md = digestInputStream.messageDigest
            return md.digest()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            return null
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            try {
                if (fis != null) {
                    fis!!.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    /**
     * SHA224 加密
     * Return the hex string of SHA224 encryption.
     *
     * @param data The data.
     * @return the hex string of SHA224 encryption
     */
    fun encryptSHA224ToString(data: String?): String {
        return if (data == null || data.isEmpty()) "" else encryptSHA224ToString(data.toByteArray())
    }

    /**
     * SHA224 加密
     * Return the hex string of SHA224 encryption.
     *
     * @param data The data.
     * @return the hex string of SHA224 encryption
     */
    fun encryptSHA224ToString(data: ByteArray): String {
        return bytes2HexString(encryptSHA224(data))
    }

    /**
     * SHA224 加密
     * Return the bytes of SHA224 encryption.
     *
     * @param data The data.
     * @return the bytes of SHA224 encryption
     */
    fun encryptSHA224(data: ByteArray): ByteArray? {
        return hashTemplate(data, "SHA224")
    }

    /**
     * SHA256 加密
     * Return the hex string of SHA256 encryption.
     *
     * @param data The data.
     * @return the hex string of SHA256 encryption
     */
    fun encryptSHA256ToString(data: String?): String {
        return if (data == null || data.isEmpty()) "" else encryptSHA256ToString(data.toByteArray())
    }

    /**
     * SHA256 加密
     * Return the hex string of SHA256 encryption.
     *
     * @param data The data.
     * @return the hex string of SHA256 encryption
     */
    fun encryptSHA256ToString(data: ByteArray): String {
        return bytes2HexString(encryptSHA256(data))
    }

    /**
     * SHA256 加密
     * Return the bytes of SHA256 encryption.
     *
     * @param data The data.
     * @return the bytes of SHA256 encryption
     */
    fun encryptSHA256(data: ByteArray): ByteArray? {
        return hashTemplate(data, "SHA-256")
    }

    /**
     * SHA384 加密
     * Return the hex string of SHA384 encryption.
     *
     * @param data The data.
     * @return the hex string of SHA384 encryption
     */
    fun encryptSHA384ToString(data: String?): String {
        return if (data == null || data.isEmpty()) "" else encryptSHA384ToString(data.toByteArray())
    }

    /**
     * SHA384 加密
     * Return the hex string of SHA384 encryption.
     *
     * @param data The data.
     * @return the hex string of SHA384 encryption
     */
    fun encryptSHA384ToString(data: ByteArray): String {
        return bytes2HexString(encryptSHA384(data))
    }

    /**
     * SHA384 加密
     * Return the bytes of SHA384 encryption.
     *
     * @param data The data.
     * @return the bytes of SHA384 encryption
     */
    fun encryptSHA384(data: ByteArray): ByteArray? {
        return hashTemplate(data, "SHA-384")
    }

    /**
     * SHA512 加密
     * Return the hex string of SHA512 encryption.
     *
     * @param data The data.
     * @return the hex string of SHA512 encryption
     */
    fun encryptSHA512ToString(data: String?): String {
        return if (data == null || data.isEmpty()) "" else encryptSHA512ToString(data.toByteArray())
    }

    /**
     * SHA512 加密
     * Return the hex string of SHA512 encryption.
     *
     * @param data The data.
     * @return the hex string of SHA512 encryption
     */
    fun encryptSHA512ToString(data: ByteArray): String {
        return bytes2HexString(encryptSHA512(data))
    }

    /**
     * SHA512 加密
     * Return the bytes of SHA512 encryption.
     *
     * @param data The data.
     * @return the bytes of SHA512 encryption
     */
    fun encryptSHA512(data: ByteArray): ByteArray? {
        return hashTemplate(data, "SHA-512")
    }

    /**
     * Return the bytes of hash encryption.
     *
     * @param data      The data.
     * @param algorithm The name of hash encryption.
     * @return the bytes of hash encryption
     */
    private fun hashTemplate(data: ByteArray?, algorithm: String): ByteArray? {
        if (data == null || data.isEmpty()) return null
        return try {
            val md = MessageDigest.getInstance(algorithm)
            md.update(data)
            md.digest()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            null
        }

    }

    ///////////////////////////////////////////////////////////////////////////
    // hmac encryption
    ///////////////////////////////////////////////////////////////////////////

    /**
     * HmacMD5 加密
     * Return the hex string of HmacMD5 encryption.
     *
     * @param data The data.
     * @param key  The key.
     * @return the hex string of HmacMD5 encryption
     */
    fun encryptHmacMD5ToString(data: String?, key: String?): String {
        return if (data == null || data.isEmpty() || key == null || key.isEmpty()) "" else encryptHmacMD5ToString(data.toByteArray(), key.toByteArray())
    }

    /**
     * HmacMD5 加密
     * Return the hex string of HmacMD5 encryption.
     *
     * @param data The data.
     * @param key  The key.
     * @return the hex string of HmacMD5 encryption
     */
    fun encryptHmacMD5ToString(data: ByteArray, key: ByteArray): String {
        return bytes2HexString(encryptHmacMD5(data, key))
    }

    /**
     * HmacMD5 加密
     * Return the bytes of HmacMD5 encryption.
     *
     * @param data The data.
     * @param key  The key.
     * @return the bytes of HmacMD5 encryption
     */
    fun encryptHmacMD5(data: ByteArray, key: ByteArray): ByteArray? {
        return hmacTemplate(data, key, "HmacMD5")
    }

    /**
     * HmacSHA1 加密
     * Return the hex string of HmacSHA1 encryption.
     *
     * @param data The data.
     * @param key  The key.
     * @return the hex string of HmacSHA1 encryption
     */
    fun encryptHmacSHA1ToString(data: String?, key: String?): String {
        return if (data == null || data.isEmpty() || key == null || key.isEmpty()) "" else encryptHmacSHA1ToString(data.toByteArray(), key.toByteArray())
    }

    /**
     * HmacSHA1 加密
     * Return the hex string of HmacSHA1 encryption.
     *
     * @param data The data.
     * @param key  The key.
     * @return the hex string of HmacSHA1 encryption
     */
    fun encryptHmacSHA1ToString(data: ByteArray, key: ByteArray): String {
        return bytes2HexString(encryptHmacSHA1(data, key))
    }

    /**
     * HmacSHA1 加密
     * Return the bytes of HmacSHA1 encryption.
     *
     * @param data The data.
     * @param key  The key.
     * @return the bytes of HmacSHA1 encryption
     */
    fun encryptHmacSHA1(data: ByteArray, key: ByteArray): ByteArray? {
        return hmacTemplate(data, key, "HmacSHA1")
    }

    /**
     * HmacSHA224 加密
     * Return the hex string of HmacSHA224 encryption.
     *
     * @param data The data.
     * @param key  The key.
     * @return the hex string of HmacSHA224 encryption
     */
    fun encryptHmacSHA224ToString(data: String?, key: String?): String {
        return if (data == null || data.isEmpty() || key == null || key.isEmpty()) "" else encryptHmacSHA224ToString(data.toByteArray(), key.toByteArray())
    }

    /**
     * HmacSHA224 加密
     * Return the hex string of HmacSHA224 encryption.
     *
     * @param data The data.
     * @param key  The key.
     * @return the hex string of HmacSHA224 encryption
     */
    fun encryptHmacSHA224ToString(data: ByteArray, key: ByteArray): String {
        return bytes2HexString(encryptHmacSHA224(data, key))
    }

    /**
     * HmacSHA224 加密
     * Return the bytes of HmacSHA224 encryption.
     *
     * @param data The data.
     * @param key  The key.
     * @return the bytes of HmacSHA224 encryption
     */
    fun encryptHmacSHA224(data: ByteArray, key: ByteArray): ByteArray? {
        return hmacTemplate(data, key, "HmacSHA224")
    }

    /**
     * HmacSHA256 加密
     * Return the hex string of HmacSHA256 encryption.
     *
     * @param data The data.
     * @param key  The key.
     * @return the hex string of HmacSHA256 encryption
     */
    fun encryptHmacSHA256ToString(data: String?, key: String?): String {
        return if (data == null || data.isEmpty() || key == null || key.isEmpty()) "" else encryptHmacSHA256ToString(data.toByteArray(), key.toByteArray())
    }

    /**
     * HmacSHA256 加密
     * Return the hex string of HmacSHA256 encryption.
     *
     * @param data The data.
     * @param key  The key.
     * @return the hex string of HmacSHA256 encryption
     */
    fun encryptHmacSHA256ToString(data: ByteArray, key: ByteArray): String {
        return bytes2HexString(encryptHmacSHA256(data, key))
    }

    /**
     * HmacSHA256 加密
     * Return the bytes of HmacSHA256 encryption.
     *
     * @param data The data.
     * @param key  The key.
     * @return the bytes of HmacSHA256 encryption
     */
    fun encryptHmacSHA256(data: ByteArray, key: ByteArray): ByteArray? {
        return hmacTemplate(data, key, "HmacSHA256")
    }

    /**
     * HmacSHA384 加密
     * Return the hex string of HmacSHA384 encryption.
     *
     * @param data The data.
     * @param key  The key.
     * @return the hex string of HmacSHA384 encryption
     */
    fun encryptHmacSHA384ToString(data: String?, key: String?): String {
        return if (data == null || data.isEmpty() || key == null || key.isEmpty()) "" else encryptHmacSHA384ToString(data.toByteArray(), key.toByteArray())
    }

    /**
     * HmacSHA384 加密
     * Return the hex string of HmacSHA384 encryption.
     *
     * @param data The data.
     * @param key  The key.
     * @return the hex string of HmacSHA384 encryption
     */
    fun encryptHmacSHA384ToString(data: ByteArray, key: ByteArray): String {
        return bytes2HexString(encryptHmacSHA384(data, key))
    }

    /**
     * HmacSHA384 加密
     * Return the bytes of HmacSHA384 encryption.
     *
     * @param data The data.
     * @param key  The key.
     * @return the bytes of HmacSHA384 encryption
     */
    fun encryptHmacSHA384(data: ByteArray, key: ByteArray): ByteArray? {
        return hmacTemplate(data, key, "HmacSHA384")
    }

    /**
     * HmacSHA512 加密
     * Return the hex string of HmacSHA512 encryption.
     *
     * @param data The data.
     * @param key  The key.
     * @return the hex string of HmacSHA512 encryption
     */
    fun encryptHmacSHA512ToString(data: String?, key: String?): String {
        return if (data == null || data.isEmpty() || key == null || key.isEmpty()) "" else encryptHmacSHA512ToString(data.toByteArray(), key.toByteArray())
    }

    /**
     * HmacSHA512 加密
     * Return the hex string of HmacSHA512 encryption.
     *
     * @param data The data.
     * @param key  The key.
     * @return the hex string of HmacSHA512 encryption
     */
    fun encryptHmacSHA512ToString(data: ByteArray, key: ByteArray): String {
        return bytes2HexString(encryptHmacSHA512(data, key))
    }

    /**
     * HmacSHA512 加密
     * Return the bytes of HmacSHA512 encryption.
     *
     * @param data The data.
     * @param key  The key.
     * @return the bytes of HmacSHA512 encryption
     */
    fun encryptHmacSHA512(data: ByteArray, key: ByteArray): ByteArray? {
        return hmacTemplate(data, key, "HmacSHA512")
    }

    /**
     * Return the bytes of hmac encryption.
     *
     * @param data      The data.
     * @param key       The key.
     * @param algorithm The name of hmac encryption.
     * @return the bytes of hmac encryption
     */
    private fun hmacTemplate(data: ByteArray?,
                             key: ByteArray?,
                             algorithm: String): ByteArray? {
        if (data == null || data.isEmpty() || key == null || key.isEmpty()) return null
        return try {
            val secretKey = SecretKeySpec(key, algorithm)
            val mac = Mac.getInstance(algorithm)
            mac.init(secretKey)
            mac.doFinal(data)
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
            null
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            null
        }

    }

    ///////////////////////////////////////////////////////////////////////////
    // DES encryption
    ///////////////////////////////////////////////////////////////////////////

    /**
     * DES 加密
     * Return the Base64-encode bytes of DES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
     * @param iv             The buffer with the IV. The contents of the
     * buffer are copied to protect against subsequent modification.
     * @return the Base64-encode bytes of DES encryption
     */
    fun encryptDES2Base64(data: ByteArray,
                          key: ByteArray,
                          transformation: String,
                          iv: ByteArray): ByteArray {
        return base64Encode(encryptDES(data, key, transformation, iv))
    }

    /**
     * DES 加密
     * Return the hex string of DES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
     * @param iv             The buffer with the IV. The contents of the
     * buffer are copied to protect against subsequent modification.
     * @return the hex string of DES encryption
     */
    fun encryptDES2HexString(data: ByteArray,
                             key: ByteArray,
                             transformation: String,
                             iv: ByteArray): String {
        return bytes2HexString(encryptDES(data, key, transformation, iv))
    }

    /**
     * DES 加密
     * Return the bytes of DES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
     * @param iv             The buffer with the IV. The contents of the
     * buffer are copied to protect against subsequent modification.
     * @return the bytes of DES encryption
     */
    fun encryptDES(data: ByteArray,
                   key: ByteArray,
                   transformation: String,
                   iv: ByteArray): ByteArray? {
        return symmetricTemplate(data, key, "DES", transformation, iv, true)
    }

    /**
     * DES 解密
     * Return the bytes of DES decryption for Base64-encode bytes.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
     * @param iv             The buffer with the IV. The contents of the
     * buffer are copied to protect against subsequent modification.
     * @return the bytes of DES decryption for Base64-encode bytes
     */
    fun decryptBase64DES(data: ByteArray,
                         key: ByteArray,
                         transformation: String,
                         iv: ByteArray): ByteArray? {
        return decryptDES(base64Decode(data), key, transformation, iv)
    }

    /**
     * DES 解密
     * Return the bytes of DES decryption for hex string.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
     * @param iv             The buffer with the IV. The contents of the
     * buffer are copied to protect against subsequent modification.
     * @return the bytes of DES decryption for hex string
     */
    fun decryptHexStringDES(data: String,
                            key: ByteArray,
                            transformation: String,
                            iv: ByteArray): ByteArray? {
        return decryptDES(hexString2Bytes(data), key, transformation, iv)
    }

    /**
     * DES 解密
     * Return the bytes of DES decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
     * @param iv             The buffer with the IV. The contents of the
     * buffer are copied to protect against subsequent modification.
     * @return the bytes of DES decryption
     */
    fun decryptDES(data: ByteArray?,
                   key: ByteArray,
                   transformation: String,
                   iv: ByteArray): ByteArray? {
        return symmetricTemplate(data, key, "DES", transformation, iv, false)
    }

    ///////////////////////////////////////////////////////////////////////////
    // 3DES encryption
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 3DES 加密
     * Return the Base64-encode bytes of 3DES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
     * @param iv             The buffer with the IV. The contents of the
     * buffer are copied to protect against subsequent modification.
     * @return the Base64-encode bytes of 3DES encryption
     */
    fun encrypt3DES2Base64(data: ByteArray,
                           key: ByteArray,
                           transformation: String,
                           iv: ByteArray): ByteArray {
        return base64Encode(encrypt3DES(data, key, transformation, iv))
    }

    /**
     * 3DES 加密
     * Return the hex string of 3DES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
     * @param iv             The buffer with the IV. The contents of the
     * buffer are copied to protect against subsequent modification.
     * @return the hex string of 3DES encryption
     */
    fun encrypt3DES2HexString(data: ByteArray,
                              key: ByteArray,
                              transformation: String,
                              iv: ByteArray): String {
        return bytes2HexString(encrypt3DES(data, key, transformation, iv))
    }

    /**
     * 3DES 加密
     * Return the bytes of 3DES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
     * @param iv             The buffer with the IV. The contents of the
     * buffer are copied to protect against subsequent modification.
     * @return the bytes of 3DES encryption
     */
    fun encrypt3DES(data: ByteArray,
                    key: ByteArray,
                    transformation: String,
                    iv: ByteArray): ByteArray? {
        return symmetricTemplate(data, key, "DESede", transformation, iv, true)
    }

    /**
     * 3DES 解密
     * Return the bytes of 3DES decryption for Base64-encode bytes.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
     * @param iv             The buffer with the IV. The contents of the
     * buffer are copied to protect against subsequent modification.
     * @return the bytes of 3DES decryption for Base64-encode bytes
     */
    fun decryptBase64_3DES(data: ByteArray,
                           key: ByteArray,
                           transformation: String,
                           iv: ByteArray): ByteArray? {
        return decrypt3DES(base64Decode(data), key, transformation, iv)
    }

    /**
     * 3DES 解密
     * Return the bytes of 3DES decryption for hex string.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
     * @param iv             The buffer with the IV. The contents of the
     * buffer are copied to protect against subsequent modification.
     * @return the bytes of 3DES decryption for hex string
     */
    fun decryptHexString3DES(data: String,
                             key: ByteArray,
                             transformation: String,
                             iv: ByteArray): ByteArray? {
        return decrypt3DES(hexString2Bytes(data), key, transformation, iv)
    }

    /**
     * 3DES 解密
     * Return the bytes of 3DES decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
     * @param iv             The buffer with the IV. The contents of the
     * buffer are copied to protect against subsequent modification.
     * @return the bytes of 3DES decryption
     */
    fun decrypt3DES(data: ByteArray?,
                    key: ByteArray,
                    transformation: String,
                    iv: ByteArray): ByteArray? {
        return symmetricTemplate(data, key, "DESede", transformation, iv, false)
    }

    ///////////////////////////////////////////////////////////////////////////
    // AES encryption
    ///////////////////////////////////////////////////////////////////////////

    /**
     * AES 加密
     * Return the Base64-encode bytes of AES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
     * @param iv             The buffer with the IV. The contents of the
     * buffer are copied to protect against subsequent modification.
     * @return the Base64-encode bytes of AES encryption
     */
    fun encryptAES2Base64(data: ByteArray,
                          key: ByteArray,
                          transformation: String,
                          iv: ByteArray): ByteArray {
        return base64Encode(encryptAES(data, key, transformation, iv))
    }

    /**
     * AES 加密
     * Return the hex string of AES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
     * @param iv             The buffer with the IV. The contents of the
     * buffer are copied to protect against subsequent modification.
     * @return the hex string of AES encryption
     */
    fun encryptAES2HexString(data: ByteArray,
                             key: ByteArray,
                             transformation: String,
                             iv: ByteArray): String {
        return bytes2HexString(encryptAES(data, key, transformation, iv))
    }

    /**
     * AES 加密
     * Return the bytes of AES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
     * @param iv             The buffer with the IV. The contents of the
     * buffer are copied to protect against subsequent modification.
     * @return the bytes of AES encryption
     */
    fun encryptAES(data: ByteArray,
                   key: ByteArray,
                   transformation: String,
                   iv: ByteArray): ByteArray? {
        return symmetricTemplate(data, key, "AES", transformation, iv, true)
    }

    /**
     * AES 解密
     * Return the bytes of AES decryption for Base64-encode bytes.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
     * @param iv             The buffer with the IV. The contents of the
     * buffer are copied to protect against subsequent modification.
     * @return the bytes of AES decryption for Base64-encode bytes
     */
    fun decryptBase64AES(data: ByteArray,
                         key: ByteArray,
                         transformation: String,
                         iv: ByteArray): ByteArray? {
        return decryptAES(base64Decode(data), key, transformation, iv)
    }

    /**
     * AES 解密
     * Return the bytes of AES decryption for hex string.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
     * @param iv             The buffer with the IV. The contents of the
     * buffer are copied to protect against subsequent modification.
     * @return the bytes of AES decryption for hex string
     */
    fun decryptHexStringAES(data: String,
                            key: ByteArray,
                            transformation: String,
                            iv: ByteArray): ByteArray? {
        return decryptAES(hexString2Bytes(data), key, transformation, iv)
    }

    /**
     * AES 解密
     * Return the bytes of AES decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
     * @param iv             The buffer with the IV. The contents of the
     * buffer are copied to protect against subsequent modification.
     * @return the bytes of AES decryption
     */
    fun decryptAES(data: ByteArray?,
                   key: ByteArray,
                   transformation: String,
                   iv: ByteArray): ByteArray? {
        return symmetricTemplate(data, key, "AES", transformation, iv, false)
    }

    /**
     * Return the bytes of symmetric encryption or decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param algorithm      The name of algorithm.
     * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS5Padding*.
     * @param isEncrypt      True to encrypt, false otherwise.
     * @return the bytes of symmetric encryption or decryption
     */
    private fun symmetricTemplate(data: ByteArray?,
                                  key: ByteArray?,
                                  algorithm: String,
                                  transformation: String,
                                  iv: ByteArray?,
                                  isEncrypt: Boolean): ByteArray? {
        if (data == null || data.isEmpty() || key == null || key.isEmpty()) return null
            val secretKey: SecretKey
            if ("DES" == algorithm) {
                val desKey = DESKeySpec(key)
                val keyFactory = SecretKeyFactory.getInstance(algorithm)
                secretKey = keyFactory.generateSecret(desKey)
            } else {
                secretKey = SecretKeySpec(key, algorithm)
            }
            val cipher = Cipher.getInstance(transformation)
            if (iv == null || iv.isEmpty()) {
                cipher.init(if (isEncrypt) Cipher.ENCRYPT_MODE else Cipher.DECRYPT_MODE, secretKey)
            } else {
                val params = IvParameterSpec(iv)
                cipher.init(if (isEncrypt) Cipher.ENCRYPT_MODE else Cipher.DECRYPT_MODE, secretKey, params)
            }
            return cipher.doFinal(data)

    }

    ///////////////////////////////////////////////////////////////////////////
    // RSA encryption
    ///////////////////////////////////////////////////////////////////////////

    /**
     * RSA 加密
     * Return the Base64-encode bytes of RSA encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param isPublicKey    True to use public key, false to use private key.
     * @param transformation The name of the transformation, e.g., *RSA/CBC/PKCS1Padding*.
     * @return the Base64-encode bytes of RSA encryption
     */
    fun encryptRSA2Base64(data: ByteArray,
                          key: ByteArray,
                          isPublicKey: Boolean,
                          transformation: String): ByteArray {
        return base64Encode(encryptRSA(data, key, isPublicKey, transformation))
    }

    /**
     * RSA 加密
     * Return the hex string of RSA encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param isPublicKey    True to use public key, false to use private key.
     * @param transformation The name of the transformation, e.g., *RSA/CBC/PKCS1Padding*.
     * @return the hex string of RSA encryption
     */
    fun encryptRSA2HexString(data: ByteArray,
                             key: ByteArray,
                             isPublicKey: Boolean,
                             transformation: String): String {
        return bytes2HexString(encryptRSA(data, key, isPublicKey, transformation))
    }

    /**
     * RSA 加密
     * Return the bytes of RSA encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param isPublicKey    True to use public key, false to use private key.
     * @param transformation The name of the transformation, e.g., *RSA/CBC/PKCS1Padding*.
     * @return the bytes of RSA encryption
     */
    fun encryptRSA(data: ByteArray,
                   key: ByteArray,
                   isPublicKey: Boolean,
                   transformation: String): ByteArray? {
        return rsaTemplate(data, key, isPublicKey, transformation, true)
    }

    /**
     * RSA 解密
     * Return the bytes of RSA decryption for Base64-encode bytes.
     *
     * @param data           The data.
     * @param key            The key.
     * @param isPublicKey    True to use public key, false to use private key.
     * @param transformation The name of the transformation, e.g., *RSA/CBC/PKCS1Padding*.
     * @return the bytes of RSA decryption for Base64-encode bytes
     */
    fun decryptBase64RSA(data: ByteArray,
                         key: ByteArray,
                         isPublicKey: Boolean,
                         transformation: String): ByteArray? {
        return decryptRSA(base64Decode(data), key, isPublicKey, transformation)
    }

    /**
     * RSA 解密
     * Return the bytes of RSA decryption for hex string.
     *
     * @param data           The data.
     * @param key            The key.
     * @param isPublicKey    True to use public key, false to use private key.
     * @param transformation The name of the transformation, e.g., *RSA/CBC/PKCS1Padding*.
     * @return the bytes of RSA decryption for hex string
     */
    fun decryptHexStringRSA(data: String,
                            key: ByteArray,
                            isPublicKey: Boolean,
                            transformation: String): ByteArray? {
        return decryptRSA(hexString2Bytes(data), key, isPublicKey, transformation)
    }

    /**
     * RSA 解密
     * Return the bytes of RSA decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param isPublicKey    True to use public key, false to use private key.
     * @param transformation The name of the transformation, e.g., *RSA/CBC/PKCS1Padding*.
     * @return the bytes of RSA decryption
     */
    fun decryptRSA(data: ByteArray?,
                   key: ByteArray,
                   isPublicKey: Boolean,
                   transformation: String): ByteArray? {
        return rsaTemplate(data, key, isPublicKey, transformation, false)
    }

    /**
     * Return the bytes of RSA encryption or decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param isPublicKey    True to use public key, false to use private key.
     * @param transformation The name of the transformation, e.g., *DES/CBC/PKCS1Padding*.
     * @param isEncrypt      True to encrypt, false otherwise.
     * @return the bytes of RSA encryption or decryption
     */
    private fun rsaTemplate(data: ByteArray?,
                            key: ByteArray?,
                            isPublicKey: Boolean,
                            transformation: String,
                            isEncrypt: Boolean): ByteArray? {
        if (data == null || data.size == 0 || key == null || key.size == 0) {
            return null
        }
        try {
            val rsaKey: Key?
            if (isPublicKey) {
                val keySpec = X509EncodedKeySpec(key)
                rsaKey = KeyFactory.getInstance("RSA").generatePublic(keySpec)
            } else {
                val keySpec = PKCS8EncodedKeySpec(key)
                rsaKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec)
            }
            if (rsaKey == null) return null
            val cipher = Cipher.getInstance(transformation)
            cipher.init(if (isEncrypt) Cipher.ENCRYPT_MODE else Cipher.DECRYPT_MODE, rsaKey)
            val len = data.size
            val maxLen = if (isEncrypt) 117 else 128
            val count = len / maxLen
            if (count > 0) {
                var ret = ByteArray(0)
                var buff = ByteArray(maxLen)
                var index = 0
                for (i in 0 until count) {
                    System.arraycopy(data, index, buff, 0, maxLen)
                    ret = joins(ret, cipher.doFinal(buff))
                    index += maxLen
                }
                if (index != len) {
                    val restLen = len - index
                    buff = ByteArray(restLen)
                    System.arraycopy(data, index, buff, 0, restLen)
                    ret = joins(ret, cipher.doFinal(buff))
                }
                return ret
            } else {
                return cipher.doFinal(data)
            }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: NoSuchPaddingException) {
            e.printStackTrace()
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
        } catch (e: BadPaddingException) {
            e.printStackTrace()
        } catch (e: IllegalBlockSizeException) {
            e.printStackTrace()
        } catch (e: InvalidKeySpecException) {
            e.printStackTrace()
        }

        return null
    }

    ///////////////////////////////////////////////////////////////////////////
    // other utils methods
    ///////////////////////////////////////////////////////////////////////////

    private fun joins(prefix: ByteArray, suffix: ByteArray): ByteArray {
        val ret = ByteArray(prefix.size + suffix.size)
        System.arraycopy(prefix, 0, ret, 0, prefix.size)
        System.arraycopy(suffix, 0, ret, prefix.size, suffix.size)
        return ret
    }

    private val HEX_DIGITS = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

    private fun bytes2HexString(bytes: ByteArray?): String {
        if (bytes == null) return ""
        val len = bytes.size
        if (len <= 0) return ""
        val ret = CharArray(len shl 1)
        var i = 0
        var j = 0
        while (i < len) {
            ret[j++] = HEX_DIGITS[bytes[i].toInt() shr 4 and 0x0f]
            ret[j++] = HEX_DIGITS[bytes[i].toInt() and 0x0f]
            i++
        }
        return String(ret)
    }

    private fun hexString2Bytes(hexStr: String): ByteArray? {
        var hexString = hexStr
        if (isSpace(hexString)) return null
        var len = hexString.length
        if (len % 2 != 0) {
            hexString = "0$hexString"
            len += 1
        }
        val hexBytes = hexString.toUpperCase().toCharArray()
        val ret = ByteArray(len shr 1)
        var i = 0
        while (i < len) {
            ret[i shr 1] = (hex2Dec(hexBytes[i]) shl 4 or hex2Dec(hexBytes[i + 1])).toByte()
            i += 2
        }
        return ret
    }

    private fun hex2Dec(hexChar: Char): Int {
        return when (hexChar) {
            in '0'..'9' -> hexChar - '0'
            in 'A'..'F' -> hexChar - 'A' + 10
            else -> throw IllegalArgumentException()
        }
    }

    private fun base64Encode(input: ByteArray?): ByteArray {
        return Base64.encode(input, Base64.NO_WRAP)
    }

    private fun base64Decode(input: ByteArray): ByteArray {
        return Base64.decode(input, Base64.NO_WRAP)
    }

    private fun isSpace(s: String?): Boolean {
        if (s == null) return true
        var i = 0
        val len = s.length
        while (i < len) {
            if (!Character.isWhitespace(s[i])) {
                return false
            }
            ++i
        }
        return true
    }

}