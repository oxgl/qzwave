package com.oxyggen.qzw.types

class FunctionCallbackIDIterator(first: FunctionCallbackID, val last: FunctionCallbackID) :
    Iterator<FunctionCallbackID> {

    private var current = first

    override fun hasNext(): Boolean = current < last

    override fun next(): FunctionCallbackID = if (hasNext()) ++current else last
}
