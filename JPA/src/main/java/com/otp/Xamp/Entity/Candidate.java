package com.otp.Xamp.Entity;

import java.util.Date;

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
@Table(name = "Student")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@DynamicUpdate
@Data

public class Candidate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cId")
	private Integer cId;

	@Column(name = "cName", nullable = false)
	private String cName;

	@Column(name = "fatherName", nullable = false)
	private String fatherName;

	@Column(name = "mothername", nullable = false)
	private String motherName;

	@Column(name = "dob", nullable = false)
	private Date dob;

	@Column(name = "gender", nullable = false)
	private String gender;

	@Column(name = "add1", nullable = false)
	private String add1;

	@Column(name = "add2", nullable = false)
	private String add2;

	@Column(name = "district", nullable = false)
	private String district;

	@Column(name = "city", nullable = false)
	private String city;

	@Column(name = "state", nullable = false)
	private String state;

	@Column(name = "pincode", nullable = false)
	private String pincode;

	@Column(name = "cNum", nullable = false)
	private String cNum;

	@Column(name = "email", nullable = false)
	private String email;

}
