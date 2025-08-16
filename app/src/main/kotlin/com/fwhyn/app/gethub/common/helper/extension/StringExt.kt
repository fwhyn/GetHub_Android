package com.fwhyn.app.gethub.common.helper.extension

fun String.trimSpaceTabEnter(): String {
    return this.trim().replace("\\s+".toRegex(), "")
}