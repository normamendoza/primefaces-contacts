package py.una.pol.utils;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import py.una.pol.model.Contact;
import py.una.pol.service.ContactoService;
import py.una.pol.structs.ListResponse;

public class DataTableModel extends LazyDataModel<Contact> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<Contact> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		ListResponse<Contact> data = ContactoService.getContactList(first, pageSize, null);
		setRowCount(data.getTotal());
		System.out.println("Registros Encontrados: " + data.getLista().size());
		return data.getLista();
	}

	@Override
	public Contact getRowData(String rowKey) {

		return null;
	}

	@Override
	public Object getRowKey(Contact contact) {
		return contact.getId();
	}
}
