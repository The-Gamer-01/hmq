package com.hyx.common.protocol.route;

import lombok.Data;

/**
 * 队列信息.
 *
 * @author hyx
 **/

@Data
public class QueueData {
    private String brokerName;
    
    private int readQueueNums;
    
    private int writeQueueNums;
}
