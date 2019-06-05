package ru.geekbrains.psy_journal.model.data;

public class TD {

	private int id;
	private String code;
	private String name;
	private TF tf;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TF getTf() {
		return tf;
	}

	public void setTf(TF tf) {
		this.tf = tf;
	}
}
