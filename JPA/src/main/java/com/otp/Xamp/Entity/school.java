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
@Table(name = "School")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@DynamicUpdate
@Data
public class school {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sId")
	private Integer sId;

	@Column(name = "District", nullable = false)
	private String district;

	@Column(name = "Zone", nullable = false)
	private String zone;

	@Column(name = "SchoolID", nullable = false)
	private String schoolID;

	@Column(name = "UDISE_Code", nullable = false)
	private String uDISE_Code;

	@Column(name = "Buildingid", nullable = false)
	private String buildingid;

	@Column(name = "School_Name", nullable = false)
	private String schoolName;

	@Column(name = "Address", nullable = false)
	private String address;

	@Column(name = "Shift", nullable = false)
	private String shift;

	@Column(name = "SchoolLevel", nullable = false)
	private String schoolLevel;

	@Column(name = "Gender", nullable = false)
	private String gender;

	@Column(name = "Phone", nullable = false)
	private String phone;

	@Column(name = "Hos_Name", nullable = false)
	private String hosName;

	@Column(name = "Latitude", nullable = false)
	private String latitude;

	@Column(name = "Longitude", nullable = false)
	private String longitude;

	public Integer getsId() {
		return sId;
	}

	public void setsId(Integer sId) {
		this.sId = sId;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getSchoolID() {
		return schoolID;
	}

	public void setSchoolID(String schoolID) {
		this.schoolID = schoolID;
	}

	public String getuDISE_Code() {
		return uDISE_Code;
	}

	public void setuDISE_Code(String uDISE_Code) {
		this.uDISE_Code = uDISE_Code;
	}

	public String getBuildingid() {
		return buildingid;
	}

	public void setBuildingid(String buildingid) {
		this.buildingid = buildingid;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getSchoolLevel() {
		return schoolLevel;
	}

	public void setSchoolLevel(String schoolLevel) {
		this.schoolLevel = schoolLevel;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getHosName() {
		return hosName;
	}

	public void setHosName(String hosName) {
		this.hosName = hosName;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}
