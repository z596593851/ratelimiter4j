package com.eudemon.ratelimiter.algorithm;

import com.eudemon.ratelimiter.exception.InternalErrorException;

/**
 * 限流算法接口
 */
public interface RateLimiter {

  /**
   * try to acquire an access token.
   * 
   * @return true if get an access token successfully, otherwise, return false.
   * @throws InternalErrorException if some internal error occurs.
   */
  boolean tryAcquire() throws InternalErrorException;

}
