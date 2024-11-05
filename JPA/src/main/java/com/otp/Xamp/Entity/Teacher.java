package com.otp.Xamp.Entity;

import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
@Table(name = "Teacher")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@DynamicUpdate
@Data
public class Teacher {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tId")
	private Integer tId;

	@Column(name = "tName", nullable = false)
	private String tName;

	@Column(name = "schoolID", nullable = false)
	private String schoolID;

	@Column(name = "School_Name", nullable = false)
	private String schoolName;

	@Column(name = "Address", nullable = false)
	private String address;

	@Column(name = "Gender", nullable = false)
	private String gender;

	@Column(name = "Phone", nullable = false)
	private String phone;

	@ElementCollection
	@Column(name = "subjectList", nullable = false)
	private List<String> subjectList;

}
