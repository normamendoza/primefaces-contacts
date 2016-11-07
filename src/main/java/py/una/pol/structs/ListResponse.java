package py.una.pol.structs;

import java.util.List;

public class ListResponse<T> {
	private Long total;
	private List<T> lista;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getLista() {
		return lista;
	}

	public void setLista(List<T> lista) {
		this.lista = lista;
	}

}
