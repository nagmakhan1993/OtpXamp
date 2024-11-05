package com.otp.Xamp.Entity;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Book")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@DynamicUpdate
@Data

public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bId")
	private Integer bId;

	@Column(name = "subjectName", nullable = false)
	private String subjectName;

	@Column(name = "bookName", nullable = false)
	private String bookName;

	@Column(name = "NcertWebLink", nullable = false)
	private String href;

	@Column(name = "className", nullable = false)
	private String className;

	@Column(name = "numberOfChapter", nullable = false)
	private String numberOfChapter;

	@Column(name = "BookLink", nullable = false)
	private String originLink;
}
