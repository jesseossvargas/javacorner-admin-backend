package com.javacorner.admin.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javacorner.admin.dao.InstructorDao;
import com.javacorner.admin.dto.InstructorDTO;
import com.javacorner.admin.entity.Course;
import com.javacorner.admin.entity.Instructor;
import com.javacorner.admin.entity.User;
import com.javacorner.admin.mapper.InstructorMapper;
import com.javacorner.admin.service.CourseService;
import com.javacorner.admin.service.InstructorService;
import com.javacorner.admin.service.UserService;

@Transactional
@Service
public class InstructorServiceImpl implements InstructorService{

	private InstructorDao instructorDao;
	
	private InstructorMapper instructorMapper;
	
	private UserService userService;
	
	private CourseService courseService;
	
	public InstructorServiceImpl(InstructorDao instructorDao, InstructorMapper instructorMapper, UserService userService, CourseService courseService) {
		super();
		this.instructorDao = instructorDao;
		this.instructorMapper = instructorMapper;
		this.userService = userService;
		this.courseService = courseService;
	}

	@Override
	public Instructor loadInstructorById(Long instructorId) {
		return instructorDao.findById(instructorId).orElseThrow(() -> new EntityNotFoundException("Instructor With Id " + instructorId + "Not Found"));
	}

	@Override
	public Page<InstructorDTO> findInstructorsByName(String name, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Instructor> instructorsPage = instructorDao.findInstructorsByName(name, pageRequest);
		return new PageImpl<>(instructorsPage.stream().map((instructor) -> instructorMapper.fromInstructor(instructor)).collect(Collectors.toList()), pageRequest, instructorsPage.getTotalElements());
	}

	@Override
	public InstructorDTO loadInstructorByEmail(String email) {
		return instructorMapper.fromInstructor(instructorDao.findInstructorByEmail(email));
	}

	@Override
	public InstructorDTO createInstructor(InstructorDTO instructorDTO) {
		User user = userService.createUser(instructorDTO.getUser().getEmail(), instructorDTO.getUser().getPassword());
		userService.assignRoleToUser(user.getEmail(), "Instructor");
		Instructor instructor = instructorMapper.fromInstructorDTO(instructorDTO);
		instructor.setUser(user);
		return instructorMapper.fromInstructor(instructorDao.save(instructor));
	}

	@Override
	public InstructorDTO updateInstructor(InstructorDTO instructorDTO) {
		Instructor loadedInstructor = loadInstructorById(instructorDTO.getInstructorId());
		Instructor instructor = instructorMapper.fromInstructorDTO(instructorDTO);
		instructor.setUser(loadedInstructor.getUser());
		instructor.setCourses(loadedInstructor.getCourses());
		return instructorMapper.fromInstructor(instructorDao.save(instructor)); 
	}

	@Override
	public List<InstructorDTO> fetchInstructors() {
		return instructorDao.findAll().stream().map(instructor -> instructorMapper.fromInstructor(instructor)).collect(Collectors.toList());
	}

	@Override
	public void removeInstructor(Long instructorId) {
		Instructor instructor = loadInstructorById(instructorId);
		for(Course course : instructor.getCourses()) {
			courseService.removeCourse(course.getCourseId());
		}
		instructorDao.deleteById(instructorId);
	}

}
