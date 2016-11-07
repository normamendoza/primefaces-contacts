package py.una.pol.ManagedBeans;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import py.una.pol.model.Contact;
import py.una.pol.service.ContactoService;
import py.una.pol.utils.DataTableModel;

@ManagedBean
@SessionScoped
public class ContactsManager implements Serializable {
	private static final long serialVersionUID = 1L;

	private Contact item = new Contact();
	private DataTableModel dataTableModel = new DataTableModel();

	public void add() {
		ContactoService.crear(item);
	}

	public void resetAdd() {
		item = new Contact();
	}

	public void edit(Contact item) {
		this.item = item;
	}

	public void saveEdit() {
		ContactoService.actualizar(item);
	}

	public void delete(Contact item) throws IOException {
		ContactoService.eliminar(item);
		if (item.getId() == this.item.getId()) {
			item = new Contact();
		}
	}

	public DataTableModel getDataTableModel() {
		return dataTableModel;
	}

	public Contact getItem() {
		return this.item;
	}

}
