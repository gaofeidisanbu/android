package com.yangcong345.android.phone.component.task2.model

import com.google.gson.annotations.Expose

class NewTaskSubTaskInfo(val id: Int, val name: String, val trigger: String, val status: String,  @Expose val duration: Long, award: Map<String, Any>, @Expose var coin: Int) {
    init {
        coin = award["count"] as Int
    }
}
