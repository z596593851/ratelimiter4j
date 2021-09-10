package com.eudemon.ratelimiter.rule;

import java.util.List;

import com.eudemon.ratelimiter.exception.InvalidUrlException;
import com.eudemon.ratelimiter.rule.source.UniformRuleConfigMapping;

/**
 * 给app提供限流配置的CRUD操作，内部使用trie tree存储每个app的限流设置
 */
public interface RateLimitRule {

  /**
   * get limit info of one url.
   * 
   * @param appId
   * @param api
   * @return
   * @throws InvalidUrlException 
   */
  ApiLimit getLimit(String appId, String api) throws InvalidUrlException;

  /**
   * add one limit for one app.
   * 
   * @param appId
   * @param apiLimit
   * @throws InvalidUrlException 
   */
  void addLimit(String appId, ApiLimit apiLimit) throws InvalidUrlException;

  /**
   * add limits for one app.
   * 
   * @param limits
   * @throws InvalidUrlException 
   */
  void addLimits(String appId, List<ApiLimit> limits) throws InvalidUrlException;

  /**
   * override old rule.
   * 
   * @param uniformRuleConfigMapping
   */
  void rebuildRule(UniformRuleConfigMapping uniformRuleConfigMapping);

  /**
   * add rule into the existing rule.
   * 
   * @param uniformRuleConfigMapping
   */
  void addRule(UniformRuleConfigMapping uniformRuleConfigMapping);

}
