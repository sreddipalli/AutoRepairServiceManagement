package com.autorepair.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.utils.Md5Utils;

@RestController
@AllArgsConstructor
@Slf4j
public class DocumentController {

  private S3Client s3Client;

  @GetMapping("servicemanagement/api/v1.0/document/{documentPath}/**")
  ResponseEntity<byte[]> getImageV1(HttpServletRequest request,
      @PathVariable("documentPath") String documentPath) throws UnsupportedEncodingException {

    log.info("get the document :{}", documentPath);

    String fullPath = URLDecoder.decode(request.getRequestURI(), StandardCharsets.UTF_8.toString());
    String capturedPath = fullPath.substring(fullPath.indexOf(documentPath));
    log.info("get the document : {} and capturedPath :{}", documentPath, capturedPath);
    GetObjectRequest objectRequest =
        GetObjectRequest.builder().key(capturedPath).bucket("autorepairtest").build();

    ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(objectRequest);
    byte[] imageBytes = objectBytes.asByteArray();

    log.info("object Response contentType :{}", objectBytes.response().contentType());
    MediaType mediaType = MediaType.parseMediaType(objectBytes.response().contentType());

    log.info("MediaType :{}", mediaType);
    return ResponseEntity.ok().contentType(mediaType).body(imageBytes);
  }

  @PostMapping(value = "servicemanagement/api/v1.0/document")
  ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile imageFile,
      @RequestParam String key) throws IOException {

    log.info("file upload details:{}", imageFile);

    // Calculate the source file MD5
    String clientMD5 = Md5Utils.md5AsBase64(imageFile.getInputStream());

    log.info("key for file upload :{}, file.getContentType :{}", key, imageFile.getContentType());

    PutObjectRequest objectRequest = PutObjectRequest.builder().bucket("autorepairtest").key(key)
        .contentMD5(clientMD5).contentType(imageFile.getContentType()).build();


    PutObjectResponse response = s3Client.putObject(objectRequest,
        software.amazon.awssdk.core.sync.RequestBody.fromBytes(imageFile.getBytes()));

    log.info("Response metadata :{}", response.responseMetadata());
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
        .body("Success" + imageFile.getOriginalFilename());
  }
}
