package com.vibid.files.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Service
@Transactional
public class ImageService {

  private final Path rootLocation;

  public ImageService(@Value("${uploadPath.path}") String uploadPath) {
    this.rootLocation = Paths.get(uploadPath);
  }

  public String store(MultipartFile file) throws IOException {  //파일 전송 확인
    //		 fileName : 예류2.jpg
    //		 filePath : d:/images/uuid-예류2.jpg
    //		 saveFileName : uuid-예류2.png
    //		 fileNameExtension : image/jpeg
    //		 size : 4994942
    //		 registerDate : 2020-02-06 22:29:57.748

    if (file.isEmpty()) { // 빈파일 확인
      throw new IllegalArgumentException("Failed to store empty file " + file.getOriginalFilename());
    }
    //파일 정보 저장
    return fileSave(rootLocation.toString(), file); // 파일 저장후 반환된 파일명 저장
  }

  public String fileSave(String rootLocation, MultipartFile file) throws IOException {
    File uploadDir = new File(rootLocation);
    //업로드 경로 확인
    if (!uploadDir.exists()) {
      uploadDir.mkdirs();
    }

    // saveFileName 생성
    UUID uuid = UUID.randomUUID(); // 랜덤 uuid생성
    String saveFileName = uuid.toString() + file.getOriginalFilename(); // saveFileName에 생성된 uuid + 원본파일이름 저장 (중복방지)
    File saveFile = new File(rootLocation, saveFileName); // 위의 확인된 경로와, 생성된 파일명 저장
    FileCopyUtils.copy(file.getBytes(), saveFile); //byte 타입 배열을 지정한 파일에 복사 {FileCopyUtils 파일및 스트림 복사를 위한 유틸리티 메소드}
    //saveFileName 반환
    return saveFileName;
  }

  public Resource loadFile(String fileName) {
    return new FileSystemResource(rootLocation + "/" + fileName);
  }


}
