package com.milind.ssl;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
public class HelloController {


    @RequestMapping(value = "/postjson", method = RequestMethod.POST)
    public String postjson(@RequestBody String json) throws IOException {

        System.out.println("Request: "+json);
        try {
            Random ran = new Random();
            int x = ran.nextInt(6) + 5;
            TimeUnit.SECONDS.sleep(x);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Received:"+json;
    }

}