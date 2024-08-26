package org.example.webfluxstudy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import reactor.core.publisher.Flux;

@RestController
public class TestController {

    private DeferredResult<Flux<String>> deferredResult = new DeferredResult<>();

    @GetMapping("/test1")
    public void test1() {
        Flux<String> flux = Flux.fromArray(new String[]{"123", "213", "12312"});
        deferredResult.setResult(flux);
    }

    @GetMapping("/test2")
    public DeferredResult<Flux<String>> test2() {
        return deferredResult;
    }
}
