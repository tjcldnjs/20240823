//package com.tenco.bank.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.tenco.bank.repository.interfaces.BookRepository;
//import com.tenco.bank.repository.model.Book;
//
//@Service
//public class BookService {
//
//	@Autowired
//	private BookRepository bookRepository;
//
//	public List<Book> bookListAll(int pageNum){
//		if(pageNum == 1) {
//			pageNum = 0;
//		}else {
//			pageNum = pageNum * 30;
//		}
//		return bookRepository.bookListAll(pageNum);
//	}
