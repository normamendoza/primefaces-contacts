package py.una.pol.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Type;

import javax.enterprise.context.SessionScoped;

import com.google.gson.reflect.TypeToken;

import py.una.pol.dao.ContactoDao;
import py.una.pol.model.Contact;
import py.una.pol.structs.ListResponse;

@SessionScoped
public class ContactoDaoImpl extends DaoImpl<Contact> implements Serializable, ContactoDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3997483001655053199L;
	private Type listType;

	@Override
	public Type getListDataType() {
		if (listType == null) {
			listType = new TypeToken<ListResponse<Contact>>() {
			}.getType();

		}
		return listType;

	}

}
