package com.QrApplication.Implementation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.QrApplication.Interface.FileUpload;

@Component
public class DocumentUpload implements FileUpload {

	@Override
	public String uploadFile(String filePath, String base64) {

		try {
			String filename = UUID.randomUUID().toString();
			Path directoryPath = Paths.get(filePath);
			if (!Files.exists(directoryPath)) {
				try {
					Files.createDirectories(directoryPath);
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}

			byte[] imageBytes = Base64.getDecoder().decode(base64);
			String path = filePath+File.separator +  filename + ".png";
			System.err.println(path);

			try (FileOutputStream fos = new FileOutputStream(new File(path))) {
				fos.write(imageBytes);
				fos.flush();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return filename + ".png";

		} catch (Exception e) {
			return null;
		}
	}

}
