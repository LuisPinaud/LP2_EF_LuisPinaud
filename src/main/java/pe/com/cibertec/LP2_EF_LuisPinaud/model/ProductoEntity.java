package pe.com.cibertec.LP2_EF_LuisPinaud.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_producto")
public class ProductoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="producto_id")
	private Integer productoId;
	@Column(name="nombre_producto", nullable = false)
	private String nombreProducto;
	@Column(name="precio", nullable = false)
	private Double precio;
	@Column(name="stock", nullable = false)
	private Integer stock;
	
	@ManyToOne
	@JoinColumn(name="fk_categoria", nullable = false)
	private CategoriaEntity categoriaEntity;

	public ProductoEntity(Integer productoId, String nombreProducto, Double precio, Integer stock,
			CategoriaEntity categoriaEntity) {
		super();
		this.productoId = productoId;
		this.nombreProducto = nombreProducto;
		this.precio = precio;
		this.stock = stock;
		this.categoriaEntity = categoriaEntity;
	}

	public ProductoEntity() {
		super();
	}

	public Integer getProductoId() {
		return productoId;
	}

	public void setProductoId(Integer productoId) {
		this.productoId = productoId;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public CategoriaEntity getCategoriaEntity() {
		return categoriaEntity;
	}

	public void setCategoriaEntity(CategoriaEntity categoriaEntity) {
		this.categoriaEntity = categoriaEntity;
	}
	
	
}
