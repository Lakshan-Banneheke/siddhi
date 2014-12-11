/*
 * Copyright (c) 2005 - 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.wso2.siddhi.core.event.state;

import com.lmax.disruptor.EventFactory;

/**
 * Event Factory to create new StateEvents
 */
public class StateEventFactory implements EventFactory<StateEvent> {

    private int eventSize;
    private int preOutputDataSize;
    private int outputDataSize;

    public StateEventFactory(int eventSize, int preOutputDataSize, int outputDataSize) {
        this.eventSize = eventSize;
        this.preOutputDataSize = preOutputDataSize;
        this.outputDataSize = outputDataSize;
    }

    public StateEvent newInstance() {
        return new StateEvent(eventSize, preOutputDataSize, outputDataSize);
    }

}