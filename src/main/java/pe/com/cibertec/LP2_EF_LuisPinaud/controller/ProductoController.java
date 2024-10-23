package pe.com.cibertec.LP2_EF_LuisPinaud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import pe.com.cibertec.LP2_EF_LuisPinaud.model.CategoriaEntity;
import pe.com.cibertec.LP2_EF_LuisPinaud.model.ProductoEntity;
import pe.com.cibertec.LP2_EF_LuisPinaud.service.CategoriaService;
import pe.com.cibertec.LP2_EF_LuisPinaud.service.ProductoService;

@Controller
public class ProductoController {
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private ProductoService productoService;
	
	@GetMapping("/lista_producto")
	public String listarProductos(Model model, HttpSession sesion) {
		if(sesion.getAttribute("usuario")==null) {
			return "redirect:/";
		}
		List<ProductoEntity> listaPro = productoService.listarProducto();
		model.addAttribute("listaprod", listaPro);
		return "lista_producto";
	}
	
	@GetMapping("/registrar_producto")
	public String vistaRegistrarProducto(Model model) {
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
}
