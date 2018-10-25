package xyz.seecret.demo;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liuzz on 2018/10/22 9:57 AM.
 */
@RestController
@RequestMapping("test")
public class TestController {
    public TestController(){
        //new Throwable().printStackTrace();
    }
    @RequestMapping("/{param1}")
    public String test(@PathVariable("param1") String param1){
        return param1;

    }
    /*@RequestMapping("/{param2}")
    public String test2(@PathVariable("param2") String param2){
        return param2;

    }*/
}
