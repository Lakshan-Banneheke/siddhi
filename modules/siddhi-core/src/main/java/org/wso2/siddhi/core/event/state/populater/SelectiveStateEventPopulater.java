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
package org.wso2.siddhi.core.event.state.populater;

import org.wso2.siddhi.core.event.ComplexEvent;
import org.wso2.siddhi.core.event.state.StateEvent;
import org.wso2.siddhi.core.event.stream.StreamEvent;

import java.util.List;

/**
 * The populater class that populates StateEvents
 */
public class SelectiveStateEventPopulater implements StateEventPopulater {

    private List<MappingElement> mappingElements;       //List to hold information needed for population

    public SelectiveStateEventPopulater(List<MappingElement> mappingElements) {
        this.mappingElements = mappingElements;
    }

    public void convertToStateEvent(ComplexEvent complexEvent) {
        StateEvent stateEvent = (StateEvent) complexEvent;
        for (MappingElement mappingElement : mappingElements) {
            int[] toPosition = mappingElement.getToPosition();
            switch (toPosition[0]) {
                case 0:
                    stateEvent.setPreOutputData(getFromData(stateEvent, mappingElement.getFromPosition()),
                            toPosition[1]);
                    break;
                case 1:
                    stateEvent.setOutputData(getFromData(stateEvent, mappingElement.getFromPosition()),
                            toPosition[1]);
                    break;
                default:
                    //will not happen
                    throw new IllegalStateException("To Position cannot be :" + toPosition[0]);
            }
        }
    }

    private Object getFromData(StateEvent stateEvent, int[] fromPosition) {
        StreamEvent streamEvent = stateEvent.getStreamEvent(fromPosition[0]);
        if (fromPosition[1] > 0) {
            for (int i = 0, size = fromPosition[1]; i < size; i++) {
                streamEvent = streamEvent.getNext();
            }
        }
        switch (fromPosition[2]) {
            case 0:
                return streamEvent.getBeforeWindowData()[fromPosition[3]];
            case 1:
                return streamEvent.getOnAfterWindowData()[fromPosition[3]];
            case 2:
                return streamEvent.getOutputData()[fromPosition[3]];
            default:
                //will not happen
                throw new IllegalStateException("3rd element in from position cannot be :" + fromPosition[2]);
        }
    }

}
