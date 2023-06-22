package com.itulah.todo_listapps.ui.home

data class ToDo(
    val assignTo: String? = null,
    val date: String? = null,
    val description: String? = null,
    val title: String? = null,
    val status: Boolean? = null
)