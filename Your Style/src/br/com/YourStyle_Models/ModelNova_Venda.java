package br.com.YourStyle_Models;

public class ModelNova_Venda {
	private String CPFCliente, nomesProdutos[][], tamanhosProdutos[][], quantidadesProdutos[][], valoresProdutos[][];

	private double valorTotal, pagoTotal;
	
	public String getCPFCliente() {
		return CPFCliente;
	}
	public void setCPFCliente(String cPFCliente) {
		CPFCliente = cPFCliente;
	}
	public String[][] getNomesProdutos() {
		return nomesProdutos;
	}
	public void setNomesProdutos(String[][] nomesProdutos) {
		this.nomesProdutos = nomesProdutos;
	}
	public String[][] getQuantidadesProdutos() {
		return quantidadesProdutos;
	}
	public void setQuantidadesProdutos(String[][] quantidadesProdutos) {
		this.quantidadesProdutos = quantidadesProdutos;
	}
	public String[][] getValoresProdutos() {
		return valoresProdutos;
	}
	public void setValoresProdutos(String[][] carrinhoComprasLinhas) {
		this.valoresProdutos = carrinhoComprasLinhas;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public double getPagoTotal() {
		return pagoTotal;
	}
	public void setPagoTotal(double pagoTotal) {
		this.pagoTotal = pagoTotal;
	}
	public String[][] getTamanhosProdutos() {
		return tamanhosProdutos;
	}
	public void setTamanhosProdutos(String[][] tamanhosProdutos) {
		this.tamanhosProdutos = tamanhosProdutos;
	}	
}