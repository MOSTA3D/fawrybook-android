package com.mosta3d.fawrybook.shared.client

import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo

data class Options(val dummy: String? = "", val responseType: TypeInfo = typeInfo<Any>())
