package com.tenco.bank.board;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.tenco.bank.dto.Todo;
import com.tenco.bank.dto.Todo.item;
import com.tenco.bank.repository.model.Blog;
import com.tenco.bank.service.BlogService;

@Controller
public class BoardController {
	private final BlogService blogService;

	public BoardController(BlogService blogService) {
		this.blogService = blogService;
	}
	
	@GetMapping("/books")
	public String getBooks(Model model) {
		// JSON 데이터 (예시로 하드코딩된 데이터 사용)
		
		List<String> coverImageUrls = new ArrayList<>();
		coverImageUrls.add("https://image.aladin.co.kr/product/34499/81/cover200/k712932751_1.jpg");

		// 모델에 데이터 추가
		model.addAttribute("coverImageUrls", coverImageUrls);

		// JSP 뷰 반환
		return "books";
	}

	@GetMapping("/test/")
	public ResponseEntity<?> restChangeTest() {

		// 여기 주소는 리소스 서버 주소 설정을 해야 한다.
		URI uri = UriComponentsBuilder.fromUriString("http://www.aladin.co.kr").path("/ttb/api/ItemList.aspx")
				.queryParam("ttbkey", "ttbkred7300361331001").queryParam("QueryType", "Bestseller")
				.queryParam("MaxResults", "50").queryParam("start", "10000").queryParam("SearchTarget", "Book")
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
		ResponseEntity<Todo> response = restTemplate1.exchange(uri, HttpMethod.POST, requestEntity, Todo.class);

		System.out.println("response Header : " + response.getHeaders());
		System.out.println("response Body : " + response.getBody());

		System.out.println("sysout :" + response.getBody().getItem());
		return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
	}

	@GetMapping("/board/index")
	public String index(Model model, @RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {

		int totalRecords = blogService.selectCount();
		int totalPages = (int) Math.ceil((double) totalRecords / size);

		List<Blog> blogList = blogService.select(page, size);

		model.addAttribute("currentPage", page);
		model.addAttribute("blogList", blogList);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("size", size);

		return "/board/index";
	}

	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}

	@GetMapping("/board/updateForm")
	public String updateForm(@RequestParam(name = "id", required = false) int id, Model model) {
		Blog blog = blogService.selectById(id);
		model.addAttribute("blog", blog);
		return "board/updateForm";
	}

	@PostMapping("/board/save")
	public String save(Blog blog) {
		blogService.create(blog);
		return "redirect:/board/index";
	}

	@PostMapping("/board/update")
	public String update(Blog blog, @RequestParam(name = "id", required = false) int id) {

		blogService.update(blog);
		return "redirect:/board/index";
	}

	@PostMapping("/board/delete")
	public String delete(@RequestParam(name = "id", required = false) int id) {
		blogService.delete(id);
		return "redirect:/board/index";
	}
}
