package kr.kro.bbanggil.common.util;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import kr.kro.bbanggil.global.config.FileUploadProperties;
import kr.kro.bbanggil.user.bakery.dto.request.FileRequestDTO;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FileUploadUtil {
   private FileUploadProperties fileUploadProperties;
   
   public void uploadFile(MultipartFile file, FileRequestDTO FileRequestDTO, String folderName) throws IOException {
      FileRequestDTO.setLocalPath(fileUploadProperties.getLocalPath());
      FileRequestDTO.setLocalResourcePath(fileUploadProperties.getResourcesPath());
      
      String originalFileName = file.getOriginalFilename();
      String changeName = UUID.randomUUID().toString() + "." + getFileExtension(originalFileName);
      Path path = Paths.get(FileRequestDTO.getLocalPath() + "\\" + folderName + "\\" + changeName);
      Files.write(path, file.getBytes());
      
      FileRequestDTO.setOriginalName(originalFileName);
      FileRequestDTO.setChangeName(changeName);
      FileRequestDTO.setExtension(getFileExtension(originalFileName));
      FileRequestDTO.setSize(file.getSize());
      FileRequestDTO.setFolderNamePath(folderName);
   }
   
   private String getFileExtension(String fileName) {
      // fileName : 제목없음.png
      // dotIndex : 4
      int dotIndex = fileName.lastIndexOf('.');
      return dotIndex == -1 ? "" : fileName.substring(dotIndex+1);
   }
   
   public void deleteFile(String localPath,  String folderName, String fileName) throws IOException {
      Path path = Paths.get(localPath + "\\" + folderName + "\\" + fileName);
      Files.delete(path);
   }
}