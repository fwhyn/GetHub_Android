package com.fwhyn.app.gethub.common.helper

fun String.trimSpaceTabEnter(): String {
    return this.trim().replace("\\s+".toRegex(), "")
}