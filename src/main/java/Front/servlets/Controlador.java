package Front.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.ParseException;

import Front.modelo.Usuarios;
import Front.modelo.UsuariosJSON;


@WebServlet("/Controlador")
public class Controlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
    String administrador = "admininicial";
    String contrasena = "admin123456";
    static Usuarios usuarioGlb = null;

    public Controlador() {
        super();
        // TODO Auto-generated constructor stub
    } 


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String accion = request.getParameter("accion");
		PrintWriter out = response.getWriter();
		if(accion.equals("Ingresar")) {
			this.validarUsuarios(request, response);
		}
		if(accion.equals("Menu")) {
			this.redireccionarMenu(request, response);
		}else {
			out.println("No entro a los IF");
			out.println(accion);
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public void validarUsuarios(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			ArrayList<Usuarios> lista = UsuariosJSON.getJSON("BOG");
			String usuario = "";
			String password = "";
			usuario = request.getParameter("txtUsuario"); // Recibe informacion del formulario
			password = request.getParameter("txtContrasena"); // Recibe informacion del formulario
			if((usuario != "")&& (password != "")) {
				if(usuario.equals(administrador) && password.equals(contrasena) ) {
					Usuarios user = new Usuarios();
					user.setNombre_usuario(administrador);
					user.setUsuario(administrador);
					user.setPassword(contrasena);
					request.getRequestDispatcher("/Controlador?accion=Menu&menu=Principal&usuario").forward(request, response);
				}else {
					for (Usuarios user: lista) {
						if (user.getUsuario().equals(usuario) && user.getPassword().equals(password)) {
							usuarioGlb = user;
							request.getRequestDispatcher("/Controlador?accion=Menu&menu=Principal&usuario").forward(request, response);
						}
					}
					request.setAttribute("validacion",0);
					request.getRequestDispatcher("/Index.jsp").forward(request, response);
				}	
			}else {
				request.setAttribute("validacion",1);
				request.getRequestDispatcher("/Index.jsp").forward(request, response);
				
			}
		} catch (IOException e) {
			out.println(" No entro al try");
			e.printStackTrace();
		} catch (ParseException e) {
			out.println(" No entro al try");
			e.printStackTrace();
		}
	}
	
	public void redireccionarMenu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String menu = request.getParameter("menu");
		if (menu.equals("Principal")) {
			request.setAttribute("sesion",usuarioGlb);
			request.getRequestDispatcher("Menu.jsp").forward(request, response);
		}
		if (menu.equals("Usuario")) {
			request.setAttribute("sesion",usuarioGlb);
			request.getRequestDispatcher("Usuario.jsp").forward(request, response);	
		}
		if (menu.equals("Cliente")) {
			request.setAttribute("sesion",usuarioGlb);
			request.getRequestDispatcher("Cliente.jsp").forward(request, response);
		}
		if (menu.equals("Proveedor")) {
			request.setAttribute("sesion",usuarioGlb);
			request.getRequestDispatcher("Proveedores.jsp").forward(request, response);
		}
		if (menu.equals("Productos")) {
			request.setAttribute("sesion",usuarioGlb);
			request.getRequestDispatcher("Productos.jsp").forward(request, response);
		}
		if (menu.equals("Ventas")) {
			request.setAttribute("sesion",usuarioGlb);
			request.getRequestDispatcher("Ventas.jsp").forward(request, response);
		}
		if (menu.equals("Reportes")) {
			request.setAttribute("sesion",usuarioGlb);
			request.getRequestDispatcher("Reportes.jsp").forward(request, response);
		}
		if (menu.equals("Salir")) {
			usuarioGlb = null;
			request.getRequestDispatcher("Index.jsp").forward(request, response);
		}
	}
}
