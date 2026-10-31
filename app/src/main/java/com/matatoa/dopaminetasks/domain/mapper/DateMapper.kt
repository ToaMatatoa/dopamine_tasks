package com.matatoa.dopaminetasks.domain.mapper

import java.time.LocalDate
import java.time.ZoneId

fun LocalDate.toStartOfDayMillis(): Long =
    atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
