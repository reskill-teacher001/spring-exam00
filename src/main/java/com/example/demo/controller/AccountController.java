package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {
	
	@Autowired
	HttpSession session;

	@Autowired
	Account account;
	
	@Autowired
	UserRepository userRepository;

	@GetMapping({"/login", "/logout"})
	public String index() {
		session.invalidate();
		
		return "login";
	}

	@PostMapping("/login")
	public String login(
			@RequestParam(name="email", required=false) String email,
			@RequestParam(name="password", required=false) String password,
			Model model
	) {
		User user = null;
		
		Optional<User> record = userRepository.findByEmailAndPassword(email, password);
		
		if (record.isEmpty() == false) {
			user = record.get();
		}
		
		if (user == null) {
			model.addAttribute("error", "メールアドレスとパスワードが一致しませんでした。");
			
			return "login";
		}
		
		String name = user.getName();
		account.setName(name);
		
		return "redirect:/users";
	}
}
