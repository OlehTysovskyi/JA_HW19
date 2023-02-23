package source.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "firstname")
	private String firstname;
	@Column(name = "lastname")
	private String lastname;
	@Column(name = "age")
	private int age;
	@OneToOne(cascade = CascadeType.ALL)
	private FileMultipart photo;

	public Student(String firstname, String lastname, int age, FileMultipart photo) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
		this.photo = photo;
	}

	public Student() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public FileMultipart getPhoto() {
		return photo;
	}

	public void setPhoto(FileMultipart photo) {
		this.photo = photo;
	}
}
