package com.shifthackz.joyreactor.presentation.entity


import android.content.Context
import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

sealed class TextUI {

    data class Static(val value: String) : TextUI()

    class Resource(
        @StringRes val resId: Int,
        vararg val args: Any,
    ) : TextUI()

    class Concat(
        vararg val texts: Any,
        val separator: String = "",
    ) : TextUI()

    fun asString(resources: Resources): String = when (this) {
        is Static -> value
        is Resource -> resources.getString(resId, *args.nestedArgs(resources))
        is Concat -> buildString {
            texts.map { it.mapArg(resources) + separator }.forEach(::append)
        }
    }

    fun asString(context: Context): String = asString(context.resources)

    override fun equals(other: Any?): Boolean = when (other) {
        is TextUI -> when (other) {
            is Concat -> this is Concat && this.texts.contentEquals(other.texts)
            is Resource -> this is Resource && this.resId == other.resId
            is Static -> this is Static && this.value == other.value
        }
        else -> false
    }

    override fun hashCode(): Int = javaClass.hashCode()

    companion object {
        val empty: TextUI = Static("")
    }
}

fun String.asTextUi(): TextUI.Static = TextUI.Static(this)

fun Int.asTextUi(): TextUI.Resource = TextUI.Resource(this)

fun Array<out Any>.nestedArgs(resources: Resources) =
    map { it.mapArg(resources) }.toTypedArray()

fun Any.mapArg(resources: Resources): String = when (this) {
    is TextUI.Resource -> asString(resources)
    is TextUI.Concat -> asString(resources)
    is TextUI.Static -> value
    is Int -> TextUI.Resource(this).asString(resources)
    else -> this.toString()
}

@Composable
fun TextUI.asString(): String = when(this) {
    is TextUI.Static -> value
    is TextUI.Resource -> {
        stringResource(resId, *args.nestedArgs(LocalContext.current.resources))
    }
    is TextUI.Concat -> buildString {
        texts
            .map { it.mapArg(LocalContext.current.resources) + separator}
            .forEach(::append)
    }
}
