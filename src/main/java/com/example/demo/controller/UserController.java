package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

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
public class UserController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	HttpSession session;
	@Autowired
	Account account;
	//ログイン画面兼セッションリセット
	@GetMapping({"/","/login"})
	public String index(Model model) {
		session.invalidate();
		String email="";
		model.addAttribute("email",email);
		return "user";
	}
	//ユーザー新規登録画面
	@GetMapping("/users/new")
	public String newUser(Model model) {
		User user1=new User("","","");
		model.addAttribute("user1",user1);
		return "userAdd";
	}
	//ログアウト画面
	@GetMapping("/users/logout")
	public String logout(){
		return "logout";
	}
	//ホームに戻る
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	@GetMapping("/users/view")
	public String usersEdit(Model model) {
		Integer id=account.getId();
		User user=userRepository.findById(id).get();
		model.addAttribute(user);
		return "userEdit";
	}
	@GetMapping("/users/delete")
	public String delete() {
		userRepository.deleteById(account.getId());
		return "redirect:/login";
	}
	
	
	//ユーザー追加処理
	@PostMapping("/users/add")
	public String addUser(
			@RequestParam(name="name",defaultValue="")String name,
			@RequestParam(name="email",defaultValue="")String email,
			@RequestParam(name="password",defaultValue="")String password,
			Model model) {
		List<String>errorList=new ArrayList<String>();
		User user1=new User(name,email,password);
		List<User>user=userRepository.findByEmailLike(email);
		if(name.length()==0||name.equals(null)) {
			errorList.add("名前を入力してください");
		}
		if(email.length()==0||email.equals(null)) {
			errorList.add("メールアドレスを入力してください");
		}
		if(user.size()>0) {
			errorList.add("メールアドレスは登録済みです");
		}
		if(password.length()==0||password.equals(null)) {
			errorList.add("パスワードを入力してください");
		}
		if(name.length()>20) {
			errorList.add("名前は20文字以内で入力してください");
		}
		
		if(errorList.size()>0) {
			model.addAttribute("error",errorList);
			model.addAttribute("user1",user1);
			return "userAdd";
		}else {
			userRepository.save(user1);
			return "user";
		}
	}
	
	//ログイン処理
	@PostMapping("/login")
	public String login(
			@RequestParam("email")String email,
			@RequestParam("password")String password,
			Model model) {
		List<User>user=userRepository.findByEmailLike(email);
		
		List<String>errorList=new ArrayList<String>();
		
		
		
		if(email.length()==0||email.equals(null)) {
			errorList.add("メールアドレスを入力してください");
		}else if(user.size()<1) {
			errorList.add("メールアドレスが見つかりません");
		}
		
		if(password.length()==0||password.equals(null)) {
			errorList.add("パスワードを入力してください");
		}
		
		if(errorList.size()>0) {
			model.addAttribute("email",email);
			model.addAttribute("error",errorList);
			return "user";
		}
		User userA=user.get(0);
		if(userA.getPassword().equals(password)) {
			account.setId(userA.getId());
			account.setName(userA.getName());
			account.setEmail(email);
			return "home";
			}else {
				errorList.add("パスワードが一致しません");
			}
		
		model.addAttribute("email",email);
		model.addAttribute("error",errorList);
		return "user";
	}
	@PostMapping("/users/update")
		public String userUpdate(
				@RequestParam(name="name",defaultValue="")String name,
				@RequestParam(name="email",defaultValue="")String email,
				@RequestParam(name="password",defaultValue="")String password,
				Model model) {
		Integer id=account.getId();
		List<String>errorList=new ArrayList<String>();
		User user=new User(id,name,email,password);
		
		
		List<User>userA=userRepository.findByEmailLike(email);
		if(name.length()==0||name.equals(null)) {
			errorList.add("名前を入力してください");
		}
		if(email.length()==0||email.equals(null)) {
			errorList.add("メールアドレスを入力してください");
		}
		if(!(account.getEmail().equals(email))) {
			if(userA.size()>0) {
				errorList.add("メールアドレスは登録済みです");
			}
		}
		if(password.length()==0||password.equals(null)) {
			errorList.add("パスワードを入力してください");
		}
		if(name.length()>20) {
			errorList.add("名前は20文字以内で入力してください");
		}
		
		if(errorList.size()>0) {
			model.addAttribute("error",errorList);
			model.addAttribute("user",user);
			return "userEdit";
		}else {
			account.setId(user.getId());
			account.setName(user.getName());
			account.setEmail(user.getEmail());
			userRepository.save(user);
			
			return "redirect:/home";
		}
	
		
		
	}

}
