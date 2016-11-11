package py.una.pol.utils;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import py.una.pol.dao.Dao;
import py.una.pol.model.BaseEntity;
import py.una.pol.structs.ListResponse;

public abstract class DataTableModel<T extends BaseEntity> extends LazyDataModel<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		String filter = (String) filters.get("globalFilter");
		System.out.println("Filtrando lista por el valor: " + filter);
		ListResponse<T> data = getService().getList(first, pageSize, filter);
		setRowCount(data.getTotal());
		System.out.println("Registros Encontrados: " + data.getLista().size());
		return data.getLista();
	}

	public abstract Dao<T> getService();

	@Override
	public Object getRowKey(T object) {
		return object.getId();
	}

}
