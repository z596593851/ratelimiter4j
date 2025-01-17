package com.eudemon.ratelimiter.rule.source;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eudemon.ratelimiter.extension.Order;
import com.eudemon.ratelimiter.rule.parser.JsonRuleConfigParser;
import com.eudemon.ratelimiter.rule.parser.RuleConfigParser;
import com.eudemon.ratelimiter.rule.parser.YamlRuleConfigParser;

/**
 * 从本地读取限流配置文件，并且使用对应的parser进行解析
 */
@Order(Order.HIGHEST_PRECEDENCE + 10)
public class FileRuleConfigSource implements RuleConfigSource {

  private static final Logger log = LoggerFactory.getLogger(FileRuleConfigSource.class);

  public static final String API_LIMIT_CONFIG_NAME = "ratelimiter-rule";
  public static final String JSON_EXTENSION = "json";
  public static final String YML_EXTENSION = "yml";
  public static final String YAML_EXTENSION = "yaml";

  private static final String[] SUPPORT_EXTENSIONS =
      new String[] {YAML_EXTENSION, YML_EXTENSION, JSON_EXTENSION};
  private static final Map<String, RuleConfigParser> PARSER_MAP = new HashMap<>();

  static {
    PARSER_MAP.put(YAML_EXTENSION, new YamlRuleConfigParser());
    PARSER_MAP.put(YML_EXTENSION, new YamlRuleConfigParser());
    PARSER_MAP.put(JSON_EXTENSION, new JsonRuleConfigParser());
  }

  /**
   * 从本地文件中解析限流配置
   */
  @Override
  public UniformRuleConfigMapping load() {
    for (String extension : SUPPORT_EXTENSIONS) {
      InputStream in = null;
      try {
        in = this.getClass().getResourceAsStream("/" + getFileNameByExt(extension));
        if (in != null) {
          RuleConfigParser parser = PARSER_MAP.get(extension);
          return parser.parse(in);
        }
      } finally {
        if (in != null) {
          try {
            in.close();
          } catch (IOException e) {
            log.error("close file error:{}", e);
          }
        }
      }
    }
    return null;
  }

  private String getFileNameByExt(String extension) {
    return API_LIMIT_CONFIG_NAME + "." + extension;
  }

}
