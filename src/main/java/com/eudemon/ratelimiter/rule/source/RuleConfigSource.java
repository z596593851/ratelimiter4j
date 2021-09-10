package com.eudemon.ratelimiter.rule.source;

/**
 * 配置解析器，可以以file、zk等方式进行解析
 */
public interface RuleConfigSource {

  UniformRuleConfigMapping load();

}
