package com.example.blogapp.core

import java.util.concurrent.TimeUnit

private const val SECOND_MILLIS = 1
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val DAY_MILLIS = 24 * HOUR_MILLIS

object TimeUtils {

    fun getTimeAgo(time: Int): String {

        val now = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
        if(time > now || time <= 0){
            return "en el futuro"
        }

        val diff = now - time
        return when {
            diff < MINUTE_MILLIS -> "Just now"
            diff < 2 * MINUTE_MILLIS -> "Hace un minuto"
            diff < 60 * MINUTE_MILLIS -> "Hace ${diff / MINUTE_MILLIS} minutos"
            diff < 2 * HOUR_MILLIS -> "Hace una hora"
            diff < 24 * HOUR_MILLIS -> "Hace ${diff / HOUR_MILLIS} horas"
            diff < 48 * HOUR_MILLIS -> "Ayer"
            else -> "Hace ${diff / DAY_MILLIS} dias"
        }
    }

}