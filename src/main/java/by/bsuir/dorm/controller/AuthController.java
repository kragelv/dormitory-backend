package by.bsuir.dorm.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.graphql.data.method.annotation.QueryMapping;

@Controller
public class AuthController {
    @PreAuthorize("isAuthenticated()")
    @QueryMapping
    public String greeting(@Argument String name) {
        return "Hello, " + name;
    }
}
