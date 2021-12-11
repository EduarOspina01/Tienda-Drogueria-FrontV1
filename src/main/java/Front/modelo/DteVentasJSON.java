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

public class DteVentasJSON {
	
	private static URL url;
	private static String sitio = "http://localhost:5000/";
	private static String sitiobog = "http://localhost:9000/detalle_ventas_bog";
	private static String sitiomed = "http://localhost:9000/detalle_ventas_med";
	private static String sitiocal = "http://localhost:9000/detalle_ventas_cal";
	//private static String sitio = "http://localhost:8080/back_drogueria-0.0.1-SNAPSHOT/";
	
	/**
	 * Agruega informacion al objeto Usuario de la carpeta modelo
	 * @param json
	 * @return Un ArrayList de tipo Usuario
	 * @throws ParseException
	 */
	public static ArrayList<DteVentas> parsingUsuarios(String json) throws ParseException {//devulve un arraylist
		JSONParser jsonParser = new JSONParser();
		ArrayList<DteVentas> lista = new ArrayList<DteVentas>();
		//JSONArray usuarios = (JSONArray) jsonParser.parse(json);
		JSONArray dteventas = (JSONArray) jsonParser.parse(json);
		Iterator i = dteventas.iterator(); //recorrer la tabla usuario
		while (i.hasNext()) {
			JSONObject innerObj = (JSONObject) i.next();
			DteVentas venta = new DteVentas();
			venta.setCodigo_dte(Long.parseLong(innerObj.get("codigo_detalle_venta").toString())); //convertir de String a Long
			venta.setCantidad(Integer.parseInt(innerObj.get("cantidad_producto").toString()));
			venta.setCod_producto(Long.parseLong(innerObj.get("codigo_producto").toString()));
			venta.setCod_venta(Long.parseLong(innerObj.get("codigo_venta").toString()));
			venta.setValor_sin_iva(Double.parseDouble(innerObj.get("valor_total").toString()));
			venta.setValor_total(Double.parseDouble(innerObj.get("valor_venta").toString()));
			venta.setValor_iva(Double.parseDouble(innerObj.get("valor_iva").toString()));
			venta.setCodigo_ciudad(innerObj.get("codigo_ciudad").toString());
			//venta.setDescripcion("vacio");
			lista.add(venta);
		}
		return lista;
	}
	
	public static ArrayList<DteVentas> getJSON(String ciudad) throws IOException, ParseException { //devolver un listado JSON

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
		ArrayList<DteVentas> lista = new ArrayList<DteVentas>();
		lista = parsingUsuarios(json);
		http.disconnect();
		return lista;
	}
	
	
	
	public static int postJSON(DteVentas venta,String ciudad) throws IOException {

		if(ciudad.equals("BOG")) {
			url = new URL(sitiobog + "/Guardar"); //trae el metodo de Usuarios.API
		}else if (ciudad.equals("MED")) {
			url = new URL(sitiomed + "/Guardar"); //trae el metodo de Usuarios.API
		}else if (ciudad.equals("CAL")) {
			url = new URL(sitiocal + "/Guardar");
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

		String data = "{" + "\"codigo_detalle_venta\":\"" + String.valueOf(venta.getCodigo_dte())
				+ "\",\"cantidad_producto\": \"" + String.valueOf(venta.getCantidad()) + "\",\"codigo_producto\": \""
				+ String.valueOf(venta.getCod_producto()) + "\",\"codigo_venta\":\"" + String.valueOf(venta.getCod_venta()) + "\",\"valor_total\":\""
				+ String.valueOf(venta.getValor_sin_iva()) + "\",\"valor_venta\": \""
				+ String.valueOf(venta.getValor_total()) +  "\",\"valor_iva\": \""
				+ String.valueOf(venta.getValor_iva())+"\",\"codigo_ciudad\":\"" + venta.getCodigo_ciudad() + "\"}";
		
		byte[] out = data.getBytes(StandardCharsets.UTF_8);
		OutputStream stream = http.getOutputStream();
		stream.write(out);

		int respuesta = http.getResponseCode();
		http.disconnect();
		return respuesta;
	}
}
