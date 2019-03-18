package xyz.seecret.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class DemoApplicationTests {

	public static void main(String[] args) {
		WebClient client = WebClient.create("http://localhost:8080/hello_world");
		Mono<ClientResponse> mono = client.get().exchange();
		mono.doOnNext((response)->{
			System.out.println(response.statusCode());
		});
	}

}
