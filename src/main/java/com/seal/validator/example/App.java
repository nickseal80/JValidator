package com.seal.validator.example;

import com.seal.validator.Validator;
import com.seal.validator.config.Configuration;
import com.seal.validator.exception.ConfigurationException;

import java.lang.reflect.InvocationTargetException;

public class App
{
    public static void main(String[] args) {
        Request request = new Request("Seal", "example@example.com");


        try {
            request.validate(request);
            // TODO: подумать как инитить конфиг. Должна быть возможность брать из .properties и собирать в рантайме
//            Validator validator = new Validator();
//            Configuration config = validator.getConfig();
//            config
//                    .forPackage("com.seal.validator.example")
//                    .setMode(Configuration.MODE_DEBUG)
//                    .build();

//            validator.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
