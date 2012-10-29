package baza;

public class Krawedzie {
	private Integer id;
	private Integer id_pracownicy_in;
	private Integer id_pracownicy_out;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId_pracownicy_in() {
		return id_pracownicy_in;
	}
	public void setId_pracownicy_in(int i) {
		this.id_pracownicy_in = i;
	}
	public Integer getId_pracownicy_out() {
		return id_pracownicy_out;
	}
	public void setId_pracownicy_out(Integer id_pracownicy_out) {
		this.id_pracownicy_out = id_pracownicy_out;
	}
}
