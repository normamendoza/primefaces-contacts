package py.una.pol.ManagedBeans;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;

import py.una.pol.dao.ContactoDao;
import py.una.pol.model.Contact;
import py.una.pol.utils.ContactDataTableModel;

@ManagedBean
@SessionScoped
public class ContactsManager implements Serializable {
	private static final long serialVersionUID = 1L;

	private Contact item = new Contact();
	private String errorMsg;
	private String successMsg;
	@Inject
	private ContactDataTableModel dataTableModel;
	@Inject
	private ContactoDao contactoService;

	public void add() {
		try {
			Contact newContact = contactoService.crear(item);
			item.setId(newContact.getId());
			item.setFechacreacion(newContact.getFechacreacion());
			RequestContext.getCurrentInstance().update("contactoForm");
			setSuccessMsg("Registro creado con exito");
		} catch (Exception e) {
			setErrorMsg("Ocurrio un error al guardar registro");
		}
	}

	public void resetAdd() {
		resetMessages();
		item = new Contact();
	}

	public void edit(Contact item) {
		resetMessages();
		this.item = item;
	}

	public void saveEdit() {
		try {
			contactoService.actualizar(item);
			setSuccessMsg("Registro actualizado con exito");
		} catch (Exception e) {
			setErrorMsg("Error al actualizar registro");
		}
	}

	public void delete(Contact item) throws IOException {
		try {
			Long itemId = item.getId();
			contactoService.eliminar(item);
			if (itemId == this.item.getId()) {
				this.item = new Contact();
			}
			setSuccessMsg("Registro eliminado con exito");
		} catch (Exception e) {
			setErrorMsg("Error al eliminar registro");
		}
	}

	public ContactDataTableModel getDataTableModel() {
		return dataTableModel;
	}

	public Contact getItem() {
		return this.item;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public String getSuccessMsg() {
		return successMsg;
	}

	private void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
		successMsg = null;
	}

	private void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
		errorMsg = null;
	}

	private void resetMessages() {
		this.successMsg = null;
		this.errorMsg = null;
	}

}
