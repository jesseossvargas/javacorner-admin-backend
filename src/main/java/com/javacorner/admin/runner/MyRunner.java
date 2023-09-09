package com.javacorner.admin.runner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.javacorner.admin.dto.CourseDTO;
import com.javacorner.admin.dto.InstructorDTO;
import com.javacorner.admin.dto.StudentDTO;
import com.javacorner.admin.dto.UserDTO;
import com.javacorner.admin.service.CourseService;
import com.javacorner.admin.service.InstructorService;
import com.javacorner.admin.service.RoleService;
import com.javacorner.admin.service.StudentService;
import com.javacorner.admin.service.UserService;

@Component
public class MyRunner implements CommandLineRunner{

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private InstructorService instructorService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private StudentService studentService;
	
	@Override
	public void run(String... args) throws Exception {
		createRoles();
		createAdmin();
		createInstructors();
		createCourses();
		StudentDTO student = createStudent();
		assignCourseToStudent(student);
		createStudents();
	}

	private void createAdmin() {
		userService.createUser("admin@gmail.com", "1234");
		userService.assignRoleToUser("admin@gmail.com", "Admin");
		
	}

	private void createRoles() {
		Arrays.asList("Admin", "Instructor", "Student").forEach(roleService::createRole);
	}
	
	private void createInstructors() {
		for(int i=0; i<10; i++) {
			InstructorDTO instructorDTO = new InstructorDTO();
			instructorDTO.setFirstName("instructor"+i+"FN");
			instructorDTO.setLastName("instructor"+i+"LN");
			instructorDTO.setSummary("master"+i);
			UserDTO userDTO = new UserDTO();
			userDTO.setEmail("instructor"+i+"@gmail.com");
			userDTO.setPassword("1234");
			instructorDTO.setUser(userDTO);
			instructorService.createInstructor(instructorDTO);
		}
	}
	
	private void createCourses() {
		for(int i=0; i<20; i++) {
			CourseDTO courseDTO = new CourseDTO();
			courseDTO.setCourseDescription("Java"+i);
			courseDTO.setCourseDuration(i+"Hours");
			courseDTO.setCourseName("Java Course"+i);
			InstructorDTO instructorDTO = new InstructorDTO();
			instructorDTO.setInstructorId(1L);
			courseDTO.setInstructor(instructorDTO);
			courseService.createCourse(courseDTO);
		}
	}
	

	private void createStudents() {
		for(int i=0; i<10; i++) {
			StudentDTO studentDTO = new StudentDTO();
			studentDTO.setFirstName("studentFN"+i);
			studentDTO.setLastName("studentLN"+i);
			studentDTO.setLevel("intermediate");
			UserDTO userDTO = new UserDTO();
			userDTO.setEmail("student"+i+"@gmail.com");
			userDTO.setPassword("1234");
			studentDTO.setUser(userDTO);
			studentService.createStudent(studentDTO);
		}
	}
	
	private StudentDTO createStudent() {
		StudentDTO studentDTO = new StudentDTO();
		studentDTO.setFirstName("studentFN");
		studentDTO.setLastName("studentLN");
		studentDTO.setLevel("intermediate");
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("student@gmail.com");
		userDTO.setPassword("1234");
		studentDTO.setUser(userDTO);
		return studentService.createStudent(studentDTO);
	}
	
	private void assignCourseToStudent(StudentDTO studentDTO) {
		courseService.assignStudentToCourse(1L, studentDTO.getStudentId());
	}
	
}
