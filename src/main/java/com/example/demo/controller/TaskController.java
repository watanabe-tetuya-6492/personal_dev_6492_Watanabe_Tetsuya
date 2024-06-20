package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Task;
import com.example.demo.model.Account;
import com.example.demo.model.Calender;
import com.example.demo.model.ToWeek;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TaskRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class TaskController {
	@Autowired
	TaskRepository taskRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	HttpSession session;
	@Autowired
	Account account;
	@GetMapping("/tasks")
	public String index(Model model) {
		List<String>errorList=new ArrayList<>();
		List<Task>taskList=taskRepository.findAllByOrderByIdAsc();
		List<Category>categoryList=categoryRepository.findAll();
		if(taskList.size()==0) {
			errorList.add("タスクがありません。新規登録をお願いいたします。");
			model.addAttribute("categoryList",categoryList);
			model.addAttribute("error",errorList);
			LocalDate date=LocalDate.now();
			model.addAttribute(date);
			return "taskAdd";
		}
		model.addAttribute("taskList",taskList);
		model.addAttribute("categoryList",categoryList);
		return "task";
	}
	@GetMapping("/tasks/{id}")
	public String taskIndex(@PathVariable("id")Integer id,
			Model model) {
		List<String>errorList=new ArrayList<>();
		List<Task>taskList=taskRepository.findByCategoryId(id);
		List<Category>categoryList=categoryRepository.findAll();
		if(taskList.size()==0) {
			errorList.add("タスクがありません。新規登録をお願いいたします。");
			model.addAttribute("error",errorList);
			model.addAttribute("categoryList",categoryList);
			LocalDate date=LocalDate.now();
			model.addAttribute(date);
			return "taskAdd";
		}
		model.addAttribute(taskList);
		model.addAttribute("categoryList",categoryList);
		return "task";
	}
	@GetMapping("/tasks/new")
	public String taskNew(Model model) {
		List<Category>categoryList=categoryRepository.findAll();
		model.addAttribute("categoryList",categoryList);
		LocalDate date=LocalDate.now();
		model.addAttribute(date);
		return "taskAdd";
	}
	@GetMapping("/tasks/{id}/edit")
	public String taskEdit(
			@PathVariable("id")Integer id,
			Model model) {
		Task task=taskRepository.findById(id).get();
		Category category=categoryRepository.findById(task.getCategoryId()).get();
		List<Category>categoryList=categoryRepository.findByNameNotLike(category.getName());
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("category",category);
		model.addAttribute(task);
		model.addAttribute("id",id);
		return "taskEdit";
	}
	@GetMapping("/tasks/{id}/dateedit")
	public String taskdateEdit(
			@PathVariable("id")Integer id,
			Model model) {
		Task task=taskRepository.findById(id).get();
		Category category=categoryRepository.findById(task.getCategoryId()).get();
		List<Category>categoryList=categoryRepository.findByNameNotLike(category.getName());
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("category",category);
		model.addAttribute(task);
		model.addAttribute("id",id);
		return "taskdateEdit";
	}
	@GetMapping("/tasks/{date}/editcalender")
	public String dateTaskList(
			@PathVariable("date")LocalDate date,
			Model model) {
		List<String>errorList=new ArrayList<>();
		List<Task>taskList=taskRepository.findByClosingDateOrderByIdAsc(date);
		List<Category>categoryList=categoryRepository.findAll();
		if(taskList.size()==0) {
			errorList.add("タスクがありません。新規登録をお願いいたします。");
			model.addAttribute("error",errorList);
			model.addAttribute("categoryList",categoryList);
			model.addAttribute("date",date);
			return "taskAdd";
		}
		model.addAttribute("taskList",taskList);
		model.addAttribute("date",date);
		return "dateTaskList";
	}
	
	@GetMapping("/tasks/{id}/increase")
	public String increase(
			@PathVariable("id")Integer id,
			@RequestParam("categoryId")Integer categoryId,
			@RequestParam("userId")Integer userId,
			@RequestParam("title")String title,
			@RequestParam("closingDate")LocalDate closingDate,
			@RequestParam("progress")Integer progress,
			@RequestParam("memo")String memo){
		Integer increasedProgress;
		if(progress<2) {
		increasedProgress =progress+1;
		}else {
			return "redirect:/tasks";
		}
		Task upgradeTask=new Task(
				id,
				categoryId,
				userId,
				title,
				closingDate,
				increasedProgress,
				memo);
		taskRepository.save(upgradeTask);
		
		return "redirect:/tasks";
	}
	@GetMapping("/tasks/{id}/dateincrease")
	public String dateincrease(
			@PathVariable("id")Integer id,
			@RequestParam("categoryId")Integer categoryId,
			@RequestParam("userId")Integer userId,
			@RequestParam("title")String title,
			@RequestParam("closingDate")LocalDate closingDate,
			@RequestParam("progress")Integer progress,
			@RequestParam("memo")String memo,
			Model model){
		Integer increasedProgress;
		if(progress<2) {
		increasedProgress =progress+1;
		}else {
			return "redirect:/tasks/"+ closingDate +"/editcalender";
		}
		Task upgradeTask=new Task(
				id,
				categoryId,
				userId,
				title,
				closingDate,
				increasedProgress,
				memo);
		taskRepository.save(upgradeTask);
		List<Category>categoryList=categoryRepository.findAll();
		List<Task>taskList=taskRepository.findByClosingDate(closingDate);
		
		
		model.addAttribute("taskList",taskList);
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("date",closingDate);
		
		
		return "redirect:/tasks/"+ closingDate +"/editcalender";
	}
	
	
	@GetMapping("/tasks/{id}/decrease")
	public String decrease(@PathVariable("id")Integer id,
			@RequestParam("categoryId")Integer categoryId,
			@RequestParam("userId")Integer userId,
			@RequestParam("title")String title,
			@RequestParam("closingDate")LocalDate closingDate,
			@RequestParam("progress")Integer progress,
			@RequestParam("memo")String memo){
		Integer decreasedProgress;
		if(progress>0){
			decreasedProgress =progress-1;
			}else {
				return "redirect:/tasks";
			}
		Task upgradeTask=new Task(
				id,
				categoryId,
				userId,
				title,
				closingDate,
				decreasedProgress,
				memo);
		taskRepository.save(upgradeTask);
		return "redirect:/tasks";
	}
	@GetMapping("/tasks/{id}/datedecrease")
	public String datedecrease(@PathVariable("id")Integer id,
			@RequestParam("categoryId")Integer categoryId,
			@RequestParam("userId")Integer userId,
			@RequestParam("title")String title,
			@RequestParam("closingDate")LocalDate closingDate,
			@RequestParam("progress")Integer progress,
			@RequestParam("memo")String memo,
			Model model){
		Integer decreasedProgress;
		if(progress>0){
			decreasedProgress =progress-1;
			}else {
				return "redirect:/tasks/"+ closingDate +"/editcalender";
			}
		Task upgradeTask=new Task(
				id,
				categoryId,
				userId,
				title,
				closingDate,
				decreasedProgress,
				memo);
		taskRepository.save(upgradeTask);
		return "redirect:/tasks/"+ closingDate +"/editcalender";
	}
	
	//カレンダー出力
	@GetMapping("/calender")
	public String calenderShow(Model model) {
	//曜日ごとにリストを用意
	List<Calender>mon=new ArrayList<>();
	List<Calender>tue=new ArrayList<>();
	List<Calender>wed=new ArrayList<>();
	List<Calender>thu=new ArrayList<>();
	List<Calender>fri=new ArrayList<>();
	List<Calender>sat=new ArrayList<>();
	List<Calender>sun=new ArrayList<>();
	//今日の日付を獲得
	LocalDate today= LocalDate.now();
	//月初めを獲得
	LocalDate firstDate = today.withDayOfMonth(1);
	//月の長さを獲得
	Integer length= today.lengthOfMonth();
	//月の終わりの日を獲得
	LocalDate lastDate=today.withDayOfMonth(length);
	//月初めの曜日をString型で獲得
	String startDayOfWeek = firstDate.getDayOfWeek().toString();
	String lastDayOfWeek = lastDate.getDayOfWeek().toString();
	int downo=0;
	int downoe=0;
	//月初めの曜日を数値に変換
	if(startDayOfWeek.equals("MONDAY")) {
		downo=2;
	}else if(startDayOfWeek.equals("TUESDAY")) {
		downo=3;
	}else if(startDayOfWeek.equals("WEDNESDAY")) {
		downo=4;
	}else if(startDayOfWeek.equals("THURSDAY")) {
		downo=5;
	}else if(startDayOfWeek.equals("FRIDAY")) {
		downo=6;
	}else if(startDayOfWeek.equals("SATURDAY")) {
		downo=7;
	}else if(startDayOfWeek.equals("SUNDAY")) {
		downo=1;
	}
	//月終わりの曜日を数値に変換
	if(lastDayOfWeek.equals("MONDAY")) {
		downoe=2;
	}else if(lastDayOfWeek.equals("TUESDAY")) {
		downoe=3;
	}else if(lastDayOfWeek.equals("WEDNESDAY")) {
		downoe=4;
	}else if(lastDayOfWeek.equals("THURSDAY")) {
		downoe=5;
	}else if(lastDayOfWeek.equals("FRIDAY")) {
		downoe=6;
	}else if(lastDayOfWeek.equals("SATURDAY")) {
		downoe=7;
	}else if(lastDayOfWeek.equals("SUNDAY")) {
		downoe=1;
	}
	//後ろと前の余りを決める
	int preday = downo-1;
	int backday =downoe-1;
	//先月
	LocalDate premonth=today.minusMonths(1);
	//先月の長さ
	int prelength= premonth.lengthOfMonth();
	int a= 0;
	for(int d=preday;d>0;d--) {
		Integer day =prelength-d+1;
		Calender calender;
		if(a==6) {
			LocalDate date=premonth.withDayOfMonth(day);
			calender=new Calender(7,day,date);
			sat.add(calender);
			a=0;
			continue;
		}
        if(a==5) {
			LocalDate date=premonth.withDayOfMonth(day);
			calender=new Calender(6,day,date);
			fri.add(calender);
			
			a+=1;
			// continue;
		}
        if(a==4) {
			LocalDate date=premonth.withDayOfMonth(day);
			calender=new Calender(5,day,date);
			thu.add(calender);
			
			a+=1;
			// continue;
		}

		if(a==3) {
			LocalDate date=premonth.withDayOfMonth(day);
			calender=new Calender(4,day,date);
			wed.add(calender);
			
			a+=1;
			// continue;
		}

		
        if(a==2) {
			LocalDate date=premonth.withDayOfMonth(day);
			calender=new Calender(3,day,date);
			tue.add(calender);
			
			a+=1;
//			continue;
		}

		
        if(a==1) {
			LocalDate date=premonth.withDayOfMonth(day);
			calender=new Calender(2,day,date);
			mon.add(calender);
			
			a+=1;
//			continue;
		}
        
        
        if(a==0) {
			//カレンダー初日の処理
			LocalDate date=premonth.withDayOfMonth(day);
			calender=new Calender(1,day,date);
			sun.add(calender);
			
			a+=1;
//			continue;
		}
	}
	//繰り返し数字を日付にして上記内容の繰り返しで日付出力
	for(int b=1;b<=length;b++) {
		Integer day=b;
		Calender calender;
		if(a==6) {
			LocalDate date=today.withDayOfMonth(day);
			calender=new Calender(7,day,date);
			sat.add(calender);
			a=0;
			continue;
		}
        if(a==5) {
			LocalDate date=today.withDayOfMonth(day);
			calender=new Calender(6,day,date);
			fri.add(calender);
			a+=1;
//			continue;
		}
        if(a==4) {
			LocalDate date=today.withDayOfMonth(day);
			calender=new Calender(5,day,date);
			thu.add(calender);
			a+=1;
//			continue;
		}
		if(a==3) {
			LocalDate date=today.withDayOfMonth(day);
			calender=new Calender(4,day,date);
			wed.add(calender);
			a+=1;
//			continue;
		}
		
        if(a==2) {
			LocalDate date=today.withDayOfMonth(day);
			calender=new Calender(3,day,date);
			tue.add(calender);
			a+=1;
//			continue;
		}
		
        if(a==1) {
			LocalDate date=today.withDayOfMonth(day);
			calender=new Calender(2,day,date);
			mon.add(calender);
			a+=1;
//			continue;
		}
		
        if(a==0) {
			LocalDate date=today.withDayOfMonth(day);
			calender=new Calender(1,day,date);
			sun.add(calender);
			a+=1;
//			continue;
		}
	}
	LocalDate backmonth=today.plusMonths(1);
	//繰り返し数字を日付にして上記内容の繰り返しで後ろの数字を出力
	for(int c=1;c<=backday;c++) {
		Integer day=c;
		Calender calender;
		if(a==6) {
			LocalDate date = backmonth.withDayOfMonth(day);
			calender=new Calender(7,day,date);
			sat.add(calender);
			a=0;
			continue;
		}
        if(a==5) {
			LocalDate date = backmonth.withDayOfMonth(day);
			calender=new Calender(6,day,date);
			fri.add(calender);
			a+=1;
//			continue;
		}
        if(a==4) {
			LocalDate date = backmonth.withDayOfMonth(day);
			calender=new Calender(5,day,date);
			thu.add(calender);
			a+=1;
//			continue;
		}
		
		
		if(a==3) {
			LocalDate date = backmonth.withDayOfMonth(day);
			calender=new Calender(4,day,date);
			wed.add(calender);
			a+=1;
//			continue;
		}
		
        if(a==2) {
			LocalDate date = backmonth.withDayOfMonth(day);
			calender=new Calender(3,day,date);
			tue.add(calender);
			a+=1;
//			continue;
		}
		
        if(a==1) {
			LocalDate date = backmonth.withDayOfMonth(day);
			calender=new Calender(2,day,date);
			mon.add(calender);
			a+=1;
//			continue;
		}
		
        if(a==0) {
			LocalDate date = backmonth.withDayOfMonth(day);
			calender=new Calender(1,day,date);
			sun.add(calender);
			a+=1;
//			continue;
		}
	}
	
	
	List<Integer>sunthereOrNot=new ArrayList<>();
	List<Integer>monthereOrNot=new ArrayList<>();
	List<Integer>tuethereOrNot=new ArrayList<>();
	List<Integer>wedthereOrNot=new ArrayList<>();
	List<Integer>thuthereOrNot=new ArrayList<>();
	List<Integer>frithereOrNot=new ArrayList<>();
	List<Integer>satthereOrNot=new ArrayList<>();
	//カレンダーにタスクのあるなし判定
	for(int f=0;f<=4;f++) {
		Calender sunA=sun.get(f);
		Calender monA=mon.get(f);
		Calender tueA=tue.get(f);
		Calender wedA=wed.get(f);
		Calender thuA=thu.get(f);
		Calender friA=fri.get(f);
		Calender satA=sat.get(f);
		LocalDate sunDate=sunA.getDate();
		LocalDate monDate=monA.getDate();
		LocalDate tueDate=tueA.getDate();
		LocalDate wedDate=wedA.getDate();
		LocalDate thuDate=thuA.getDate();
		LocalDate friDate=friA.getDate();
		LocalDate satDate=satA.getDate();
		List<Task>sunTask=new ArrayList<>();
		sunTask=taskRepository.findByClosingDate(sunDate);
		List<Task>monTask=new ArrayList<>();
		monTask=taskRepository.findByClosingDate(monDate);
		List<Task>tueTask=new ArrayList<>();
		tueTask=taskRepository.findByClosingDate(tueDate);
		List<Task>wedTask=new ArrayList<>();
		wedTask=taskRepository.findByClosingDate(wedDate);
		List<Task>thuTask=new ArrayList<>();
		thuTask=taskRepository.findByClosingDate(thuDate);
		List<Task>friTask=new ArrayList<>();
		friTask=taskRepository.findByClosingDate(friDate);
		List<Task>satTask=new ArrayList<>();
		satTask=taskRepository.findByClosingDate(satDate);
		if(sunTask.size()==0||sunTask==null) {
			sunthereOrNot.add(1);
		}else {
			sunthereOrNot.add(2);
		}
		if(monTask.size()==0||monTask==null) {
			monthereOrNot.add(1);
		}else {
			monthereOrNot.add(2);
		}
		if(tueTask.size()==0||tueTask==null) {
			tuethereOrNot.add(1);
		}else {
			tuethereOrNot.add(2);
		}
		if(wedTask.size()==0||wedTask==null) {
			wedthereOrNot.add(1);
		}else {
			wedthereOrNot.add(2);
		}
		if(thuTask.size()==0||thuTask==null) {
			thuthereOrNot.add(1);
		}else {
			thuthereOrNot.add(2);
		}
		if(friTask.size()==0||friTask==null) {
			frithereOrNot.add(1);
		}else {
			frithereOrNot.add(2);
		}
		if(satTask.size()==0||satTask==null) {
			satthereOrNot.add(1);
		}else {
			satthereOrNot.add(2);
		}	
	}
	//横方向の格納
	List <ToWeek>monthmaker=new ArrayList<>();
	for(int c=0;c<=4;c++) {
		Calender sunA=sun.get(c);
		Calender monA=mon.get(c);
		Calender tueA=tue.get(c);
		Calender wedA=wed.get(c);
		Calender thuA=thu.get(c);
		Calender friA=fri.get(c);
		Calender satA=sat.get(c);
		Integer sunt=sunthereOrNot.get(c);
		Integer mont=monthereOrNot.get(c);
		Integer tuet=tuethereOrNot.get(c);
		Integer wedt=wedthereOrNot.get(c);
		Integer thut=thuthereOrNot.get(c);
		Integer frit=frithereOrNot.get(c);
		Integer satt=satthereOrNot.get(c);
		ToWeek monthsmake= new ToWeek(sunA,monA,tueA,wedA,thuA,friA,satA,sunt,mont,tuet,wedt,thut,frit,satt);
		monthmaker.add(monthsmake);
	}
	model.addAttribute("calenders",monthmaker);
	int todaysmonth=today.getMonthValue();
	int todaysyear=today.getYear();
	String calenderhead=todaysyear+"年"+todaysmonth+"月";
	model.addAttribute("calenderhead",calenderhead);
	
	return "calender";
}
	
	
	
	@PostMapping("/tasks/add")
	public String taskAdd(
			@RequestParam("category")Integer categoryId,
			@RequestParam("title")String title,
			@RequestParam("closingDate")LocalDate closingDate,
			@RequestParam("memo")String memo,
			Model model) {
		List<String>errorList=new ArrayList<>();
		Integer id=account.getId();
		if(id==null) {
			errorList.add("ログインされていません");
			model.addAttribute("error",errorList);
			return "user";
		}
		Task task = new Task(categoryId,id,title,closingDate,0,memo);
		taskRepository.save(task);
		return "redirect:/tasks";
		
	}
	@PostMapping("/tasks/{id}/update")
	public String taskUpdate(
			@PathVariable("id")Integer id,
			@RequestParam("category")Integer categoryId,
			@RequestParam("userId")Integer userId,
			@RequestParam("title")String title,
			@RequestParam("closingDate")LocalDate closingDate,
			@RequestParam("progress")Integer progress,
			@RequestParam("memo")String memo,
			@RequestParam("handover")int handover,
			Model model) {
		Integer updateuserId = account.getId();
		List<String>errorList=new ArrayList<>();
		if(id==null) {
			errorList.add("ログインされていません");
			model.addAttribute("error",errorList);
			return "user";
		}
		Task task;
		if(handover==1) {
			task = new Task(id,categoryId,updateuserId,title,closingDate,progress,memo);
		}else {
		task = new Task(id,categoryId,userId,title,closingDate,progress,memo);
		}
		taskRepository.save(task);
		return "redirect:/tasks";
	}
	@PostMapping("/tasks/{id}/delete")
	public String taskDelete(
			@PathVariable("id")Integer id) {
		taskRepository.deleteById(id);
		return "redirect:/tasks";
	}
	@PostMapping("/tasks/{id}/dateupdate")
	public String taskdateUpdate(
			@PathVariable("id")Integer id,
			@RequestParam("category")Integer categoryId,
			@RequestParam("userId")Integer userId,
			@RequestParam("title")String title,
			@RequestParam("closingDate")LocalDate closingDate,
			@RequestParam("progress")Integer progress,
			@RequestParam("memo")String memo,
			@RequestParam("handover")int handover,
			Model model) {
		Integer updateuserId = account.getId();
		List<String>errorList=new ArrayList<>();
		if(id==null) {
			errorList.add("ログインされていません");
			model.addAttribute("error",errorList);
			return "user";
		}
		Task task;
		if(handover==1) {
			task = new Task(id,categoryId,updateuserId,title,closingDate,progress,memo);
		}else {
		task = new Task(id,categoryId,userId,title,closingDate,progress,memo);
		}
		taskRepository.save(task);
		return "redirect:/tasks/"+ closingDate +"/editcalender";
	}
	@PostMapping("/tasks/{id}/datedelete")
	public String taskdateDelete(
			@PathVariable("id")Integer id,
			@RequestParam("closingDate")LocalDate closingDate) {
		taskRepository.deleteById(id);
		return "redirect:/tasks/"+ closingDate +"/editcalender";
	}
	
}
