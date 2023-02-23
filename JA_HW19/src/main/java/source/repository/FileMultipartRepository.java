package source.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import source.domain.FileMultipart;

public interface FileMultipartRepository extends JpaRepository<FileMultipart, String> {

}
