package py.una.pol.structs;

import java.util.List;

public class ListResponse<T> {
	private int total;
	private List<T> lista;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getLista() {
		return lista;
	}

	public void setLista(List<T> lista) {
		this.lista = lista;
	}

}
