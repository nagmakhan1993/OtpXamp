package com.otp.Xamp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.otp.Xamp.Entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

	List<Book> findByBookLink(String bookLink);

	@Query("SELECT b FROM Book b WHERE b.className = :className AND b.subjectName = :subjectName AND b.bookName = :bookName")
	List<Book> findByClassNameSubjectAndBookName(@Param("className") String className, @Param("subjectName") String subjectName,
			@Param("bookName") String bookName);

}
