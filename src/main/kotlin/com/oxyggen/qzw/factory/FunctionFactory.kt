package com.oxyggen.qzw.factory

import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.function.*
import com.oxyggen.qzw.function.Function
import com.oxyggen.qzw.serialization.BinaryDeserializerHandler
import com.oxyggen.qzw.serialization.BinaryFrameDeserializerContext
import com.oxyggen.qzw.serialization.BinaryFunctionDeserializerContext
import com.oxyggen.qzw.types.FrameType
import org.apache.logging.log4j.kotlin.Logging
import java.io.InputStream

class FunctionFactory {
    companion object : Logging {
        private val bdh by lazy {
            BinaryDeserializerHandler<Function, BinaryFunctionDeserializerContext>(
                objectDescription = "function",
                FunctionSerialApiGetInitData::class,
//               FunctionSerialApiApplNodeInformation::class,
//               FunctionApplicationCommandHandler::class,
//               FunctionZWGetControllerCapabilities::class,
//               FunctionSerialApiSetTimeouts::class,
               FunctionSerialApiGetCapabilities::class,
//               FunctionSerialApiSoftReset::class,
//               FunctionSerialApiSetup::class,
//               FunctionZWSendNodeInformation::class,
//               FunctionZWSendData::class,
                FunctionZWGetVersion::class,
//               FunctionZWRFPowerLevelSet::class,
               FunctionZWGetRandom::class,
//               FunctionZWMemoryGetId::class,
//               FunctionMemoryGetByte::class,
//               FunctionZWReadMemory::class,
//               FunctionZWSetLearnNodeState::class,
//               FunctionZWGetNodeProtocolInfo::class,
//               FunctionZWSetDefault::class,
//               FunctionZWNewController::class,
//               FunctionZWReplicationCommandComplete::class,
//               FunctionZWReplicationSendData::class,
//               FunctionZWAssignReturnRoute::class,
//               FunctionZWDeleteReturnRoute::class,
//               FunctionZWRequestNodeNeighborUpdate::class,
                FunctionZWApplicationUpdate::class,
//                FunctionZWAddNodeToNetwork::class,
//                FunctionZWRemoveNodeFromNetwork::class,
//                FunctionZWCreateNewPrimary::class,
//                FunctionZWControllerChange::class,
//                FunctionZWSetLearnMode::class,
//                FunctionZWAssignSucReturnRoute::class,
//                FunctionZWEnableSuc::class,
//                FunctionZWRequestNetworkUpdate::class,
//                FunctionZWSetSucNodeId::class,
//                FunctionZWDeleteSucReturnRoute::class,
//                FunctionZWGetSucNodeId::class,
//                FunctionZWRequestNodeNeighborUpdateOptions::class,
//                FunctionZWExploreRequestInclusion::class,
//                FunctionZWRequestNodeInfo::class,
//                FunctionZWRemoveFailedNodeId::class,
//                FunctionZWIsFailedNodeId::class,
//                FunctionZWReplaceFailedNode::class,
//                FunctionZWGetRoutingInfo::class,
//                FunctionSerialApiSlaveNodeInfo::class,
//                FunctionApplicationSlaveCommandHandler::class,
//                FunctionZWSendSlaveNodeInfo::class,
//                FunctionZWSendSlaveData::class,
//                FunctionZWSetSlaveLearnMode::class,
//                FunctionZWGetVirtualNodes::class,
//                FunctionZWIsVirtualNode::class,
//                FunctionZWSetPromiscuousMode::class,
//                FunctionPromiscuousApplicationCommandHandler::class,
                FunctionProprietaryAny::class
            )
        }

        fun deserializeFunction(
            inputStream: InputStream,
            frameContext: BinaryFrameDeserializerContext,
            frameType: FrameType
        ): Function {
            val signatureByte = inputStream.getByte()
            val context = BinaryFunctionDeserializerContext(signatureByte, frameContext, frameType)
            return bdh.deserialize(inputStream, context)
        }

    }
}