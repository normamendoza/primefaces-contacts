package py.una.pol.dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.ClientProtocolException;
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
import com.google.gson.stream.JsonReader;

import py.una.pol.dao.Dao;
import py.una.pol.model.BaseEntity;
import py.una.pol.structs.ListResponse;
import py.una.pol.utils.GsonUTCAdapter;

public abstract class DaoImpl<T extends BaseEntity> implements Dao<T> {

	private Class<T> entityClass;
	private static final String BASE_URL = "https://desa03.konecta.com.py/pwf/rest/agenda";

	@Override
	public ListResponse<T> getList(int first, int pageSize, String filter) {
		try {
			URIBuilder uriBuilder = new URIBuilder(BASE_URL);
			uriBuilder.addParameter("inicio", String.valueOf(first));
			uriBuilder.addParameter("cantidad", String.valueOf(pageSize));
			uriBuilder.addParameter("filtro", filter);
			HttpGet httpGet = new HttpGet(uriBuilder.build());
			CloseableHttpClient cliente = HttpClients.createDefault();
			CloseableHttpResponse response = cliente.execute(httpGet);
			return convertListResponse(response.getEntity().getContent());
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ListResponse<T>();
		}
	}

	private ListResponse<T> convertListResponse(InputStream is) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new GsonUTCAdapter()).create();
		JsonReader jsonReader = new JsonReader(new InputStreamReader(is));
		/*
		 * Type type = new TypeToken<ListResponse<T>>() { }.getType();
		 */
		return gson.fromJson(jsonReader, getListDataType());

	}

	private T convertResponse(String response) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new GsonUTCAdapter()).create();
		JsonReader jsonReader = new JsonReader(new StringReader(response));
		return gson.fromJson(jsonReader, getEntityClass());
	}

	@SuppressWarnings("unchecked")
	private Class<T> getEntityClass() {

		if (entityClass == null) {
			ParameterizedType superClass = (ParameterizedType) this.getClass().getGenericSuperclass();
			entityClass = (Class<T>) superClass.getActualTypeArguments()[0];
		}
		return entityClass;
	}

	@Override
	public void actualizar(T object) throws UnsupportedOperationException, IOException {
		Gson gson = new Gson();
		String json = gson.toJson(object);
		System.out.println("actualizando...");
		System.out.println(json);
		HttpPut httpPut = new HttpPut(BASE_URL + "/" + object.getId());
		StringEntity entity = new StringEntity(json);
		httpPut.setEntity(entity);
		httpPut.setHeader("Content-Type", "application/json");
		CloseableHttpClient cliente = HttpClients.createDefault();
		CloseableHttpResponse response = cliente.execute(httpPut);
		String respuesta = IOUtils.toString(response.getEntity().getContent());
		System.out.println("respuesta -> " + respuesta);
		object = convertResponse(respuesta);
		System.out.println("objeto actualizado: " + object);
	}

	@Override
	public T crear(T object) throws ClientProtocolException, IOException {
		Gson gson = new Gson();
		String json = gson.toJson(object);
		System.out.println(json);
		HttpPost httpPost = new HttpPost(BASE_URL);
		StringEntity entity = new StringEntity(json);
		httpPost.setEntity(entity);
		httpPost.setHeader("Content-Type", "application/json");
		CloseableHttpClient cliente = HttpClients.createDefault();
		CloseableHttpResponse response = cliente.execute(httpPost);
		String respuesta = IOUtils.toString(response.getEntity().getContent());
		System.out.println("respuesta -> " + respuesta);
		T creado = convertResponse(respuesta);
		System.out.println("objeto creado: " + creado);
		return creado;
	}

	@Override
	public void eliminar(T object) throws ClientProtocolException, IOException {

		Gson gson = new Gson();
		String json = gson.toJson(object);
		System.out.println(json);
		HttpDelete httpDelete = new HttpDelete(BASE_URL + "/" + object.getId());
		CloseableHttpClient cliente = HttpClients.createDefault();
		CloseableHttpResponse response = cliente.execute(httpDelete);
		String respuesta = IOUtils.toString(response.getEntity().getContent());
		object = convertResponse(respuesta);
		System.out.println("objeto eliminado: " + object);

	}

	public abstract Type getListDataType();

}
