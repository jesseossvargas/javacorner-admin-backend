package com.javacorner.admin.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.javacorner.admin.dto.CourseDTO;
import com.javacorner.admin.entity.Course;

@Service
public class CourseMapper {
	
	InstructorMapper instructorMapper;
	
	public CourseMapper(InstructorMapper instructorMapper) {
		this.instructorMapper = instructorMapper;
	}
	
	public CourseDTO fromCourse(Course course) {
		CourseDTO courseDTO = new CourseDTO();
		BeanUtils.copyProperties(course, courseDTO);
		courseDTO.setInstructor(instructorMapper.fromInstructor(course.getInstructor()));
		return courseDTO;
	}
	
	public Course fromCourseDTO(CourseDTO courseDTO) {
		Course course = new Course();
		BeanUtils.copyProperties(courseDTO, course);
		return course;
	}
	
}