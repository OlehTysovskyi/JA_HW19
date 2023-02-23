package source.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import source.domain.FileMultipart;
import source.domain.Student;
import source.dto.MultipartUploadResponse;
import source.service.FileMultipartService;

@RestController
public class FileMultipartController {

	@Autowired
	FileMultipartService fileMultipartService;

	@PostMapping("/uploadFile")
	public MultipartUploadResponse uploadFile(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "firstname", required = false) String firstname,
			@RequestParam(value = "lastname", required = false) String lastname,
			@RequestParam(value = "age", required = false) String age) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Student student = new Student();
		student.setFirstname(firstname);
		student.setLastname(lastname);
		student.setAge(Integer.parseInt(age));
		FileMultipart fileMultipart = fileMultipartService.storeFile(file, student);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileMultipart.getId()).toUriString();

		return new MultipartUploadResponse(fileMultipart.getFileName(), fileDownloadUri, file.getContentType(),
				file.getSize());
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String move() {

		return "cabinet";
	}

	@GetMapping("/downloadFile/{fileId}")
	public ResponseEntity<Resource> downlaodFile(@PathVariable String fileId) throws FileNotFoundException {
		FileMultipart fileMultipart = fileMultipartService.getFile(fileId);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileMultipart.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileMultipart.getFileName() + "\"")
				.body(new ByteArrayResource(fileMultipart.getData()));
	}

//    @PostMapping("/uploadMultipleFiles")
//    public List<MultipartUploadResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//        return Arrays.asList(files).stream().map(file -> {
//            try {
//                return uploadFile(file);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }).collect(Collectors.toList());
//    }

}