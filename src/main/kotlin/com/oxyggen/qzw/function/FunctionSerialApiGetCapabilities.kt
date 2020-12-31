@file:Suppress(
    "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate"
)

package com.oxyggen.qzw.function

import com.oxyggen.qzw.extensions.getUByte
import com.oxyggen.qzw.extensions.putUByte
import com.oxyggen.qzw.frame.FrameSOF
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializer
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializerContext
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID
import com.oxyggen.qzw.utils.BitmaskUtils
import java.io.InputStream
import java.io.OutputStream

abstract class FunctionSerialApiGetCapabilities {
    companion object : BinaryFunctionDeserializer {

        override fun getHandledSignatureBytes(): Set<Byte> = setOf(FunctionID.SERIAL_API_GET_CAPABILITIES.byteValue)

        @ExperimentalUnsignedTypes
        override fun deserialize(
            inputStream: InputStream,
            context: BinaryFunctionDeserializerContext
        ): Function = when (context.frameType) {
            FrameType.REQUEST -> Request()
            FrameType.RESPONSE -> {
                val serialApplVersion = inputStream.getUByte()
                val serialApplRevision = inputStream.getUByte()
                val serialManufId1 = inputStream.getUByte()
                val serialManufId2 = inputStream.getUByte()
                val serialManufProdType1 = inputStream.getUByte()
                val serialManufProdType2 = inputStream.getUByte()
                val serialManufProdId1 = inputStream.getUByte()
                val serialManufProdId2 = inputStream.getUByte()
                val supportedFuncBitmask = inputStream.readAllBytes()
                val supportedFuncBytes = BitmaskUtils.decompressBitmaskToByteSet(supportedFuncBitmask)
                val supportedFunctionId = mutableSetOf<FunctionID>()
                supportedFuncBytes.forEach {
                    val functionId = FunctionID.getByByteValue(it)
                    if (functionId != null) supportedFunctionId.add(functionId)
                }

                Response(
                    serialApplVersion,
                    serialApplRevision,
                    serialManufId1,
                    serialManufId2,
                    serialManufProdType1,
                    serialManufProdType2,
                    serialManufProdId1,
                    serialManufProdId2,
                    supportedFunctionId
                )
            }
        }


    }

    class Request : FunctionRequest(FunctionID.SERIAL_API_GET_CAPABILITIES)

    class Response(
        val serialApplVersion: UByte,
        val serialApplRevision: UByte,
        val serialManufId1: UByte,
        val serialManufId2: UByte,
        val serialManufProdType1: UByte,
        val serialManufProdType2: UByte,
        val serialManufProdId1: UByte,
        val serialManufProdId2: UByte,
        val supportedFunctionId: Set<FunctionID>
    ) : FunctionResponse(FunctionID.SERIAL_API_GET_CAPABILITIES) {

        override fun serialize(outputStream: OutputStream, frame: FrameSOF) {
            super.serialize(outputStream, frame)
            outputStream.putUByte(serialApplVersion)
            outputStream.putUByte(serialApplRevision)
            outputStream.putUByte(serialManufId1)
            outputStream.putUByte(serialManufId2)
            outputStream.putUByte(serialManufProdType1)
            outputStream.putUByte(serialManufProdType2)
            outputStream.putUByte(serialManufProdId1)
            outputStream.putUByte(serialManufProdId2)
            val supportedFuncBytes = mutableSetOf<UByte>()
            supportedFunctionId.forEach {
                supportedFuncBytes += it.byteValue.toUByte()
            }
            val supportedFuncBitmask = BitmaskUtils.compressUByteSetToBitmask(supportedFuncBytes)
            outputStream.write(supportedFuncBitmask)
        }


        override fun toString(): String = "SERIAL_API_GET_CAPABILITIES(appl: $serialApplVersion.$serialApplRevision, " +
                "manuf: $serialManufId1/$serialManufId2, " +
                "prod type: $serialManufProdType1/$serialManufProdType2, " +
                "prod id: $serialManufProdId1/$serialManufProdId2, " +
                "supported func: ${supportedFunctionId.toList()}"
    }
}