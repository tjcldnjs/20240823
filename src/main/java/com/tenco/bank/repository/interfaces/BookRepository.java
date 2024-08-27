package com.tenco.bank.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tenco.bank.dto.Item;
import com.tenco.bank.dto.Todo;

@Mapper
public interface BookRepository {
	
	public int insert(Item todo);
	
	public List<Item> select();
	
}

