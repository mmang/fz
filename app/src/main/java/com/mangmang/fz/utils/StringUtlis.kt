package com.mangmang.fz.utils

/**
 * Created by mangmang on 2017/9/26.
 */
object StringUtlis {


    fun toUpperCase4Index(string: String, index: Int): String {
        val methodName = string.toCharArray()
        methodName[index] = toUpperCase(methodName[index])
        return String(methodName)
    }

    private fun toUpperCase(chars: Char): Char {
        var chars = chars
        if (97 <= chars.toInt() && chars.toInt() <= 122) {
            chars = (chars.toInt() xor (32)).toChar()
        }
        return chars
    }
}