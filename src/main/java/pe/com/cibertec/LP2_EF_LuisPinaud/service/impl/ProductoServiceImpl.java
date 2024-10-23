package pe.com.cibertec.LP2_EF_LuisPinaud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.cibertec.LP2_EF_LuisPinaud.model.ProductoEntity;
import pe.com.cibertec.LP2_EF_LuisPinaud.repository.ProductoRepository;
import pe.com.cibertec.LP2_EF_LuisPinaud.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService {
	@Autowired
	private ProductoRepository productoRepository;

	@Override
	public List<ProductoEntity> listarProducto() {
		
		return productoRepository.findAll();
	}

	@Override
	public void crearProducto(ProductoEntity productoEntity) {
		
		productoRepository.save(productoEntity);
	}

	@Override
	public ProductoEntity buscarProductoPorId(Integer id) {
		
		return productoRepository.findById(id).get();
	}

	@Override
	public void actualizarProducto(Integer id, ProductoEntity productoEntity) {
		
		ProductoEntity encontradoPro = buscarProductoPorId(id);
		if(encontradoPro == null) {
			throw new RuntimeException("Producto no encontrado");
		}
		
		try {
			encontradoPro.setNombreProducto(productoEntity.getNombreProducto());
			encontradoPro.setPrecio(productoEntity.getPrecio());
			encontradoPro.setStock(productoEntity.getStock());
			encontradoPro.setCategoriaEntity(productoEntity.getCategoriaEntity());
			productoRepository.save(encontradoPro);
		} catch (Exception e) {
			throw new RuntimeException("Ocurrió un error al actualizar");
		}
	}

	@Override
	public void eliminarProducto(Integer id) {
		
		ProductoEntity encontradoPro = buscarProductoPorId(id);
		if(encontradoPro != null) {
			productoRepository.delete(encontradoPro);
		}
	}
}
