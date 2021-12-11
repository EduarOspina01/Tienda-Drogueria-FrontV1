package Front.modelo;

public class Ventas {
	
	private long codigo_venta;
	private long cedula_cliente;
	private long cedula_usuario;
	private double iva_venta;
	private double total_venta;
	private double valor_total;
	private String codigo_ciudad;
	
	
	public String getCodigo_ciudad() {
		return codigo_ciudad;
	}
	public void setCodigo_ciudad(String codigo_ciudad) {
		this.codigo_ciudad = codigo_ciudad;
	}
	public long getCodigo_venta() {
		return codigo_venta;
	}
	public void setCodigo_venta(long codigo_venta) {
		this.codigo_venta = codigo_venta;
	}
	public long getCedula_cliente() {
		return cedula_cliente;
	}
	public void setCedula_cliente(long cedula_cliente) {
		this.cedula_cliente = cedula_cliente;
	}
	public long getCedula_usuario() {
		return cedula_usuario;
	}
	public void setCedula_usuario(long cedula_usuario) {
		this.cedula_usuario = cedula_usuario;
	}
	public double getTotal_venta() {
		return total_venta;
	}
	public void setTotal_venta(double total_venta) {
		this.total_venta = total_venta;
	}
	public double getValor_total() {
		return valor_total;
	}
	public void setValor_total(double valor_total) {
		this.valor_total = valor_total;
	}
	public double getIvaventa() {
		return iva_venta;
	}
	public void setIvaventa(double ivaventa) {
		this.iva_venta = ivaventa;
	}
	
	

}
