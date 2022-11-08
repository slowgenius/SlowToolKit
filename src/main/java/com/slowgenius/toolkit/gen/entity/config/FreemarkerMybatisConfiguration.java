package com.slowgenius.toolkit.gen.entity.config;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;

/**
 * @author slowgenius
 * @version SlowToolkit
 * @since 2022/7/18 21:23:58
 */

public class FreemarkerMybatisConfiguration extends Configuration {

    public FreemarkerMybatisConfiguration(String basePackagePath) {
        super(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        setDefaultEncoding("UTF-8");
        setClassForTemplateLoading(getClass(), basePackagePath);
    }

    public Template getTemplate(String ftl) throws IOException {
        return this.getTemplate(ftl, "UTF-8");
    }
}
