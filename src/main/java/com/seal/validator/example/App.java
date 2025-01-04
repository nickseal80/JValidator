package com.seal.validator.example;

import com.seal.validator.Validator;
import com.seal.validator.config.Configuration;
import com.seal.validator.exception.ConfigurationException;

public class App
{
    public static void main(String[] args) {
        Request request = new Request();


        try {
            request.validate(Request.class);
            // TODO: подумать как инитить конфиг. Должна быть возможность брать из .properties и собирать в рантайме
//            Validator validator = new Validator();
//            Configuration config = validator.getConfig();
//            config
//                    .forPackage("com.seal.validator.example")
//                    .setMode(Configuration.MODE_DEBUG)
//                    .build();

//            validator.init();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
}
