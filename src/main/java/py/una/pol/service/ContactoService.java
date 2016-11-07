package py.una.pol.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import py.una.pol.model.Contact;
import py.una.pol.structs.ListResponse;
import py.una.pol.utils.GsonUTCAdapter;

public class ContactoService {

	private static final String BASE_URL = "https://desa03.konecta.com.py/pwf/rest/agenda";

	public static List<Contact> getContactList(int first, int pageSize, String filter) {
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			URIBuilder uriBuilder = new URIBuilder(BASE_URL);
			uriBuilder.addParameter("inicio", String.valueOf(first));
			uriBuilder.addParameter("cantidad", String.valueOf(pageSize));
			HttpGet httpGet = new HttpGet(uriBuilder.build());
			CloseableHttpResponse response = httpclient.execute(httpGet);
			ListResponse<Contact> list = convertListResponse(response.getEntity().getContent());
			return list != null ? list.getLista() : new ArrayList<>();
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ArrayList<>();
		}
	}

	private static ListResponse<Contact> convertListResponse(InputStream is) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new GsonUTCAdapter()).create();
		JsonReader jsonReader = new JsonReader(new InputStreamReader(is));
		Type type = new TypeToken<ListResponse<Contact>>() {
		}.getType();
		return gson.fromJson(jsonReader, type);
	}

	private static Contact convertResponse(InputStream is) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new GsonUTCAdapter()).create();
		JsonReader jsonReader = new JsonReader(new InputStreamReader(is));
		return gson.fromJson(jsonReader, Contact.class);
	}

	public static void actualizar(Contact contact) {
		Gson gson = new Gson();
		String json = gson.toJson(contact);
		System.out.println(json);
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPut httpPut = new HttpPut(BASE_URL);
			StringEntity entity = new StringEntity(json);
			httpPut.setEntity(entity);
			httpPut.setHeader("Content-Type", "application/json");
			CloseableHttpResponse response = httpclient.execute(httpPut);
			contact = convertResponse(response.getEntity().getContent());
			System.out.println("contacto actualizado: " + contact);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void crear(Contact contact) {
		Gson gson = new Gson();
		String json = gson.toJson(contact);
		System.out.println(json);
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(BASE_URL);
			StringEntity entity = new StringEntity(json);
			httpPost.setEntity(entity);
			httpPost.setHeader("Content-Type", "application/json");
			CloseableHttpResponse response = httpclient.execute(httpPost);
			contact = convertResponse(response.getEntity().getContent());
			System.out.println("contacto creado: " + contact);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void eliminar(Contact contact) {
		Gson gson = new Gson();
		String json = gson.toJson(contact);
		System.out.println(json);
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpDelete httpDelete = new HttpDelete(BASE_URL + "/" + contact.getId());
			CloseableHttpResponse response = httpclient.execute(httpDelete);
			contact = convertResponse(response.getEntity().getContent());
			System.out.println("contacto eliminado: " + contact);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
