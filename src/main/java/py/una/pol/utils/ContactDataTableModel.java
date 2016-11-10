package py.una.pol.utils;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import py.una.pol.dao.ContactoDao;
import py.una.pol.dao.Dao;
import py.una.pol.model.Contact;

@SessionScoped
public class ContactDataTableModel extends DataTableModel<Contact> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ContactoDao contactoService;

	@Override
	public Dao<Contact> getService() {
		return contactoService;
	}
}
