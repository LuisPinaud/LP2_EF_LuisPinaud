package pe.com.cibertec.LP2_EF_LuisPinaud.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.multipart.MultipartFile;

public class Utilitarios {
	public static String guardarImagen(MultipartFile foto) {
		try {
			Path pathDire = Paths.get("src/main/resources/static/foto_usuario/");
			if(!Files.exists(pathDire)) {
				Files.createDirectories(pathDire);
			}
			
			byte[] bytes = foto.getBytes();
			Path path = Paths.get("src/main/resources/static/foto_usuario/" + 
			foto.getOriginalFilename());
			
			Files.write(path, bytes);
			return foto.getOriginalFilename();
			
		}catch (IOException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public static String extraerHashContra(String passwordOriginal) {
		return BCrypt.hashpw(passwordOriginal, BCrypt.gensalt());
	}
	
	public static boolean checkPassword(String passwordIngresado, String hashPassword) {
		return BCrypt.checkpw(passwordIngresado, hashPassword);
	}
}
