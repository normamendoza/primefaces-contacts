package py.una.pol.utils;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import py.una.pol.model.Contact;
import py.una.pol.service.ContactoService;

public class DataTableModel extends LazyDataModel<Contact> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<Contact> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		List<Contact> data = ContactoService.getContactList(first, pageSize, null);
		System.out.println("Registros Encontrados: " + data.size());
		return data;
	}
}
