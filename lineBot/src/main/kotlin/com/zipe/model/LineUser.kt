package com.zipe.model

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable

@ApiModel(description = "Line 使用者")
data class LineUser(

    @ApiModelProperty(value = "Line id", required = true)
    var lineId: String = "",

    @ApiModelProperty(value = "Line name", required = false)
    var name: String = ""

) : Serializable
