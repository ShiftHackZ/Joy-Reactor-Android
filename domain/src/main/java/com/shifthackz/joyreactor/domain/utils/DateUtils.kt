package com.shifthackz.joyreactor.domain.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

val EpochDate = Date(0)

const val UTC_LIKE_PATTERN = "yyyy-MM-dd HH:mm:ss zzz"
val UtcTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault())
val TimeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

@SuppressLint("ConstantLocale")
val hourMinuteFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

@SuppressLint("ConstantLocale")
val dayOfWeekFormat = SimpleDateFormat("EEEE", Locale.getDefault())

infix fun Date.diffTo(other: Date): DateDiff = DateDiff(this, other)

const val HOUR_MINUTE_SECONDS_FORMAT = "%02d:%02d:%02d"
const val DAY_HOUR_MINUTE_SECONDS_FORMAT = "%dd $HOUR_MINUTE_SECONDS_FORMAT"

private const val secondsInMilli = 1000L
private const val minutesInMilli = secondsInMilli * 60
private const val hoursInMilli = minutesInMilli * 60
private const val daysInMilli = hoursInMilli * 24

fun formatMillisToTime(millis: Long): String {
    return TimeFormat.format(Date(millis)).toString()
}

/**
 * Converts milliseconds to model.
 */
fun Long.toDateDiffModel(): DateDiff.Model {
    var initial = this // milliseconds
    val elapsedDays = initial / daysInMilli
    initial %= daysInMilli
    val elapsedHours = initial / hoursInMilli
    initial %= hoursInMilli
    val elapsedMinutes = initial / minutesInMilli
    initial %= minutesInMilli
    val elapsedSeconds = initial / secondsInMilli
    return DateDiff.Model(initial, elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds)
}

fun DateDiff.Model.toUserFriendlyFormat(): String {
    val (_, days, hours, minutes, seconds) = this
    return if (days != 0L) String.format(
        DAY_HOUR_MINUTE_SECONDS_FORMAT,
        days,
        hours,
        minutes,
        seconds
    )
    else String.format(HOUR_MINUTE_SECONDS_FORMAT, hours, minutes, seconds)
}

data class DateDiff(
    val startDate: Date,
    val endDate: Date
) {
    private val overall = endDate.time - startDate.time
    private val elapsedDays: Long
    private val elapsedHours: Long
    private val elapsedMinutes: Long
    private val elapsedSeconds: Long

    init {
        with(overall.toDateDiffModel()) {
            this@DateDiff.elapsedDays = elapsedDays
            this@DateDiff.elapsedHours = elapsedHours
            this@DateDiff.elapsedMinutes = elapsedMinutes
            this@DateDiff.elapsedSeconds = elapsedSeconds
        }
    }

    /**
     * Get difference between [startDate] and [endDate]
     * @return elapsed days difference
     */
    fun getDays() = elapsedDays

    /**
     * Get difference between [startDate] and [endDate]
     * @return elapsed hours difference
     */
    fun getHours() = elapsedHours

    /**
     * Get difference between [startDate] and [endDate]
     * @return elapsed minutes difference
     */
    fun getMinutes() = elapsedMinutes

    /**
     * Get difference between [endDate] and [startDate]
     * @return elapsed seconds difference
     */
    fun getSeconds() = elapsedSeconds

    /**
     * Get difference between [startDate] and [endDate]
     * @return overall difference in milliseconds
     */
    fun getOverall() = overall

    inline val model: Model
        get() = Model(getOverall(), getDays(), getHours(), getMinutes(), getSeconds())

    data class Model(
        val overall: Long,
        val elapsedDays: Long,
        val elapsedHours: Long,
        val elapsedMinutes: Long,
        val elapsedSeconds: Long
    )

    fun elapsedString(showSeconds: Boolean = true): String {
        val builder = StringBuilder()
        if (getDays() != 0L) {
            builder.append("${getDays()}d ")
        }
        builder.append("${getHours().addZero()}:")
        builder.append(getMinutes().addZero())
        if (showSeconds)
            builder.append(":${getSeconds().addZero()}")
        return builder.toString()
    }

    private fun Long.addZero() = when {
        this < 10 -> "0${this}"
        else -> this.toString()
    }

    override fun toString(): String = with(StringBuilder()) {
        append("DateDiff(")
        append("startDate=$startDate, ")
        append("endDate=$endDate, ")
        append("overall=$overall, ")
        append("days=$elapsedDays, ")
        append("hours=$elapsedHours, ")
        append("minutes=$elapsedMinutes, ")
        append("seconds=$elapsedSeconds")
        append(")")
        toString()
    }
}

class DateBuilder(time: Long? = null) {
    private val date = time?.let(::Date) ?: Date()
    private var year: Int? = null
    private var month: Int? = null

    fun setYear(value: Int) = apply {
        year = value
    }

    fun setMonth(value: Int) = apply {
        month = value
    }

    fun build(): Date = date.apply {
        time = Calendar.getInstance().apply {
            this@DateBuilder.year?.let {
                set(Calendar.YEAR, it)
            }
            this@DateBuilder.month?.let {
                set(Calendar.MONTH, it)
            }
        }.timeInMillis
    }
}

fun Date.toDayPercent() = Calendar.getInstance().let {
    it.time = this
    val seconds = it.get(Calendar.SECOND)
    val secondsOfMinutes = TimeUnit.MINUTES.toSeconds(it.get(Calendar.MINUTE).toLong())
    val secondsOfHours = TimeUnit.HOURS.toSeconds(it.get(Calendar.HOUR_OF_DAY).toLong())
    (seconds + secondsOfMinutes + secondsOfHours) / TimeUnit.DAYS.toSeconds(1).toFloat()
}

inline val Date.dayOfTheWeek: Int
    get() = with(Calendar.getInstance()) {
        time = this@dayOfTheWeek
        get(Calendar.DAY_OF_WEEK)
    }

inline val Date.hoursInt: Int
    get() = with(Calendar.getInstance()) {
        time = this@hoursInt
        get(Calendar.HOUR_OF_DAY)
    }

inline val Date.hoursInMinutesInt: Int
    get() = with(Calendar.getInstance()) {
        time = this@hoursInMinutesInt
        get(Calendar.HOUR_OF_DAY) * 60
    }

inline val Date.minutesInt: Int
    get() = with(Calendar.getInstance()) {
        time = this@minutesInt
        get(Calendar.MINUTE)
    }

inline val Date.secondsInt: Int
    get() = with(Calendar.getInstance()) {
        time = this@secondsInt
        get(Calendar.SECOND)
    }
