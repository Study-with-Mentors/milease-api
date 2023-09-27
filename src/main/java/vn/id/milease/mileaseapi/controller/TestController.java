package vn.id.milease.mileaseapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public String test(@Value("${app.version}") String version) {
        return version;
    }

    @PostMapping("/{name}")
    public String hello(@PathVariable String name) {
        return "Hello " + name;
    }
}
