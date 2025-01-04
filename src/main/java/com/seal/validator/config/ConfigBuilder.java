package com.seal.validator.config;

import com.seal.validator.exception.AnnotationContainsException;
import com.seal.validator.exception.ConfigurationException;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.*;

public class ConfigBuilder implements Configuration
{
    private String packageName;
    private String mode = Configuration.MODE_PRODUCTION;

    @Override
    public ConfigBuilder forPackage(String packageName) {
        this.packageName = packageName;
        return this;
    }

    @Override
    public ConfigBuilder setMode(String mode) {
        this.mode = mode;
        return this;
    }

    @Override
    public void build() throws ConfigurationException {
        if (packageName == null) {
            throw new ConfigurationException("Package name cannot be null");
        }

        if (!Objects.equals(mode, Configuration.MODE_PRODUCTION) && !Objects.equals(mode, Configuration.MODE_DEBUG)) {
            throw new ConfigurationException("Mode must be 'production' or 'debug'");
        }
    }

    @Override
    public String getMode() {
        return mode;
    }

    @Override
    public String getPackage() {
        return packageName;
    }
}
