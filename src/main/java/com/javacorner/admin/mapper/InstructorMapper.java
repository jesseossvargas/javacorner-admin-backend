package com.javacorner.admin.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.javacorner.admin.dto.InstructorDTO;
import com.javacorner.admin.entity.Instructor;

@Service
public class InstructorMapper {

	public InstructorDTO fromInstructor(Instructor instructor) {
		InstructorDTO instructorDTO = new InstructorDTO();
		BeanUtils.copyProperties(instructor, instructorDTO);
		return instructorDTO;
	}
	
	public Instructor fromInstructorDTO(InstructorDTO instructorDTO) {
		Instructor instructor = new Instructor();
		BeanUtils.copyProperties(instructorDTO, instructor);
		return instructor;
	}
	
}
