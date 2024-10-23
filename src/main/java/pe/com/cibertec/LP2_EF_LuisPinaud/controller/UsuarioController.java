package pe.com.cibertec.LP2_EF_LuisPinaud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import pe.com.cibertec.LP2_EF_LuisPinaud.model.UsuarioEntity;
import pe.com.cibertec.LP2_EF_LuisPinaud.service.UsuarioService;

@Controller
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/registrar_usuario")
	public String vistaRegistrarUsuario(Model model) {
		
		model.addAttribute("usuario", new UsuarioEntity());
		return "registrar_usuario";
	}
	
	@PostMapping("/registrar_usuario")
	public String registrarUsuario(@ModelAttribute("usuario") UsuarioEntity usuarioEntity,
			Model model, @RequestParam("foto") MultipartFile foto) {
		usuarioService.crearUsuario(usuarioEntity, foto);
		return "registrar_usuario";
	}
	
	@GetMapping("/")
	public String vistaLogin(Model model) {
		model.addAttribute("usuario", new UsuarioEntity());
		return "login";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("usuario") UsuarioEntity usuarioIngresado,
			Model model, HttpSession session) {
		
		boolean usuarioValidado = usuarioService.validacion(usuarioIngresado);
		if(usuarioValidado) {
			session.setAttribute("usuario", usuarioIngresado.getCorreo());
			return "redirect:/lista_producto";
		}
		
		model.addAttribute("loginInvalido","Credenciales incorrectas");
		model.addAttribute("usuario", new UsuarioEntity());
		return "login";
	}
	
}
