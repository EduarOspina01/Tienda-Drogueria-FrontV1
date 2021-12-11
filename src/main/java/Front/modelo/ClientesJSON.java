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


public class ClientesJSON {
	
	private static URL url;
	private static String sitiobog = "http://localhost:9000/clientes_bog";
	private static String sitiomed = "http://localhost:9000/clientes_med";
	private static String sitiocal = "http://localhost:9000/clientes_cal";
	//private static String sitio = "http://localhost:8080/back_drogueria-0.0.1-SNAPSHOT/";
	
	/**
	 * Agruega informacion al objeto Usuario de la carpeta modelo
	 * @param json
	 * @return Un ArrayList de tipo Clientes
	 * @throws ParseException
	 */
	public static ArrayList<Clientes> parsingClientes(String json) throws ParseException {//devulve un arraylist
		JSONParser jsonParser = new JSONParser();
		ArrayList<Clientes> lista = new ArrayList<Clientes>();
		JSONArray clientes = (JSONArray) jsonParser.parse(json);
		Iterator i = clientes.iterator(); //recorrer la tabla usuario
		while (i.hasNext()) {
			JSONObject innerObj = (JSONObject) i.next();
			Clientes cliente = new Clientes();
			cliente.setCedula_cliente(Long.parseLong(innerObj.get("cedula_cliente").toString())); //convertir de String a Long
			cliente.setDireccion_cliente(innerObj.get("direccion_cliente").toString());
			cliente.setEmail_cliente(innerObj.get("email_cliente").toString());
			cliente.setNombre_cliente(innerObj.get("nombre_cliente").toString());
			cliente.setTelefono_cliente(innerObj.get("telefono_cliente").toString());
			cliente.setCodigo_ciudad(innerObj.get("codigo_ciudad").toString());
			lista.add(cliente);
		}
		return lista;
	}
	
	/**
	 * Conecta con el back-end segun los atributos definidos esn la clase y llama al metodo parsingUsuarios para 
	 * crear una lista de objetos de tipo Clientes
	 * @return Un ArrayList de tipo Clientes
	 * @throws IOException
	 * @throws ParseException
	 */
	public static ArrayList<Clientes> getJSON(String ciudad) throws IOException, ParseException { //devolver un listado JSON
		
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
		System.out.println(json);
		ArrayList<Clientes> lista = new ArrayList<Clientes>();
		lista = parsingClientes(json);
		http.disconnect();
		return lista;
	}
	
	public static ArrayList<Clientes> getforIdJSON(String id, String ciudad) throws IOException, ParseException { //devolver un listado JSON

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
		ArrayList<Clientes> lista = new ArrayList<Clientes>();
		lista = parsingClientes(json);
		http.disconnect();
		return lista;
	}
	
	/**
	 * Conecta con el Back-end y crea en la base de datos segun un objeto de tipo Clientes
	 * @param cliente
	 * @return
	 * @throws IOException
	 */
	public static int postJSON(Clientes cliente, String ciudad) throws IOException {

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

		String data = "{" + "\"cedula_cliente\":\"" + String.valueOf(cliente.getCedula_cliente())
				+ "\",\"direccion_cliente\": \"" + cliente.getDireccion_cliente() + "\",\"email_cliente\": \""
				+ cliente.getEmail_cliente() + "\",\"nombre_cliente\":\"" + cliente.getNombre_cliente() + "\",\"telefono_cliente\":\""
				+ cliente.getTelefono_cliente() +"\",\"codigo_ciudad\":\"" + cliente.getCodigo_ciudad() + "\"}";
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
		
		try {
			int result = deleteJSON("11", "BOG");
			System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
