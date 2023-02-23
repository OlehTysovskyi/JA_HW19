package source.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import source.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
