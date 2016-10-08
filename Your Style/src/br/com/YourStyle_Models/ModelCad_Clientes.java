package br.com.YourStyle_Models;

public class ModelCad_Clientes {
	
	private String nome, rg, cpf, endereco, bairro, cidade, estado, cep, telefone, celular; 
	private int n_endereco;
	private double vl_divida;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public int getN_endereco() {
		return n_endereco;
	}
	public void setN_endereco(int n_endereco) {
		this.n_endereco = n_endereco;
	}
	public double getVl_divida() {
		return vl_divida;
	}
	public void setVl_divida(double vl_divida) {
		this.vl_divida = vl_divida;
	}
	
}
