package pe.com.cibertec.LP2_EF_LuisPinaud.service;

import org.springframework.web.multipart.MultipartFile;

import pe.com.cibertec.LP2_EF_LuisPinaud.model.UsuarioEntity;

public interface UsuarioService {
	void crearUsuario(UsuarioEntity usuarioEntity, MultipartFile foto);
	boolean validacion(UsuarioEntity usuarioEntity);

}
