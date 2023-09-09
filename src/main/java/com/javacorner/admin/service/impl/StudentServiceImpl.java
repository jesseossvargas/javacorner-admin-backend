package com.javacorner.admin.service.impl;

import java.util.Iterator;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javacorner.admin.dao.StudentDao;
import com.javacorner.admin.dto.StudentDTO;
import com.javacorner.admin.entity.Course;
import com.javacorner.admin.entity.Student;
import com.javacorner.admin.entity.User;
import com.javacorner.admin.mapper.StudentMapper;
import com.javacorner.admin.service.StudentService;
import com.javacorner.admin.service.UserService;

@Service
@Transactional
public class StudentServiceImpl implements StudentService{

	private StudentDao studentDao;
	
	private StudentMapper studentMapper;	
	
	private UserService userService;
	
	public StudentServiceImpl(StudentDao studentDao, StudentMapper studentMapper, UserService userService) {
		super();
		this.studentDao = studentDao;
		this.studentMapper = studentMapper;
		this.userService = userService;
	}

	@Override
	public Student loadStudentById(Long studentId) {
		return studentDao.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student With Id " + studentId + "Not Found" ));
		
	}

	@Override
	public Page<StudentDTO> loadStudentsByName(String name, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Student> studentsPage = studentDao.findStudentsByName(name, pageRequest);
		return new PageImpl<>(studentsPage.stream().map(student -> studentMapper.fromStudent(student)).collect(Collectors.toList()), pageRequest, studentsPage.getTotalElements());
	}

	@Override
	public StudentDTO loadStudentByEmail(String email) {
		return studentMapper.fromStudent(studentDao.findStudentByEmail(email));
	}

	@Override
	public StudentDTO createStudent(StudentDTO studentDTO) {
		User user = userService.createUser(studentDTO.getUser().getEmail(), studentDTO.getUser().getPassword());
		userService.assignRoleToUser(user.getEmail(), "Student");
		Student student = studentMapper.fromStudentDTO(studentDTO);
		student.setUser(user);
		return studentMapper.fromStudent(studentDao.save(student));
	}
	
	@Override
	public StudentDTO updateStudent(StudentDTO studentDTO) {
		Student loadedStudend = loadStudentById(studentDTO.getStudentId());
		Student student = studentMapper.fromStudentDTO(studentDTO);
		student.setUser(loadedStudend.getUser());
		student.setCourses(loadedStudend.getCourses());
		return studentMapper.fromStudent(studentDao.save(student));
	}
	
	@Override
	public void removeStudent(Long studentId) {
		Student student = loadStudentById(studentId);
		Iterator<Course> courseIterator = student.getCourses().iterator();
		if(courseIterator.hasNext()) {
			Course course = courseIterator.next();
			course.removeStudentFromCourse(student);
		}
		studentDao.deleteById(studentId);
	}

}
