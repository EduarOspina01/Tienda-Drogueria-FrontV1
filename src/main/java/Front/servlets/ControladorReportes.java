package Front.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Front.modelo.Clientes;
import Front.modelo.ClientesJSON;
import Front.modelo.Usuarios;
import Front.modelo.UsuariosJSON;
import Front.modelo.Ventas;
import Front.modelo.VentasJSON;

@WebServlet("/ControladorReportes")
public class ControladorReportes extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Usuarios sesion;
	int ventas;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControladorReportes() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		

			HttpSession sesionuser = (HttpSession) request.getSession();
			sesionuser.setAttribute("sesion",  Controlador.usuarioGlb);
			sesion = (Usuarios) sesionuser.getAttribute("sesion");
			System.out.println(sesion.getNombre_usuario());
		
		String btnUsuarios = request.getParameter("listar_usuarios");
		String btnClientes = request.getParameter("listar_clientes");
		String btnVentas = request.getParameter("listar_ventas");
		String btnVentas_b = request.getParameter("listar_ventas_b");
		String btnVentas_m = request.getParameter("listar_ventas_m");
		String btnVentas_c = request.getParameter("listar_ventas_c");
		String btnCedCliente = request.getParameter("cedula");
		String btnbog_us = request.getParameter("bog_us");
		String btnmed_us = request.getParameter("med_us");
		String btncal_us = request.getParameter("cal_us");
		String btncons_us = request.getParameter("cons_us");
		String btnbog_cl = request.getParameter("bog_cl");
		String btnmed_cl = request.getParameter("med_cl");
		String btncal_cl = request.getParameter("cal_cl");
		String btncons_cl = request.getParameter("cons_cl");
		
		
		if(btnUsuarios != null) {
			request.setAttribute("reporte", 0);
			request.getRequestDispatcher("Reportes.jsp").forward(request, response);
		}else if (btnClientes != null) {
			request.setAttribute("reporte", 1);
			request.getRequestDispatcher("Reportes.jsp").forward(request, response);
		}else if (btnVentas != null) {
				ArrayList<Ventas> lista = new ArrayList<Ventas>();
				double acumulador = 0;
				try {
					ArrayList<Ventas> listaprov = VentasJSON.getJSON(sesion.getCodigo_ciudad());
					for(Ventas venta : listaprov) {
						lista.add(venta);
					}
				} catch (Exception e) {
					System.out.println("Catch :(");
					System.out.println(e);
					// TODO: handle exception
				}
				if(lista.isEmpty()) {
					request.setAttribute("validacion", 2);
					request.getRequestDispatcher("Reportes.jsp").forward(request, response);
				}else {
					for(Ventas venta : lista) {
						acumulador += venta.getValor_total();
					}
					request.setAttribute("reporte", 3);
					request.setAttribute("lista", lista);
					request.setAttribute("acumulador", acumulador);
					request.getRequestDispatcher("Reportes.jsp").forward(request, response);
				}	
		}else if (btnVentas_b != null) {
			request.setAttribute("reporte", 2);
			request.getRequestDispatcher("Reportes.jsp").forward(request, response);
			ventas = 1;
		}else if (btnVentas_m != null) {
			request.setAttribute("reporte", 2);
			request.getRequestDispatcher("Reportes.jsp").forward(request, response);
			ventas = 2;
		}else if (btnVentas_c != null) {
			request.setAttribute("reporte", 2);
			request.getRequestDispatcher("Reportes.jsp").forward(request, response);
			ventas = 3;
		}else if(btnCedCliente != null) {
			if (ventas == 1) {
				String cedula = request.getParameter("valor_cedula");
				ArrayList<Ventas> lista = new ArrayList<Ventas>();
				double acumulador = 0;
				try {
					ArrayList<Ventas> listaprov = VentasJSON.getJSON(sesion.getCodigo_ciudad());
					for(Ventas venta : listaprov) {
						if(venta.getCedula_cliente()==Long.parseLong(cedula)) {
							if(venta.getCodigo_ciudad().equals("BOG")) {
								lista.add(venta);
							}
						}
					}
				} catch (Exception e) {
					System.out.println("Catch :(");
					System.out.println(e);
					// TODO: handle exception
				}
				if(lista.isEmpty()) {
					request.setAttribute("validacion", 2);
					request.getRequestDispatcher("Reportes.jsp").forward(request, response);
				}else {
					for(Ventas venta : lista) {
						acumulador += venta.getValor_total();
					}
					request.setAttribute("reporte", 2);
					request.setAttribute("lista", lista);
					request.setAttribute("acumulador", acumulador);
					request.getRequestDispatcher("Reportes.jsp").forward(request, response);
				}
			}else if(ventas == 2) {
				String cedula = request.getParameter("valor_cedula");
				ArrayList<Ventas> lista = new ArrayList<Ventas>();
				double acumulador = 0;
				try {
					ArrayList<Ventas> listaprov = VentasJSON.getJSON(sesion.getCodigo_ciudad());
					for(Ventas venta : listaprov) {
						if(venta.getCedula_cliente()==Long.parseLong(cedula)) {
							if(venta.getCodigo_ciudad().equals("MED")) {
								lista.add(venta);
							}
						}
					}
				} catch (Exception e) {
					System.out.println("Catch :(");
					System.out.println(e);
					// TODO: handle exception
				}
				if(lista.isEmpty()) {
					request.setAttribute("validacion", 2);
					request.getRequestDispatcher("Reportes.jsp").forward(request, response);
				}else {
					for(Ventas venta : lista) {
						acumulador += venta.getValor_total();
					}
					request.setAttribute("reporte", 2);
					request.setAttribute("lista", lista);
					request.setAttribute("acumulador", acumulador);
					request.getRequestDispatcher("Reportes.jsp").forward(request, response);
				}
			}else if (ventas == 3) {
				String cedula = request.getParameter("valor_cedula");
				ArrayList<Ventas> lista = new ArrayList<Ventas>();
				double acumulador = 0;
				try {
					ArrayList<Ventas> listaprov = VentasJSON.getJSON(sesion.getCodigo_ciudad());
					for(Ventas venta : listaprov) {
						if(venta.getCedula_cliente()==Long.parseLong(cedula)) {
							if(venta.getCodigo_ciudad().equals("CAL")) {
								lista.add(venta);
							}
						}
					}
				} catch (Exception e) {
					System.out.println("Catch :(");
					System.out.println(e);
					// TODO: handle exception
				}
				if(lista.isEmpty()) {
					request.setAttribute("validacion", 2);
					request.getRequestDispatcher("Reportes.jsp").forward(request, response);
				}else {
					for(Ventas venta : lista) {
						acumulador += venta.getValor_total();
					}
					request.setAttribute("reporte", 2);
					request.setAttribute("lista", lista);
					request.setAttribute("acumulador", acumulador);
					request.getRequestDispatcher("Reportes.jsp").forward(request, response);
				}
			}
		}else if(btnbog_us != null) {
			System.out.println("entro aqui");
			ArrayList<Usuarios> lista = new ArrayList<Usuarios>();
			try {
				ArrayList<Usuarios> listaprov = UsuariosJSON.getJSON(sesion.getCodigo_ciudad());
				for(Usuarios user : listaprov) {
					System.out.println("entro aqui");
					if(user.getCodigo_ciudad().equals("BOG")) {
						lista.add(user);
					}
				}
				if(lista.isEmpty()) {
					request.setAttribute("validacion", 0);
					request.getRequestDispatcher("Reportes.jsp").forward(request, response);
				}else {
					request.setAttribute("reporte", 0);
					request.setAttribute("lista", lista);
					request.getRequestDispatcher("Reportes.jsp").forward(request, response);
				}
			} catch (Exception e) {
				System.out.println("Catch :(");
				// TODO: handle exception
			}
		}
		else if (btnmed_us != null) {
			ArrayList<Usuarios> lista = new ArrayList<Usuarios>();
			try {
				ArrayList<Usuarios> listaprov = UsuariosJSON.getJSON(sesion.getCodigo_ciudad());
				for(Usuarios user : listaprov) {
					if(user.getCodigo_ciudad().equals("MED")) {
						lista.add(user);
					}
				}
				if(lista.isEmpty()) {
					request.setAttribute("validacion", 0);
					request.getRequestDispatcher("Reportes.jsp").forward(request, response);
				}else {
					request.setAttribute("reporte", 0);
					request.setAttribute("lista", lista);
					request.getRequestDispatcher("Reportes.jsp").forward(request, response);
				}
			} catch (Exception e) {
				System.out.println("Catch :(");
				// TODO: handle exception
			}
		}
		else if (btncal_us != null) {
			ArrayList<Usuarios> lista = new ArrayList<Usuarios>();
			try {
				ArrayList<Usuarios> listaprov = UsuariosJSON.getJSON(sesion.getCodigo_ciudad());
				for(Usuarios user : listaprov) {
					if(user.getCodigo_ciudad().equals("CAL")) {
						lista.add(user);
					}
				}
				if(lista.isEmpty()) {
					request.setAttribute("validacion", 0);
					request.getRequestDispatcher("Reportes.jsp").forward(request, response);
				}else {
					request.setAttribute("reporte", 0);
					request.setAttribute("lista", lista);
					request.getRequestDispatcher("Reportes.jsp").forward(request, response);
				}
			} catch (Exception e) {
				System.out.println("Catch :(");
				// TODO: handle exception
			}
		}
		else if (btncons_us != null) {
			ArrayList<Usuarios> lista = new ArrayList<Usuarios>();
			try {
				ArrayList<Usuarios> listaprov = UsuariosJSON.getJSON(sesion.getCodigo_ciudad());
				for(Usuarios user : listaprov) {
					lista.add(user);
				}
				if(lista.isEmpty()) {
					request.setAttribute("validacion", 0);
					request.getRequestDispatcher("Reportes.jsp").forward(request, response);
				}else {
					request.setAttribute("reporte",0);
					request.setAttribute("lista", lista);
					request.getRequestDispatcher("Reportes.jsp").forward(request, response);
				}
			} catch (Exception e) {
				System.out.println("Catch :(");
				// TODO: handle exception
			}
		}else if(btnbog_cl != null) {
			ArrayList<Clientes> lista = new ArrayList<Clientes>();
			try {
				ArrayList<Clientes> listaprov = ClientesJSON.getJSON(sesion.getCodigo_ciudad());
				for(Clientes user : listaprov) {
					if(user.getCodigo_ciudad().equals("BOG")) {
						lista.add(user);
					}
				}
				if(lista.isEmpty()) {
					request.setAttribute("validacion", 0);
					request.getRequestDispatcher("Reportes.jsp").forward(request, response);
				}else {
					request.setAttribute("reporte", 1);
					request.setAttribute("lista", lista);
					request.getRequestDispatcher("Reportes.jsp").forward(request, response);
				}
			} catch (Exception e) {
				System.out.println("Catch :(");
				// TODO: handle exception
			}
		}else if (btnmed_cl != null) {
			ArrayList<Clientes> lista = new ArrayList<Clientes>();
			try {
				ArrayList<Clientes> listaprov = ClientesJSON.getJSON(sesion.getCodigo_ciudad());
				for(Clientes user : listaprov) {
					if(user.getCodigo_ciudad().equals("MED")) {
						lista.add(user);
					}
				}
				if(lista.isEmpty()) {
					request.setAttribute("validacion", 0);
					request.getRequestDispatcher("Reportes.jsp").forward(request, response);
				}else {
					request.setAttribute("reporte", 1);
					request.setAttribute("lista", lista);
					request.getRequestDispatcher("Reportes.jsp").forward(request, response);
				}
			} catch (Exception e) {
				System.out.println("Catch :(");
				// TODO: handle exception
			}
		} else if (btncal_cl != null) {
			ArrayList<Clientes> lista = new ArrayList<Clientes>();
			try {
				ArrayList<Clientes> listaprov = ClientesJSON.getJSON(sesion.getCodigo_ciudad());
				for(Clientes user : listaprov) {
					if(user.getCodigo_ciudad().equals("CAL")) {
						lista.add(user);
					}
				}
				if(lista.isEmpty()) {
					request.setAttribute("validacion", 0);
					request.getRequestDispatcher("Reportes.jsp").forward(request, response);
				}else {
					request.setAttribute("reporte", 1);
					request.setAttribute("lista", lista);
					request.getRequestDispatcher("Reportes.jsp").forward(request, response);
				}
			} catch (Exception e) {
				System.out.println("Catch :(");
				// TODO: handle exception
			}
		}else if (btncons_cl != null) {
			ArrayList<Clientes> lista = new ArrayList<Clientes>();
			try {
				ArrayList<Clientes> listaprov = ClientesJSON.getJSON(sesion.getCodigo_ciudad());
				for(Clientes user : listaprov) {
					lista.add(user);
				}
				if(lista.isEmpty()) {
					request.setAttribute("validacion", 0);
					request.getRequestDispatcher("Reportes.jsp").forward(request, response);
				}else {
					request.setAttribute("reporte", 1);
					request.setAttribute("lista", lista);
					request.getRequestDispatcher("Reportes.jsp").forward(request, response);
				}
			} catch (Exception e) {
				System.out.println("Catch :(");
				// TODO: handle exception
			}
		}
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
