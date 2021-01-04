package com.oxyggen.qzw.command.serial

class BitMap {
    fun number(name:String, bits:IntRange) {

    }

}

class SerialDefinition {

//    var context = mutableListOf<>()

    fun byte(name: String, enabled:String="true") {

    }

    fun bitMap(init: BitMap.() -> Unit): BitMap {
        val bitmap = BitMap()
        bitmap.init()
        return bitmap
    }

    fun byteCol(name: String, length:String) {

    }
}

fun serdef(init: SerialDefinition.() -> Unit): SerialDefinition {
    val serdef = SerialDefinition()
    serdef.init()
    return serdef
}

