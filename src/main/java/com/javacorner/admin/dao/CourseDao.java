package com.javacorner.admin.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.javacorner.admin.entity.Course;

public interface CourseDao extends JpaRepository<Course, Long>{
	
	
	Page<Course> findCoursesByCourseNameContains(String keyword, Pageable pageable);
	
	@Query(value = "select * from courses where course_id in (select e.course_id from enrolled_in as e where e.student_id=:studentId)", nativeQuery = true)
	Page<Course> getCoursesByStudentId(@Param("studentId") Long studentId, Pageable pageable);
	
	@Query(value = "SELECT * FROM courses WHERE course_id NOT IN (SELECT e.course_id FROM enrolled_in e WHERE e.student_id = :studentId)", nativeQuery = true)
	Page<Course> getNonEnrrolledInCoursesByStudentId(@Param("studentId") Long studentId, Pageable pageable);

	@Query(value = "select c from Course as c where c.instructor.instructorId=:instructorId")
	Page<Course> getCoursesByInstructorId(@Param("instructorId") Long instructorId, Pageable pageable);
}
