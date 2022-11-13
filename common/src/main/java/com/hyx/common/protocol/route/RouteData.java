package com.hyx.common.protocol.route;

import lombok.Data;

import java.util.List;

/**
 * 路由类.
 *
 * @author hyx
 **/

@Data
public class RouteData {
    private String topicName;
    
    private List<QueueData> topicQueueList;
}
