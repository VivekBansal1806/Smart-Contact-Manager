package org.scm.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scm")
public class HealthCheck {

    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }
}
