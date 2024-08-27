package com.tenco.bank.board;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.tenco.bank.dto.Item;
import com.tenco.bank.dto.Todo;
import com.tenco.bank.repository.interfaces.BlogRepository;
import com.tenco.bank.repository.interfaces.BookRepository;
import com.tenco.bank.service.BlogService;

@Controller
public class BoardController {

	@Autowired
	private BlogService blogService;
	private BookRepository bookRepository;

	@GetMapping("/books")
	public String showBooks(Model model, @RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "30") int size) {
		// 데이터베이스에서 항목을 가져옵니다.
		List<Item> items = blogService.select();

		// 페이지네이션
		int totalItems = items.size();
		int totalPages = (int) Math.ceil((double) totalItems / size);
		int start = (page - 1) * size;
		int end = Math.min(start + size, totalItems);

		// 모델에 데이터를 추가
		model.addAttribute("items", items.subList(start, end));
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);

		// books.jsp로 이동
		return "books";
	}

	@GetMapping("/test/")
	public ResponseEntity<?> restChangeTest() {
		List<Item> itemList = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			// 여기 주소는 리소스 서버 주소 설정을 해야 한다.
			URI uri = UriComponentsBuilder.fromUriString("http://www.aladin.co.kr").path("/ttb/api/ItemList.aspx")
					.queryParam("ttbkey", "ttbkred7300361331001").queryParam("QueryType", "Bestseller")
					.queryParam("MaxResults", "50").queryParam("start", i + 1).queryParam("SearchTarget", "Book")
					.queryParam("output", "js").queryParam("Cover", "Big").queryParam("Version", "20131101").build()
					.toUri();
			// 2. 객체 생성
			RestTemplate restTemplate1 = new RestTemplate();
			// HTTP 메세지 Header 생성하기
			// exchange 메서드 활용

			// 1. 헤더 구성
			HttpHeaders headers = new HttpHeaders();
			// 'Content-type': 'application/json; charset=UTF-8',
			headers.add("Content-Type", "application/json");

			// 2. 바디 구성
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

			// 3. 헤더와 바디 결합 --> HttpEntity Object
			HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

			// 4. RestTemplate 을 활용해서 HTTP 통신 요청
			ResponseEntity<Todo> response = restTemplate1.exchange(uri, HttpMethod.GET, requestEntity, Todo.class);

			Todo todo = response.getBody();
			List<Item> books = todo.getItem();

			for (Item book : books) {
				itemList.add(
						Item.builder().title(book.getTitle()).author(book.getAuthor()).publisher(book.getPublisher())
								.cover(book.getCover()).categoryName(book.getCategoryName()).build());
			}
		}

		// 각각의 아이템을 데이터베이스에 저장
		for (Item item : itemList) {
			blogService.insert(item);
		}

		return new ResponseEntity<>(itemList, HttpStatus.OK);
	}
}
