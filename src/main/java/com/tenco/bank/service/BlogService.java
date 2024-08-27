package com.tenco.bank.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.Item;
import com.tenco.bank.repository.interfaces.BookRepository;

@Service
public class BlogService {

	private final BookRepository bookRepository;

	public BlogService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Transactional
	public int insert(Item todo) {
		int todos= 0;
		
		todos = bookRepository.insert(todo);
		
		return todos;
		
	}
	
	public List<Item> select(){
		List<Item> item =null;
		item = bookRepository.select();
		return item;
	}
	
}
