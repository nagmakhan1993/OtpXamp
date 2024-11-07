package com.otp.Xamp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.otp.Xamp.Entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

	public List<Book> findByHref(String href);

	@Query("SELECT b FROM Book b WHERE b.className = :className AND b.subjectName = :subjectName AND b.bookName = :bookName")
	public List<Book> findByClassNameSubjectAndBookName(@Param("className") String className,
			@Param("subjectName") String subjectName, @Param("bookName") String bookName);

	public Book findByOriginLink(String originLink);

	@Query("SELECT COUNT(DISTINCT b.className) FROM Book b")
	public long countNumberOfClasses();

	@Query("SELECT DISTINCT className FROM Book b")
	public List<String> listOfClasses();

	@Query("SELECT COUNT(distinct subjectName )FROM Book b WHERE className=:className")
	public long countNumberofSubjectsClassWise(@Param("className") String className);

	@Query("SELECT DISTINCT subjectName FROM Book b WHERE className =:className")
	public List<String> listOfSubjectsClassWise(@Param("className") String className);

	@Query("SELECT COUNT(DISTINCT bookName) FROM Book b WHERE className = :className and subjectName = :subjectName")
	public long countOfBooksClassAndSubjectWise(@Param("className") String className,
			@Param("subjectName") String subjectName);

	@Query("SELECT DISTINCT bookName, originLink  FROM Book b WHERE className = :className and subjectName = :subjectName")
	public List<String[]> listOfBooksAndOriginLinkClassAndSubjectWise(@Param("className") String className,
			@Param("subjectName") String subjectName);

	@Query("SELECT DISTINCT	originLink from Book b WHERE className = :className and bookName = :bookName")
	public List<String> listOfBookLinksClassAndBookNameWise(@Param("className") String className,
			@Param("bookName") String bookName);

}
