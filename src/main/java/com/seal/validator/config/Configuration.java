package com.seal.validator.config;

import com.seal.validator.exception.ConfigurationException;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.List;

public interface Configuration
{
    String MODE_PRODUCTION = "production";
    String MODE_DEBUG = "debug";

    ConfigBuilder forPackage(String packageName);

    ConfigBuilder setMode(String mode);

    void build() throws ConfigurationException;

    String getMode();

    String getPackage();
}
