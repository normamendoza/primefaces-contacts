package py.una.pol.dao;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import py.una.pol.model.BaseEntity;
import py.una.pol.structs.ListResponse;

public interface Dao<T extends BaseEntity> {

	ListResponse<T> getList(int first, int pageSize, String filter);

	void actualizar(T object) throws UnsupportedOperationException, IOException;

	T crear(T object) throws ClientProtocolException, IOException;

	void eliminar(T object) throws ClientProtocolException, IOException;

}
