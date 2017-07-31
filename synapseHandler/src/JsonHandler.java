/**
 * Created by methma on 7/19/17.
 */

import org.apache.axis2.AxisFault;
import org.apache.axis2.engine.AxisEngine;
import org.apache.axis2.util.MessageContextBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.AbstractSynapseHandler;
import org.apache.synapse.MessageContext;
import org.apache.synapse.SynapseConstants;
import org.apache.synapse.SynapseException;
import org.apache.synapse.commons.json.JsonUtil;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.transport.passthru.PassThroughConstants;
import org.apache.synapse.transport.passthru.util.RelayUtils;
import org.json.JSONObject;
import org.apache.synapse.AbstractSynapseHandler;

public class JsonHandler extends AbstractSynapseHandle {
    private static final Log log = LogFactory.getLog(JsonHandler.class);

    @Override
    public boolean handleRequestInFlow(MessageContext synCtx) {
        log.info("Request In Flow");
        return true;
    }

    @Override
    public boolean handleRequestOutFlow(MessageContext synCtx) {
        log.info("Request Out Flow");
        return true;
    }

    @Override
    public boolean handleResponseInFlow(MessageContext synCtx) {
        log.info("Response In Flow");
        return true;
    }

    @Override
    public boolean handleResponseOutFlow(MessageContext synCtx) {
        log.info("Response Out Flow");
        return true;
    }
}
