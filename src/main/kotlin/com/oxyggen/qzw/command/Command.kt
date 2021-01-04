package com.oxyggen.qzw.command

import com.oxyggen.qzw.types.CommandClassID
import com.oxyggen.qzw.types.CommandID

abstract class Command(val commandClassId: CommandClassID, val commandId: CommandID)