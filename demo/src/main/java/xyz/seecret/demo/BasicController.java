package xyz.seecret.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Created by liuzz on 2019-03-14 17:47.
 */
@RestController
public class BasicController {
    @GetMapping("/hello_world/{hello}")
    public Mono<String> sayHelloWorld(@PathVariable("hello") String hello) {
        return Mono.just(hello);
    }
    @GetMapping("/hello_world/hello")
    public Mono<String> sayHello() {
        return Mono.just("Hello World");
    }
}
