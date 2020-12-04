package com.hutu.es;

import com.hutu.es.dao.BookRepository;
import com.hutu.es.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.CustomEsRestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Slf4j
public class EsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EsApplication.class, args);
	}
	@RestController
	public class Test{
		@Autowired
		CustomEsRestTemplate elasticsearchRestTemplate;
		@Autowired
		BookRepository bookRepository;
		@GetMapping("getbook")
		public String get(){
			log.info("coming Test ...");
			Page<Book> books = bookRepository.findByName("米", PageRequest.of(0, 3));
			System.out.println("boos: "+books.getContent());
			return elasticsearchRestTemplate.get("5", Book.class).toString();
		}
	}
}
