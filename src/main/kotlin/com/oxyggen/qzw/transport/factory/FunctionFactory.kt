package com.oxyggen.qzw.transport.factory

import com.oxyggen.qzw.extensions.getByte
import com.oxyggen.qzw.transport.function.*
import com.oxyggen.qzw.transport.function.Function
import com.oxyggen.qzw.transport.serialization.DeserializableHandler
import com.oxyggen.qzw.transport.serialization.DeserializableFrameContext
import com.oxyggen.qzw.transport.serialization.DeserializableFunctionContext
import com.oxyggen.qzw.types.FrameType
import com.oxyggen.qzw.types.FunctionID
import org.apache.logging.log4j.kotlin.Logging
import java.io.IOException
import java.io.InputStream

@OptIn(ExperimentalUnsignedTypes::class)
class FunctionFactory {
    companion object : Logging {
        private val bdh by lazy {
            DeserializableHandler<Function, DeserializableFunctionContext>(
                objectDescription = "function",
                FunctionSerialApiGetInitData::class,
//               FunctionSerialApiApplNodeInformation::class,
                FunctionApplicationCommandHandler::class,
//               FunctionZWGetControllerCapabilities::class,
//               FunctionSerialApiSetTimeouts::class,
                FunctionSerialApiGetCapabilities::class,
//               FunctionSerialApiSoftReset::class,
//               FunctionSerialApiSetup::class,
//               FunctionZWSendNodeInformation::class,
                FunctionZWSendData::class,
                FunctionZWGetVersion::class,
//               FunctionZWRFPowerLevelSet::class,
                FunctionZWGetRandom::class,
//               FunctionZWMemoryGetId::class,
//               FunctionMemoryGetByte::class,
//               FunctionZWReadMemory::class,
//               FunctionZWSetLearnNodeState::class,
                FunctionZWGetNodeProtocolInfo::class,
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
                FunctionZWRequestNodeInfo::class,
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
            frameContext: DeserializableFrameContext,
            frameType: FrameType
        ): Function {
            val signatureByte = inputStream.getByte()
            val functionID = FunctionID.getByByteValue(signatureByte) ?: throw IOException(
                "Unknown function signature byte 0x%02x!".format(signatureByte)
            )

            val context = DeserializableFunctionContext(frameContext, frameType, functionID)
            return bdh.deserialize(inputStream, context)
        }

    }
}