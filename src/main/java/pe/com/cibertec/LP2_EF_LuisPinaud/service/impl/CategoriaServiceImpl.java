package pe.com.cibertec.LP2_EF_LuisPinaud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.cibertec.LP2_EF_LuisPinaud.model.CategoriaEntity;
import pe.com.cibertec.LP2_EF_LuisPinaud.repository.CategoriaRepository;
import pe.com.cibertec.LP2_EF_LuisPinaud.service.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Override
	public List<CategoriaEntity> obtenerCategorias() {
	
		return categoriaRepository.findAll();
	}

}
