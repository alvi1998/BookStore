package com.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.bookstore.entity.Book;
import com.bookstore.entity.MyBookList;
import com.bookstore.service.BookService;
import com.bookstore.service.MyBookListService;


@Controller
public class BookController {
	
	@Autowired
	private BookService service;
	
	@Autowired
	private MyBookListService myBookService;
	
	@GetMapping("/")
	
	public String home()
	{
		return "home";
	}
	
	
	@GetMapping("/book_register")
	public String bookRegister()
	{
		return "book_register";
	}
	
	
	@GetMapping("/available_books")
	public ModelAndView getAllbooks()
	{
		List<Book>list =service.getAllBook();
//		ModelAndView m = new ModelAndView();
//		m.setViewName("bookList");
//		m.addObject("book",list);
//		return m;
		
		return new ModelAndView("bookList","book",list);
	}
	
	@PostMapping("/save")
	
	public String addBook(@ModelAttribute Book b)
	{
		service.save(b);
		return "redirect:/available_books";
	}
	
	
     @GetMapping("/my_Books")
     public String getmyBookList(Model model)
     {
    	 List<MyBookList>list =myBookService.getAllMyBooks();
    	 model.addAttribute("book",list);
    	 return "/myBooks";
     }
     
     @RequestMapping("/mylist/{id}")
     public String getMyList(@PathVariable("id") int id)
     {
    	 Book b = service.getBookById(id);
    	 
    	 MyBookList mb = new MyBookList(b.getId(),b.getName(),b.getAuthor(),b.getPrice());
    	 myBookService.save(mb);
    	 return "redirect:/my_Books";
     }
	
     
     @RequestMapping("/editBook/{id}")
     public String editBook(@PathVariable("id") int id, Model model)
     {
    	 
    	 Book b=service.getBookById(id);
    	 model.addAttribute("book" ,b);
    	 return "bookEdit";
     }
     
     @RequestMapping("/deleteBook/{id}")
     
 	public String deleteBook(@PathVariable("id") int id)
 	{
    	 service.deleteById(id);
    	 return"redirect:/available_books";
 	}
	

}
