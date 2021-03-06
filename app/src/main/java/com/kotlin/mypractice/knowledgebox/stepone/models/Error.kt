package com.kotlin.mypractice.knowledgebox.stepone.models

enum class Error {
    NOT_FOUND,
    UNKNOWN;

    companion object {
        fun from(name: String): Error? {
            return Error.values().find { it.name == name }
        }
    }
}
