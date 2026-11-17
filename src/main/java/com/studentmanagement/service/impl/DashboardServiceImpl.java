
package com.studentmanagement.service.impl;

import com.studentmanagement.dto.DashboardStatsDTO;
import com.studentmanagement.repository.CourseRepo;
import com.studentmanagement.repository.EnrollmentRepo;
import com.studentmanagement.repository.StudentRepo;
import com.studentmanagement.service.DashboardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final EnrollmentRepo enrollmentRepository;
    private final StudentRepo studentRepository;
    private final CourseRepo courseRepository;

    public DashboardServiceImpl(EnrollmentRepo enrollmentRepository,
                                StudentRepo studentRepository,
                                CourseRepo courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public DashboardStatsDTO getDashboardStats() {

        long totalStudents = studentRepository.count();
        long totalCourses = courseRepository.count();

        String topPerformingCourse = getTopPerformingCourse();

        YearMonth currentMonth = YearMonth.now();

        LocalDateTime startDate =
                currentMonth.atDay(1).atStartOfDay();

        LocalDateTime endDate =
                currentMonth.atEndOfMonth().atTime(LocalTime.MAX);

        long studentsEnrolledThisMonth =
                enrollmentRepository.countDistinctStudentByEnrollDateBetween(
                        startDate, endDate);

        DashboardStatsDTO dashboardStatsDTO = new DashboardStatsDTO();

        dashboardStatsDTO.setTotalStudents(totalStudents);
        dashboardStatsDTO.setTotalCourses(totalCourses);
        dashboardStatsDTO.setTopPerformingCourse(topPerformingCourse);
        dashboardStatsDTO.setStudentsEnrolledThisMonth(studentsEnrolledThisMonth);

        return dashboardStatsDTO;
    }

    private String getTopPerformingCourse() {

        return enrollmentRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        e -> e.getCourse().getCourseName(),
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }
}
