package pe.com.cibertec.LP2_EF_LuisPinaud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pe.com.cibertec.LP2_EF_LuisPinaud.model.UsuarioEntity;
import pe.com.cibertec.LP2_EF_LuisPinaud.repository.UsuarioRepository;
import pe.com.cibertec.LP2_EF_LuisPinaud.service.UsuarioService;
import pe.com.cibertec.LP2_EF_LuisPinaud.utils.Utilitarios;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public void crearUsuario(UsuarioEntity usuarioEntity, MultipartFile foto) {
		// TODO Auto-generated method stub
		String nomFoto = Utilitarios.guardarImagen(foto);
		usuarioEntity.setUrlImagen(nomFoto);
		
		String passwordHash = Utilitarios.extraerHashContra(usuarioEntity.getPassword());
		usuarioEntity.setPassword(passwordHash);
		
		usuarioRepository.save(usuarioEntity);	
	}

	@Override
	public boolean validacion(UsuarioEntity usuarioEntity) {
		
				UsuarioEntity usuarioObtenido = usuarioRepository.findByCorreo(usuarioEntity.getCorreo());
				
				if(usuarioObtenido == null) {
					return false;
				}
				
				if(!Utilitarios.checkPassword(usuarioEntity.getPassword(), 
						usuarioObtenido.getPassword())) {
					return false;
				}
				
				return true;
	}

	@Override
	public UsuarioEntity usuarioPorCorreo(String correo) {
		return usuarioRepository.findByCorreo(correo);
	}

}
