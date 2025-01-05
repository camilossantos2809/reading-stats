package io.readingstats.android.domain

import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

fun Timestamp.formatToDate(): String {
    val instant = this.toDate().toInstant()
    val localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return formatter.format(localDate)
}

fun String.toTimestamp(): Timestamp {
    val formatter = DateTimeFormatter.ofPattern("ddMMyyyy")
    val localDate = LocalDate.parse(this, formatter)
    val javaDate = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
    return Timestamp(javaDate)
}