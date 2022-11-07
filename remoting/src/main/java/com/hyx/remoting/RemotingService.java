package com.hyx.remoting;

/**
 * 传输服务接口.
 *
 * @author hyx
 **/

public interface RemotingService {
    
    /**
     * 服务开始.
     */
    void start();
    
    /**
     * 服务关闭.
     */
    void shutdown();
}
