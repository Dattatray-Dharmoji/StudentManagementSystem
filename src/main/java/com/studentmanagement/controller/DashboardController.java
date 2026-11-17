
package com.studentmanagement.controller;

import com.studentmanagement.service.DashboardService;
import com.studentmanagement.service.EnrollmentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private static final Logger log =
            LoggerFactory.getLogger(DashboardController.class);

    private final EnrollmentService enrollmentService;
    private final DashboardService dashboardService;

    public DashboardController(
            EnrollmentService enrollmentService,
            DashboardService dashboardService) {

        this.enrollmentService = enrollmentService;
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        log.info("Loading dashboard");

        model.addAttribute(
                "dashboardStats",
                dashboardService.getDashboardStats()
        );

        model.addAttribute(
                "students",
                enrollmentService.getRecentlyEnrolledStudents()
        );

        return "dashboard";
    }
}
