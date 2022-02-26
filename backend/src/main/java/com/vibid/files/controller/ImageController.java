package com.vibid.files.controller;

import com.vibid.api.ApiResult;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.vibid.files.service.ImageService;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

// 판매글 안(SummerNote)의 이미지 url컨트롤러
@RestController
@RequestMapping("/api/image")
public class ImageController {
	private final Logger Log = Logger.getGlobal();

	private final ImageService imageService;
	private final ResourceLoader resourceLoader;

	public ImageController(ImageService imageService, ResourceLoader resourceLoader) {
		this.imageService = imageService;
		this.resourceLoader = resourceLoader;
	}
	
	@PostMapping
	public ApiResult<?> imageUpload(@RequestParam("file") MultipartFile file) {
		try {
			String fileName = imageService.store(file);
			return ApiResult.OK(fileName);
		} catch(Exception e) {
			return ApiResult.ERROR(e.toString(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{fileName}")
	public ResponseEntity<?> serveFile(@PathVariable String fileName){
		try {

			Resource resource = imageService.loadFile(fileName);

			return ResponseEntity.ok()
				.contentType(MediaType.valueOf(Files.probeContentType(Path.of(fileName))))
				.header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.builder("attachment")
					.filename(fileName, StandardCharsets.UTF_8)
					.build().toString())
				.body(resource);

		} catch(Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
