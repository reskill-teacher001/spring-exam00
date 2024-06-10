package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Controller
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/users")
	public String index(
			@RequestParam(name="keyword", required=false) String keyword,
			Model model
	) {
		List<User> list = null;
		
		if (keyword == null) {
			list = userRepository.findAll();
		} else {
			list = userRepository.findByNameLike("%" + keyword + "%");
		}
		
		model.addAttribute("list", list);
		model.addAttribute("keyword", keyword != null ? keyword : "");
		
		return "users";
	}
	
	@GetMapping("/users/add")
	public String create() {
		return "addUser";
	}
	
	@PostMapping("/users/add")
	public String store(
			@RequestParam(name="name", required=false) String name,
			@RequestParam(name="email", required=false) String email,
			@RequestParam(name="password", required=false) String password,
			Model model
	) {
		User user = new User(name, email, password);
		
		userRepository.saveAndFlush(user);
		
//		userRepository.regist(name, email, password);
		
		return "redirect:/users";
	}
	
	@GetMapping("/users/{id}/edit")
	public String edit(
			@PathVariable(name="id") Integer id,
			Model model
	) {
		User user = userRepository.findById(id).get();
		
		model.addAttribute("user", user);
		
		return "editUser";
	}
	
	@PostMapping("/users/{id}/edit")
	public String update(
			@PathVariable(name="id") Integer id,
			@RequestParam(name="name", required=false) String name,
			@RequestParam(name="email", required=false) String email,
			@RequestParam(name="password", required=false) String password,
			Model model
	) {
		User user = new User(id, name, email, password);
		
		userRepository.saveAndFlush(user);
		
//		userRepository.update(name, email, password, id);
		
		return "redirect:/users";
	}
	
	@PostMapping("/users/{id}/delete")
	public String delete(
			@PathVariable(name="id") Integer id,
			Model model
	) {
		userRepository.deleteById(id);

//		userRepository.delete(id);

		return "redirect:/users";
	}
	

}
