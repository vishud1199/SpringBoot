package com.vintyaa.SpringBootCrud.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vintyaa.SpringBootCrud.entity.Student;
import com.vintyaa.SpringBootCrud.repository.StudentRepository;

@Controller
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentRepository studentRepository; 	
	
	@RequestMapping("showForm")
	public String showStudentFrom(Model model, Student student){
		
		model.addAttribute("student",new Student());
				return "add-student";
	}
	
	
	@GetMapping("list")
	public String students(Model model){
		
		model.addAttribute("students",this.studentRepository.findAll());
		return "index";
		
	}
	
	//@GetMapping("add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addStudent(@Valid Student student,BindingResult result,Model model){
		
		if(result.hasErrors()){
			return "add-student";
		}
		this.studentRepository.save(student);
		return "redirect:list";
	}
	
	@GetMapping("edit/{id}")
	public String showUpdateForm(@PathVariable ("id") long id,Model model){
		Student student=this.studentRepository.findById(id).
				 orElseThrow(()->new IllegalArgumentException("invalid student"+id ));
				 
		model.addAttribute("student", student);
		return "update-student";
	}
	
	//@PostMapping("update/{id}")
	@RequestMapping(value = "update/{id}", method = RequestMethod.POST)
	public String updateStudent(@PathVariable("id") long id,@Valid Student student,BindingResult result,Model models){
		if(result.hasErrors()){
			student.setId(id);
			return "update-student";
		}
		
		studentRepository.save(student);
		return "index";
	}
	
	@GetMapping("delete/{id}")
	public String deleteStudent(@PathVariable("id") long id,Model model ){
		
		Student student=this.studentRepository.findById(id).
				 orElseThrow(()->new IllegalArgumentException("invalid student"+id ));
	
		this.studentRepository.delete(student);
		
		model.addAttribute("student",this.studentRepository.findAll() );
		
		return "index"; 
	}
	
}
