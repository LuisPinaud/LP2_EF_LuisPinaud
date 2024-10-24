package pe.com.cibertec.LP2_EF_LuisPinaud.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import pe.com.cibertec.LP2_EF_LuisPinaud.model.CategoriaEntity;
import pe.com.cibertec.LP2_EF_LuisPinaud.model.ProductoEntity;
import pe.com.cibertec.LP2_EF_LuisPinaud.model.UsuarioEntity;
import pe.com.cibertec.LP2_EF_LuisPinaud.service.CategoriaService;
import pe.com.cibertec.LP2_EF_LuisPinaud.service.ProductoService;
import pe.com.cibertec.LP2_EF_LuisPinaud.service.UsuarioService;
import pe.com.cibertec.LP2_EF_LuisPinaud.service.impl.PdfService;

@Controller
public class ProductoController {
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private PdfService pdfService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/lista_producto")
	public String listarProductos(Model model, HttpSession sesion) {
		if(sesion.getAttribute("usuario")==null) {
			return "redirect:/";
		}
		String correoS = sesion.getAttribute("usuario").toString();
		UsuarioEntity usuarioObtenido = usuarioService.usuarioPorCorreo(correoS);
		model.addAttribute("foto", usuarioObtenido.getUrlImagen());
		model.addAttribute("nombreUsuario", usuarioObtenido.getNombre());
		
		List<ProductoEntity> listaPro = productoService.listarProducto();
		model.addAttribute("listaprod", listaPro);
		return "lista_producto";
	}
	
	@GetMapping("/registrar_producto")
	public String vistaRegistrarProducto(Model model, HttpSession sesion) {
		String correoS = sesion.getAttribute("usuario").toString();
		UsuarioEntity usuarioObtenido = usuarioService.usuarioPorCorreo(correoS);
		model.addAttribute("foto", usuarioObtenido.getUrlImagen());
		model.addAttribute("nombreUsuario", usuarioObtenido.getNombre());
		
		List<CategoriaEntity>listCategoria = categoriaService.obtenerCategorias();
		model.addAttribute("categorias", listCategoria);
		model.addAttribute("producto", new ProductoEntity());
		return "registrar_producto";
	}
	
	@PostMapping("/registrar_producto")
	public String registrarProducto(@ModelAttribute("producto")ProductoEntity productoEntity,
			Model model) {
		productoService.crearProducto(productoEntity);
		return "redirect:/lista_producto";
	}
	
	@GetMapping("/ver_producto/{id}")
	public String verDetalle(Model model, @PathVariable("id")Integer id, HttpSession sesion) {
		String correoS = sesion.getAttribute("usuario").toString();
		UsuarioEntity usuarioObtenido = usuarioService.usuarioPorCorreo(correoS);
		model.addAttribute("foto", usuarioObtenido.getUrlImagen());
		model.addAttribute("nombreUsuario", usuarioObtenido.getNombre());
		
		ProductoEntity encontradoPro = productoService.buscarProductoPorId(id);
		model.addAttribute("producto", encontradoPro);
		return "vista_producto";
	}
	
	@GetMapping("/delete/{id}")
	public String eliminarProducto(@PathVariable("id")Integer id) {
		productoService.eliminarProducto(id);
		return "redirect:/lista_producto";
	}
	
	@GetMapping("/editar_producto/{id}")
	public String vistaModificarProducto(@PathVariable("id") Integer id, Model model, HttpSession sesion) {
		String correoS = sesion.getAttribute("usuario").toString();
		UsuarioEntity usuarioObtenido = usuarioService.usuarioPorCorreo(correoS);
		model.addAttribute("foto", usuarioObtenido.getUrlImagen());
		model.addAttribute("nombreUsuario", usuarioObtenido.getNombre());
		
		ProductoEntity encontradoPro = productoService.buscarProductoPorId(id);
		List<CategoriaEntity> listaCategoria = categoriaService.obtenerCategorias();
		model.addAttribute("categorias", listaCategoria);
		model.addAttribute("proEncontrado", encontradoPro);
		return "editar_producto";
	}
	
	@PostMapping("/editar_producto/{id}")
	public String modificarProducto(@PathVariable("id")Integer id, 
			@ModelAttribute("proEncontrado") ProductoEntity modificado, Model model) {
		
		productoService.actualizarProducto(id, modificado);
		return "redirect:/lista_producto";
	}
	
	@GetMapping("/generar_pdf")
	public ResponseEntity<InputStreamResource>generarPDF(HttpSession sesion)throws IOException{
		
	    List<ProductoEntity> listaProductos = productoService.listarProducto();
	    String correoS = sesion.getAttribute("usuario").toString();
		UsuarioEntity usuarioObtenido = usuarioService.usuarioPorCorreo(correoS);
		
		
	    
	    Map<String, Object> datosPdf = new HashMap<>();
	    datosPdf.put("productos", listaProductos);
	    datosPdf.put("nombreUsuario", usuarioObtenido.getNombre());
	    
	    ByteArrayInputStream pdfBytes = pdfService.generarPdf("template_pdf", datosPdf);
	    
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "inline; filename=productos.pdf");
	    
	    
	    return ResponseEntity.ok()
	            .headers(headers)
	            .contentType(MediaType.APPLICATION_PDF)
	            .body(new InputStreamResource(pdfBytes));
	}
}
