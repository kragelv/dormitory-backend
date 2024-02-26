package by.bsuir.dorm.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Controller;
import org.springframework.graphql.data.method.annotation.QueryMapping;

@Controller
public class AuthController {

    @QueryMapping
    public String greeting(@Argument String name) {
        return "Hello, " + name;
    }
}
