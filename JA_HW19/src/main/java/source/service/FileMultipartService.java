package source.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import source.domain.FileMultipart;
import source.domain.Student;
import source.repository.FileMultipartRepository;
import source.repository.StudentRepository;

@Service
public class FileMultipartService {

	@Autowired
	private FileMultipartRepository fileMultipartRepository;
	@Autowired
	private StudentRepository studentRepository;

	public FileMultipart storeFile(MultipartFile file, Student student) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileMultipart multipart = null;

		if (!fileName.contains("..")) {
			multipart = new FileMultipart(fileName, file.getContentType(), file.getBytes());
		}

		student.setPhoto(multipart);
		studentRepository.save(student);
		return fileMultipartRepository.save(multipart);
	}

	public FileMultipart getFile(String fileId) throws FileNotFoundException {
		return fileMultipartRepository.findById(fileId)
				.orElseThrow(() -> new FileNotFoundException("File not found with Id = [" + fileId + "]"));
	}
}
