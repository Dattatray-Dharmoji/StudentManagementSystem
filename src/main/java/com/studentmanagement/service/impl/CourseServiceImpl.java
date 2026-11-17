package com.studentmanagement.service.impl;

import com.studentmanagement.dto.CourseDTO;
import com.studentmanagement.exception.GlobalExceptionHandler;
import com.studentmanagement.model.Courses;
import com.studentmanagement.repository.CourseRepo;
import com.studentmanagement.service.CourseService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.modelmapper.Converters.Collection.map;


@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);
 private final CourseRepo courseRepo;
 private final ModelMapper mapper;


    CourseServiceImpl(CourseRepo courseRepo , ModelMapper mapper) {
        this.courseRepo = courseRepo;
        this.mapper = mapper;

    }

    @Override
    public CourseDTO CreateCourse(CourseDTO courseDTO){
        Courses courses = this.mapper.map(courseDTO, Courses.class);
        courseRepo.save(courses);

        log.info("Courses Saved");
        return mapper.map(courses, CourseDTO.class);
    }

    @Override
    public boolean existsByCourseCode(String code) {

        log.info("Checking if course code exists : {}", code);


      return   courseRepo.existsByCourseCodeIgnoreCase(code);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseDTO> getCourses(int page, int size) {
        log.info("List of course from {}", page);
     PageRequest Page=  PageRequest.of(page, size , Sort.by(Sort.Direction.DESC, "id"));
    return    courseRepo.findByActiveTrue(Page)
        .map(courses -> mapper.map(courses, CourseDTO.class));

    }
    @Override
    @Transactional(readOnly = true)
    public CourseDTO getCourseById(Long id) {

        Courses course = courseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        return mapper.map(course, CourseDTO.class);
    }


    // update course logic will be returned here
    @Override
    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        Courses course = courseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));


mapper.map(courseDTO, course) ;
Courses updated=courseRepo.save(course);

return  mapper.map(updated, CourseDTO.class);

    }

    @Override
    public boolean existsByCourseCodeAndIdNot(String code, Long id) {
return  courseRepo.existsByCourseCodeAndIdNot(code, id);
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepo.findByActiveTrue(Sort.by("courseName")).stream()
                .map(course -> mapper.map(course, CourseDTO.class))
                .collect(Collectors.toList());
    }
}
