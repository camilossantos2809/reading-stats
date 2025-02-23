package io.readingstats.android.services.db

import io.readingstats.android.BuildConfig
import tech.turso.libsql.Libsql

val db = Libsql.open(
//    path = "./teste.db",
    url = BuildConfig.TURSO_DATABASE_URL,
    authToken = BuildConfig.TURSO_AUTH_TOKEN,
//    syncInterval = 60
)

fun connect() = db.connect()

