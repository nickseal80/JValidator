package com.seal.validator.example;

import com.seal.validator.rule.ValidatorResponse;

public class App
{
    public static void main(String[] args) {
        Request request = new Request("S", "example@example.com");


        try {
            ValidatorResponse resp = request.validate(request);
            System.out.printf(String.valueOf(resp.getStatusCode()));
            System.out.printf(resp.getErrorList().toString());

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
