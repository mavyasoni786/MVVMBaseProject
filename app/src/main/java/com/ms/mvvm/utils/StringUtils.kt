package com.ms.mvvm.utils

import android.text.TextUtils

class StringUtils {

    companion object {

        private fun isNullOrEmpty(s: String?): Boolean {
            return s == null || s.isEmpty()
        }

        @JvmStatic
        fun hasContent(s: String?): Boolean {
            if (s == null) {
                return false
            } else return !isNullOrEmpty(s.trim { it <= ' ' })

        }

        fun trimStart(s: String, prefix: String): String {
            var s = s
            s = if (s.startsWith(prefix)) s.substring(prefix.length) else s
            return s
        }

        fun trimEnd(s: String, suffix: String): String {
            var s = s
            s = if (s.endsWith(suffix)) s.substring(0, s.length - suffix.length) else s
            return s
        }

        /* This function is used for validate email address
         *
         * @return true if email is valid otherwise false
         */
        fun isEmailValid(email: String): Boolean {
            return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        object Constants {
            var UTF_8 = "UTF-8"
        }
    }
}
