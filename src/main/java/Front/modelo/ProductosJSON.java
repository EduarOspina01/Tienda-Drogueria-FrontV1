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

public class ProductosJSON {
	
	private static URL url;
	private static String sitio = "http://localhost:5000/";
	private static String sitiobog = "http://localhost:9000/productos_bog";
	private static String sitiomed = "http://localhost:9000/productos_med";
	private static String sitiocal = "http://localhost:9000/productos_cal";
	//private static String sitio = "http://localhost:8080/back_drogueria-0.0.1-SNAPSHOT/";
	
	
	public static ArrayList<Productos> parsingUsuarios(String json) throws ParseException {//devulve un arraylist
		JSONParser jsonParser = new JSONParser();
		ArrayList<Productos> lista = new ArrayList<Productos>();
		JSONArray producto = (JSONArray) jsonParser.parse(json);
		Iterator i = producto.iterator(); //recorrer la tabla proveedor
		while (i.hasNext()) {
			JSONObject innerObj = (JSONObject) i.next();
			Productos productos = new Productos();
			productos.setCodigo_producto(Long.parseLong(innerObj.get("codigo_producto").toString())); //convertir de String a Long
			productos.setIvacompra(Double.parseDouble(innerObj.get("ivacompra").toString()));
			productos.setNitproveedor(Long.parseLong(innerObj.get("nitproveedor").toString()));
			productos.setNombre_producto(innerObj.get("nombre_producto").toString());
			productos.setPrecio_compra(Double.parseDouble(innerObj.get("precio_compra").toString()));
			productos.setPrecio_venta(Double.parseDouble(innerObj.get("precio_venta").toString()));
			lista.add(productos);
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
	public static ArrayList<Productos> getJSON(String ciudad) throws IOException, ParseException { //devolver un listado JSON

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
		ArrayList<Productos> lista = new ArrayList<Productos>();
		lista = parsingUsuarios(json);
		http.disconnect();
		return lista;
	}
	
	public static ArrayList<Productos> getforIdJSON(String id,String ciudad) throws IOException, ParseException { //devolver un listado JSON

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
		ArrayList<Productos> lista = new ArrayList<Productos>();
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
	public static int postJSON(Productos productos,String ciudad) throws IOException {

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

		String data = "{" + "\"codigo_producto\":\"" + String.valueOf(productos.getCodigo_producto())
				+ "\",\"ivacompra\": \"" + String.valueOf(productos.getIvacompra()) + "\",\"nitproveedor\": \""
				+ String.valueOf(productos.getNitproveedor()) + "\",\"nombre_producto\":\"" + productos.getNombre_producto() + "\",\"precio_compra\":\""
				+ String.valueOf(productos.getPrecio_compra()) + "\",\"precio_venta\":\""+ String.valueOf(productos.getPrecio_venta()) +"\"}";
		byte[] out = data.getBytes(StandardCharsets.UTF_8);
		OutputStream stream = http.getOutputStream();
		stream.write(out);

		int respuesta = http.getResponseCode();
		http.disconnect();
		return respuesta;
	}
	
	public static int deleteJSON(String id,String ciudad) throws IOException {

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
	
	public static boolean validarCSV(String nombreArchivo) {
		int contador = 0;
        char valdador;
        for (int i = 0; i < nombreArchivo.length(); i++) {
        	valdador = nombreArchivo.charAt(i);
            if (valdador == '.')
            	contador++;
        }
        
        if (contador<2) {
        	if(nombreArchivo.contains(".csv")) {
    			return true;
    		}else {
    			return false;
    		}
        }else{
        	return false;
        }
	}
	
	public static void main(String[] args) {
		ArrayList<Productos> inventario = null;
		try {
			inventario = ProductosJSON.getJSON("BOG");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(inventario.isEmpty()) {
			System.out.println("Vacio");
		}else {
			System.out.println(inventario.toString());
		}
		}
}
