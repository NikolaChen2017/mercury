package org.platformlambda.spring;

import org.platformlambda.core.exception.AppException;
import org.platformlambda.core.util.models.PoJo;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MockHelloSpring {

    @GetMapping("/pojo")
    public PoJo pojo(@RequestParam(value = "name", defaultValue = "hello") String name)
            throws AppException, IOException {
        if ("exception".equals(name)) {
            throw new IllegalArgumentException("IllegalArgumentException");
        }
        if ("null".equals(name)) {
            throw new NullPointerException();
        }
        if ("app_exception".equals(name)) {
            throw new AppException(401, "AppException");
        }
        if ("io_exception".equals(name)) {
            throw new IOException("IOException");
        }
        PoJo result = new PoJo();
        result.setName(name);
        result.setAddress("100 World Blvd, Earth");
        result.setNumber(101);
        return result;
    }

    @GetMapping("/hello/html")
    public String hello(@RequestParam(value = "name", defaultValue = "hello") String name) {
        return "<html><body><div>"+name+"</div></body></html>";
    }

    @GetMapping(value = "/hello/list",
            produces = {"application/xml"})
    public Object helloList() {
        List<String> result = new ArrayList<>();
        result.add("one");
        result.add("two");
        result.add("three");
        return result;
    }

    @GetMapping(value = "/hello/map",
    produces = {"application/json", "application/xml", "text/html"})
    public Map<String, Object> helloMap(@RequestParam(value = "name", defaultValue = "hello") String name) {
        Map<String, Object> result = new HashMap<>();
        result.put("name", name);
        return result;
    }

    @PostMapping(value = "/hello/map",
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml", "text/html", "text/plain"})
    public Map<String, Object> saveHelloMap(@RequestBody Map<String, Object> data) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        return result;
    }

    @PostMapping(value = "/hello/text",
            consumes = {"text/html", "text/plain"},
            produces = {"application/json", "application/xml", "text/html", "text/plain"})
    public Map<String, Object> saveHelloText(@RequestBody String data) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        return result;
    }

}
