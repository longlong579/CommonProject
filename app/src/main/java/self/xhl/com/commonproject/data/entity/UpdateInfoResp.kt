package com.tongji.braindata.data.entity


data class UpdateInfoResp(
        val versionNum: String?,
        val furtherContent: String?,
        val forceUpdate:Boolean,
        val url:String?
)