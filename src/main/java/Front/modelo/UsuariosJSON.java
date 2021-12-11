package Front.modelo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class UsuariosJSON {

	private static URL url;
	private static String sitiobog = "http://localhost:9000/usuarios_bog";
	private static String sitiomed = "http://localhost:9000/usuarios_med";
	private static String sitiocal = "http://localhost:9000/usuarios_cal";
	//private static String sitio = "http://localhost:8080/back_drogueria-0.0.1-SNAPSHOT/";
	
	/**
	 * Agruega informacion al objeto Usuario de la carpeta modelo
	 * @param json
	 * @return Un ArrayList de tipo Usuario
	 * @throws ParseException
	 */
	public static ArrayList<Usuarios> parsingUsuarios(String json) throws ParseException {//devulve un arraylist
		JSONParser jsonParser = new JSONParser();
		ArrayList<Usuarios> lista = new ArrayList<Usuarios>();
		//JSONArray usuarios = (JSONArray) jsonParser.parse(json);
		JSONArray usuarios = (JSONArray) jsonParser.parse(json);
		Iterator i = usuarios.iterator(); //recorrer la tabla usuario
		while (i.hasNext()) {
			JSONObject innerObj = (JSONObject) i.next();
			Usuarios usuario = new Usuarios();
			usuario.setCedula_usuario(Long.parseLong(innerObj.get("cedula_usuario").toString())); //convertir de String a Long
			usuario.setEmail_usuario(innerObj.get("email_usuario").toString());
			usuario.setNombre_usuario(innerObj.get("nombre_usuario").toString());
			usuario.setPassword(innerObj.get("password").toString());
			usuario.setUsuario(innerObj.get("usuario").toString());
			usuario.setCodigo_ciudad(innerObj.get("codigo_ciudad").toString());
			lista.add(usuario);
		}
		return lista;
	}
	
	/**
	 * Conecta con el back-end segun los atributos definidos esn la clase y llama al metodo parsingUsuarios para 
	 * crear una lista de objetos de tipo Usuarios
	 * @return Un ArrayList de tipo Usuario
	 * @throws IOException
	 * @throws ParseException
	 */
	public static ArrayList<Usuarios> getJSON(String ciudad) throws IOException, ParseException { //devolver un listado JSON

		if(ciudad.equals("BOG")) {
			url = new URL(sitiobog + "/Listar"); //trae el metodo de Usuarios.API
		}else if (ciudad.equals("MED")) {
			url = new URL(sitiomed + "/Listar"); //trae el metodo de Usuarios.API
		}else if (ciudad.equals("CAL")) {
			url = new URL(sitiocal + "/Listar");
		}
		HttpURLConnection http = (HttpURLConnection) url.openConnection();

		http.setRequestMethod("GET");
		http.setRequestProperty("Accept", "application/json");

		InputStream respuesta = http.getInputStream();
		byte[] inp = respuesta.readAllBytes();
		String json = "";

		for (int i = 0; i < inp.length; i++) {
			json += (char) inp[i];
		}
		ArrayList<Usuarios> lista = new ArrayList<Usuarios>();
		lista = parsingUsuarios(json);
		http.disconnect();
		return lista;
	}
	
	public static ArrayList<Usuarios> getforIdJSON(String id, String ciudad) throws IOException, ParseException { //devolver un listado JSON

		if(ciudad.equals("BOG")) {
			url = new URL(sitiobog + "/Listar/"+id); //trae el metodo de Usuarios.API
		}else if (ciudad.equals("MED")) {
			url = new URL(sitiomed + "/Listar/"+id); //trae el metodo de Usuarios.API
		}else if (ciudad.equals("CAL")) {
			url = new URL(sitiocal + "/Listar/"+id);
		}
		HttpURLConnection http = (HttpURLConnection) url.openConnection();

		http.setRequestMethod("GET");
		http.setRequestProperty("Accept", "application/json");

		InputStream respuesta = http.getInputStream();
		byte[] inp = respuesta.readAllBytes();
		
		String json = "[";

		for (int i = 0; i < inp.length; i++) {
			json += (char) inp[i];
		}
		json = json + "]";
		ArrayList<Usuarios> lista = new ArrayList<Usuarios>();
		lista = parsingUsuarios(json);
		http.disconnect();
		return lista;
	}
	
	/**
	 * Conecta con el Back-end y crea en la base de datos segun un objeto de tipo Usuarios
	 * @param usuario
	 * @return
	 * @throws IOException
	 */
	public static int postJSON(Usuarios usuario,String ciudad) throws IOException {

		if(ciudad.equals("BOG")) {
			url = new URL(sitiobog + "/Crear"); //trae el metodo de Usuarios.API
		}else if (ciudad.equals("MED")) {
			url = new URL(sitiomed + "/Crear"); //trae el metodo de Usuarios.API
		}else if (ciudad.equals("CAL")) {
			url = new URL(sitiocal + "/Crear");
		}
		HttpURLConnection http;
		http = (HttpURLConnection) url.openConnection();

		try {
			http.setRequestMethod("POST");
		} catch (ProtocolException e) {
			e.printStackTrace();
		}

		http.setDoOutput(true);
		http.setRequestProperty("Accept", "application/json");
		http.setRequestProperty("Content-Type", "application/json");

		String data = "{" + "\"cedula_usuario\":\"" + String.valueOf(usuario.getCedula_usuario())
				+ "\",\"email_usuario\": \"" + usuario.getEmail_usuario() + "\",\"nombre_usuario\": \""
				+ usuario.getNombre_usuario() + "\",\"password\":\"" + usuario.getPassword() + "\",\"usuario\":\""
				+ usuario.getUsuario() +"\",\"codigo_ciudad\":\"" + usuario.getCodigo_ciudad() + "\"}";
		System.out.println(data);
		byte[] out = data.getBytes(StandardCharsets.UTF_8);
		OutputStream stream = http.getOutputStream();
		stream.write(out);

		int respuesta = http.getResponseCode();
		http.disconnect();
		return respuesta;
	}
	
	public static int deleteJSON(String id, String ciudad) throws IOException {

		if(ciudad.equals("BOG")) {
			url = new URL(sitiobog + "/Eliminar/"+id); //trae el metodo de Usuarios.API
		}else if (ciudad.equals("MED")) {
			url = new URL(sitiomed + "/Eliminar/"+id); //trae el metodo de Usuarios.API
		}else if (ciudad.equals("CAL")) {
			url = new URL(sitiocal + "/Eliminar/"+id);
		}	
		
		HttpURLConnection http = (HttpURLConnection) url.openConnection();


		try {
			http.setRequestMethod("DELETE");
		} catch (ProtocolException e) {
			e.printStackTrace();
		}

		http.setDoOutput(true);
		http.setRequestProperty("Accept", "application/json");
		http.setRequestProperty("Content-Type", "application/json");

		int respuesta = http.getResponseCode();
		http.disconnect();
		return respuesta;
	}
	
	public static void main(String[] args) {	
		
		ArrayList<Usuarios> user = new ArrayList<Usuarios>();
		try {
			user = getJSON("BOG");
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Usuarios vista: user) {
			System.out.println(vista.getNombre_usuario());
			System.out.println(vista.getCodigo_ciudad());
			System.out.println(vista.getUsuario());
		}
	}
}
