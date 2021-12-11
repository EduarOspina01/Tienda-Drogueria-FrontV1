package Front.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.csvreader.CsvReader;

import Front.modelo.Clientes;
import Front.modelo.ClientesJSON;
import Front.modelo.Productos;
import Front.modelo.ProductosJSON;
import Front.modelo.Proveedores;
import Front.modelo.ProveedoresJSON;
import Front.modelo.Usuarios;

@WebServlet("/ControladorProductos")
@MultipartConfig
public class ControladorProductos extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ControladorProductos() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		Usuarios sesion = Controlador.usuarioGlb;
		String Listar = request.getParameter("Listar");
		String codigo = request.getParameter("codigo");
		String iva = request.getParameter("iva");
		String nit = request.getParameter("NIT");
		String nombre_prod = request.getParameter("nombre_prod");
		String precio_c = request.getParameter("precio_c");
		String precio_v = request.getParameter("precio_v");
		String Agregar = request.getParameter("Agregar");
		String consultar = request.getParameter("Buscar");
		String eliminar = request.getParameter("Eliminar");
		String modificar = request.getParameter("Actualizar");

		if (Listar != null) {
			try {
				ArrayList<Productos> lista = ProductosJSON.getJSON(sesion.getCodigo_ciudad());
				request.setAttribute("lista", lista);
				request.getRequestDispatcher("Productos.jsp").forward(request, response);
			} catch (Exception e) {
				out.println("Catch :(");
				// TODO: handle exception
			}
		}
		if (consultar != null) {
			if (codigo != "" && codigo != null) {
				boolean existe = false;
				try {
					ArrayList<Productos> lista = ProductosJSON.getJSON(sesion.getCodigo_ciudad());
					for (Productos producto_prueba : lista) {
						if (producto_prueba.getCodigo_producto() == Long.parseLong(codigo)) {
							existe = true;
						}
					}
					if (!existe) {
						request.setAttribute("validacion", 8);// El usuario no existe
						request.getRequestDispatcher("/Productos.jsp").forward(request, response);
					} else {
						ArrayList<Productos> listaid = ProductosJSON.getforIdJSON(codigo,sesion.getCodigo_ciudad());
						for (Productos items : listaid) {
							request.setAttribute("producto", items);
							request.getRequestDispatcher("Productos.jsp").forward(request, response);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					out.println("Catch :(");
					// TODO: handle exception
				}
			} else {
				request.setAttribute("validacion", 0);// Ingrese el campo cedula
				request.getRequestDispatcher("/Productos.jsp").forward(request, response);
			}
		}
		if (Agregar != null) {
			if (codigo != "" && iva != "" && nit != "" && nombre_prod != "" && precio_c != "" && codigo != null
					&& iva != null && nit != null && nombre_prod != null && precio_c != null && precio_v != ""
					&& precio_v != null) {
				boolean existe = false;
				boolean existe2 = false;
				try {
					ArrayList<Productos> listaid = ProductosJSON.getforIdJSON(codigo,sesion.getCodigo_ciudad());
					for (Productos producto_prueba : listaid) {
						if (producto_prueba.getCodigo_producto() == Long.parseLong(codigo)) {
							existe = true;
						}
					}
				} catch (Exception e) {
					out.println("Catch :(");
					// TODO: handle exception
				}
				try {
					ArrayList<Proveedores> listaprov = ProveedoresJSON.getJSON(sesion.getCodigo_ciudad());
					for (Proveedores proveedor_prueba : listaprov) {
						if (proveedor_prueba.getNitproveedor() == Long.parseLong(nit)) {
							existe2 = true;
						}
					}

				} catch (Exception e) {
					// TODO: handle exception
				}
				if (!existe) {
					if (existe2) {
						Productos producto = new Productos();
						producto.setCodigo_producto(Long.parseLong(codigo));
						producto.setIvacompra(Double.parseDouble(iva));
						producto.setNitproveedor(Long.parseLong(nit));
						producto.setNombre_producto(nombre_prod);
						producto.setPrecio_compra(Double.parseDouble(precio_c));
						producto.setPrecio_venta(Double.parseDouble(precio_v));
						int creado = ProductosJSON.postJSON(producto,sesion.getCodigo_ciudad());
						System.out.println(creado);
						if (creado == 200) {
							request.setAttribute("validacion", 2);// producto Creado
							request.getRequestDispatcher("/Productos.jsp").forward(request, response);
						} else {
							request.setAttribute("validacion", 3);// Ha habido un error
							request.getRequestDispatcher("/Productos.jsp").forward(request, response);
						}
					} else {
						request.setAttribute("validacion", 10);// El Nit no existe
						request.getRequestDispatcher("/Productos.jsp").forward(request, response);
					}
				} else {
					request.setAttribute("validacion", 4);// El producto ya existe
					request.getRequestDispatcher("/Productos.jsp").forward(request, response);
				}
			} else {
				request.setAttribute("validacion", 1);// Ingrese todos los campos
				request.getRequestDispatcher("/Productos.jsp").forward(request, response);
			}
		}
		if (eliminar != null) {
			if (codigo != "" && codigo != null) {
				boolean existe = false;
				try {
					ArrayList<Productos> lista = ProductosJSON.getJSON(sesion.getCodigo_ciudad());
					for (Productos producto_prueba : lista) {
						if (producto_prueba.getCodigo_producto() == Long.parseLong(codigo)) {
							existe = true;
						}
					}
					if (!existe) {
						request.setAttribute("validacion", 8);// El usuario no existe
						request.getRequestDispatcher("/Productos.jsp").forward(request, response);
					} else {
						int borrado = ProductosJSON.deleteJSON(codigo,sesion.getCodigo_ciudad());
						if (borrado == 200) {
							request.setAttribute("validacion", 5);// Usuario Borrado
							request.getRequestDispatcher("/Productos.jsp").forward(request, response);
						} else {
							request.setAttribute("validacion", 3);// Ha habido un error
							request.getRequestDispatcher("/Productos.jsp").forward(request, response);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					out.println("Catch :(");
					// TODO: handle exception
				}
			} else {
				request.setAttribute("validacion", 0);// Ingrese el campo cedula
				request.getRequestDispatcher("/Productos.jsp").forward(request, response);
			}
		}
		if (modificar != null) {
			if (codigo != "" && iva != "" && nit != "" && nombre_prod != "" && precio_c != "" && codigo != null
					&& iva != null && nit != null && nombre_prod != null && precio_c != null && precio_v != ""
					&& precio_v != null) {
				boolean existe = false;
				boolean existe2 = false;
				try {
					ArrayList<Productos> listaid = ProductosJSON.getforIdJSON(codigo,sesion.getCodigo_ciudad());
					for (Productos producto_prueba : listaid) {
						if (producto_prueba.getCodigo_producto() == Long.parseLong(codigo)) {
							existe = true;
						}
					}
				} catch (Exception e) {
					out.println("Catch :(");
					// TODO: handle exception
				}
				try {
					ArrayList<Proveedores> listaprov = ProveedoresJSON.getJSON(sesion.getCodigo_ciudad());
					for (Proveedores proveedor_prueba : listaprov) {
						if (proveedor_prueba.getNitproveedor() == Long.parseLong(nit)) {
							existe2 = true;
						}
					}

				} catch (Exception e) {
					// TODO: handle exception
				}
				if (existe) {
					if (existe2) {
						Productos producto = new Productos();
						producto.setCodigo_producto(Long.parseLong(codigo));
						producto.setIvacompra(Double.parseDouble(iva));
						producto.setNitproveedor(Long.parseLong(nit));
						producto.setNombre_producto(nombre_prod);
						producto.setPrecio_compra(Double.parseDouble(precio_c));
						producto.setPrecio_venta(Double.parseDouble(precio_v));
						int creado = ProductosJSON.postJSON(producto,sesion.getCodigo_ciudad());
						System.out.println(creado);
						if (creado == 200) {
							request.setAttribute("validacion", 6);// Usuario Modificado
							request.getRequestDispatcher("/Productos.jsp").forward(request, response);
						} else {
							request.setAttribute("validacion", 3);// Ha habido un error
							request.getRequestDispatcher("/Productos.jsp").forward(request, response);
						}
					} else {
						request.setAttribute("validacion", 11);// El nit no existe no se puede modificar
						request.getRequestDispatcher("/Productos.jsp").forward(request, response);
					}
				} else {
					request.setAttribute("validacion", 7);// El usuario no existe no se puede modificar
					request.getRequestDispatcher("/Productos.jsp").forward(request, response);
				}
			} else {
				request.setAttribute("validacion", 9);// Ingrese todos los campos
				request.getRequestDispatcher("/Productos.jsp").forward(request, response);
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stubs
		PrintWriter out = response.getWriter();
		Usuarios sesion = Controlador.usuarioGlb;
		System.out.println("Entro Sevlet");
		String nomb = request.getParameter("nombre");
		Part arch = request.getPart("archivo");
		String process = request.getParameter("Procesar");

		InputStream is = arch.getInputStream();
		Charset charset = Charset.forName("UTF-8");

		if (process != null) {
			if (ProductosJSON.validarCSV(nomb)) {
				try {
					CsvReader leerproducto = new CsvReader(is, charset);
					leerproducto.readHeaders();
					List<Productos> productos = new ArrayList<Productos>(); // Lista donde se guardara los datos
					while (leerproducto.readRecord()) {
						String codigo = leerproducto.get(0);
						String iva = leerproducto.get(1);
						String nit = leerproducto.get(2);
						String nombre = leerproducto.get(3);
						String compra = leerproducto.get(4);
						String venta = leerproducto.get(5);

						// añade lo leido a una lista tipo productios
						productos.add(new Productos(Long.parseLong(codigo), Double.parseDouble(iva),
								Long.parseLong(nit), nombre, Double.parseDouble(compra), Double.parseDouble(venta)));
					}
					is.close();
					System.out.println("Entro en boton");
					ArrayList<Productos> listafinal = new ArrayList<Productos>();
					try {
						//Compara con los proveedores
						ArrayList<Proveedores> lista = ProveedoresJSON.getJSON(sesion.getCodigo_ciudad());
						for (Proveedores suplier : lista) {
							for (Productos item : productos) {
								if (suplier.getNitproveedor() == item.getNitproveedor()) {
									listafinal.add(item);
								}
							}
						}
					} catch (Exception e) {
						System.out.println("Entro catch");
						out.println("Catch :(");
						// TODO: handle exception
					}
					if(listafinal.isEmpty()) {
						System.out.println("los registros no se cargaron");
						request.setAttribute("error", 4); // Registros no se cargaron
						request.getRequestDispatcher("/Productos.jsp").forward(request, response);
					}else{
						try {	
							ArrayList<Productos> inventario = ProductosJSON.getJSON(sesion.getCodigo_ciudad());
							if(inventario.isEmpty()) {
								boolean error = false;
								for(Productos item: listafinal) {
									int validacion = ProductosJSON.postJSON(item,sesion.getCodigo_ciudad());
									if(validacion != 200) {
										error = true;
									}
								}
								if(!error) {
									System.out.println("carga existosa");
									request.setAttribute("error", 2); // carga exitosa de csv
									request.getRequestDispatcher("/Productos.jsp").forward(request, response);
								}
							}else {
								ArrayList<Productos> existentes = new ArrayList<Productos>();
								boolean existe = false;
								boolean error = false;
								for(Productos item: listafinal) {
									for(Productos inv: inventario) {
										if(item.getCodigo_producto() == inv.getCodigo_producto()) {
											existentes.add(inv);
											existe = true;
											int validacion = ProductosJSON.postJSON(item,sesion.getCodigo_ciudad());
											if(validacion != 200) {
												error = true;
											}
										}else {
											int validacion = ProductosJSON.postJSON(item,sesion.getCodigo_ciudad());
											if(validacion != 200) {
												error = true;
											}
										}
									}
								}
								if(existe) {
									request.setAttribute("error", 5); // carga exitosa de csv
									request.setAttribute("productos", existentes); //productos existentes
									request.getRequestDispatcher("/Productos.jsp").forward(request, response);
								}else {
									if(!error) {
										System.out.println("carga existosa");
										request.setAttribute("error", 2); // carga exitosa de csv
										request.getRequestDispatcher("/Productos.jsp").forward(request, response);
									}
								}
								
							}
						
						}catch (Exception e) {
							// TODO: handle exception
						}
					}
				} catch (Exception e) {
					System.out.println("Entro datos invalidos");
					request.setAttribute("error", 0);// error datos leidos invalidos
					request.getRequestDispatcher("/Productos.jsp").forward(request, response);
				} finally {
					is.close();
				}
			} else {
				is.close();
				System.out.println("Entro error formato");
				request.setAttribute("error", 3);// error datos leidos invalidos
				request.getRequestDispatcher("Productos.jsp").forward(request, response);
				// request.getRequestDispatcher("/ValidacionProductos?validar=3&error=3").forward(request,
				// response);
			}
		}
	}

	/*
	 * File f = new File("C:/Prueba/productos.csv"); FileOutputStream ous = new
	 * FileOutputStream(f); int dato = is.read(); while(dato != -1) {
	 * ous.write(dato); dato = is.read(); }
	 * 
	 * ous.close(); is.close();
	 */

}
