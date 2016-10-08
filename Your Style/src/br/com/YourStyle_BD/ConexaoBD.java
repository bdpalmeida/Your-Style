package br.com.YourStyle_BD;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import br.com.YourStyle_Models.ModelNova_Venda;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

public class ConexaoBD {

	private String servidor = "com.mysql.jdbc.Driver", urlBanco, usuarioBanco,
			senhaBanco, sql;

	private boolean status;

	private Connection con = null;

	protected void LerConfiguracoes() {
		try {
			File file = new File("C:/Conexão 3/Your Style/Configurações.txt");
			FileInputStream in = new FileInputStream(file);
			Scanner scanner = new Scanner(in);

			while (scanner.hasNext()) {
				String readLine = scanner.nextLine();

				if (readLine.startsWith("URL da base de dados MySQL: "))
					urlBanco = readLine.replace("URL da base de dados MySQL: ",
							"");

				if (readLine.startsWith("Usuário: "))
					usuarioBanco = readLine.replace("Usuário: ", "");

				if (readLine.startsWith("Senha: "))
					senhaBanco = readLine.replace("Senha: ", "");
			}
			scanner.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean TestaConexao() {
		LerConfiguracoes();

		try {
			try {
				Class.forName(servidor);
			} catch (ClassNotFoundException erro) {
				erro.printStackTrace();
			}
			con = DriverManager.getConnection(urlBanco, usuarioBanco,
					senhaBanco);

			return true;
		} catch (CommunicationsException erro) {
			JOptionPane.showMessageDialog(null,
					"Conexão com a Base de Dados está desativada!",
					"Erro, conexão com a Base de Dados",
					JOptionPane.ERROR_MESSAGE);
			JOptionPane.showMessageDialog(null, "O Your Style será encerrado.",
					"Encerrando Your Style", JOptionPane.WARNING_MESSAGE);
			return false;

		} catch (SQLException erro) {
			JOptionPane.showMessageDialog(null,
					"Conexão com a Base de Dados está desativada!",
					"Erro, conexão com a Base de Dados",
					JOptionPane.ERROR_MESSAGE);
			JOptionPane.showMessageDialog(null, "O Your Style será encerrado.",
					"Encerrando Your Style", JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}

	public String[][] ConsultaTabelaCliente(String ondeNome, String ondeRg,
			String ondeCpf) {

		LerConfiguracoes();

		String linhas[][] = null;

		try {
			try {
				Class.forName(servidor);
			} catch (ClassNotFoundException erro) {
				erro.printStackTrace();
			}
			con = DriverManager.getConnection(urlBanco, usuarioBanco,
					senhaBanco);

			Statement stmt = con.createStatement();

			if (ondeNome.isEmpty() && ondeRg.isEmpty() && ondeCpf.isEmpty()
					|| ondeNome.equals("todos*"))
				sql = "SELECT * FROM vwCodigosClientes";
			else {
				if (!ondeNome.isEmpty())
					sql = "SELECT count(cod_cliente) FROM Cliente USE INDEX (nomeCliente) WHERE nome_cliente LIKE '"
							+ ondeNome + "%'";
				if (!ondeRg.isEmpty())
					sql = "SELECT count(cod_cliente) FROM Cliente USE INDEX (rgCliente) WHERE RG LIKE '"
							+ ondeRg + "%'";
				if (!ondeCpf.isEmpty())
					sql = "SELECT count(cod_cliente) FROM Cliente USE INDEX (cpfCliente) WHERE CPF LIKE '"
							+ ondeCpf + "%'";
			}

			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			if (ondeNome.equals("todos*"))
				linhas = new String[rs.getInt(1)][1];
			else
				linhas = new String[rs.getInt(1)][14];

			if (ondeNome.isEmpty() && ondeRg.isEmpty() && ondeCpf.isEmpty())
				sql = "SELECT * FROM vwClientes";
			else {
				if (!ondeNome.isEmpty())
					sql = "SELECT * FROM Cliente USE INDEX (nomeCliente) WHERE nome_cliente LIKE '"
							+ ondeNome + "%' ORDER BY nome_cliente";
				if (!ondeRg.isEmpty())
					sql = "SELECT * FROM Cliente USE INDEX (rgCliente) WHERE rg LIKE '"
							+ ondeRg + "%' ORDER BY nome_cliente";
				if (!ondeCpf.isEmpty())
					sql = "SELECT * FROM Cliente USE INDEX (cpfCliente) WHERE cpf LIKE '"
							+ ondeCpf + "%' ORDER BY nome_cliente";
			}

			if (ondeNome.equals("todos*"))
				sql = "SELECT * FROM vwNomesCPFClientes";

			rs = stmt.executeQuery(sql);

			for (int contador = 0; (rs.next()) && (contador < linhas.length); contador++) {
				String nome, cpf;
				if (!ondeNome.equals("todos*")) {
					int cod = rs.getInt(1);
					nome = rs.getString(2);
					String rg = rs.getString(3);
					cpf = rs.getString(4);
					String endereco = rs.getString(5);
					int n_endereco = rs.getInt(6);
					String bairro = rs.getString(7);
					String cidade = rs.getString(8);
					String estado = rs.getString(9);
					String cep = rs.getString(10);
					String telefone = rs.getString(11);
					String celular = rs.getString(12);
					double vl_divida = rs.getDouble(13);

					Date dataCadastro = rs.getTimestamp(14);
					SimpleDateFormat formataData = new SimpleDateFormat(
							"dd/MM/yyyy HH:mm:ss");

					linhas[contador][0] = Integer.toString(cod);
					linhas[contador][1] = nome;
					linhas[contador][2] = rg;
					linhas[contador][3] = cpf;
					linhas[contador][4] = endereco;
					linhas[contador][5] = Integer.toString(n_endereco);
					linhas[contador][6] = bairro;
					linhas[contador][7] = cidade;
					linhas[contador][8] = estado;
					linhas[contador][9] = cep;
					linhas[contador][10] = telefone;
					linhas[contador][11] = celular;
					linhas[contador][12] = Double.toString(vl_divida);
					linhas[contador][13] = formataData.format(dataCadastro);
				} else {
					nome = rs.getString(1);
					cpf = rs.getString(2);
					linhas[contador][0] = nome + " - " + cpf;
				}

			}
			rs.close();
		} catch (SQLException erro) {
			erro.printStackTrace();
		}

		return linhas;
	}

	public boolean ConsultaExisteNaTabelaCliente(String ondeNome,
			String ondeRg, String ondeCpf) {

		LerConfiguracoes();

		String linhas[][] = null;

		try {
			try {
				Class.forName(servidor);
			} catch (ClassNotFoundException erro) {
				erro.printStackTrace();
			}
			con = DriverManager.getConnection(urlBanco, usuarioBanco,
					senhaBanco);

			Statement stmt = con.createStatement();

			sql = "SELECT COUNT(cod_cliente) FROM Cliente USE INDEX (rgCliente, cpfCliente) WHERE RG LIKE '"
					+ ondeRg + "'" + " OR CPF LIKE '" + ondeCpf + "'";

			ResultSet rs = stmt.executeQuery(sql);
			rs.next();

			linhas = new String[rs.getInt(1)][1];

			rs.close();

			if (linhas.length > 0)
				return true;
			else
				return false;
		} catch (SQLException erro) {
			erro.printStackTrace();
			return true;
		}
	}

	public String[][] ConsultaTabelaClienteDivida(String ondeNome) {
		String linhas[][] = null;

		LerConfiguracoes();

		try {
			try {
				Class.forName(servidor);
			} catch (ClassNotFoundException erro) {
				erro.printStackTrace();
			}
			con = DriverManager.getConnection(urlBanco, usuarioBanco,
					senhaBanco);

			Statement stmt = con.createStatement();

			if (ondeNome.isEmpty())
				sql = "SELECT * FROM vwQuantosClientesDividas";
			else
				sql = "SELECT COUNT(cod_cliente) FROM CLIENTE USE INDEX (nomeCliente) "
						+ "WHERE nome_cliente LIKE '"
						+ ondeNome
						+ "%' AND vl_divida > 0";

			ResultSet rs = stmt.executeQuery(sql);
			rs.next();

			linhas = new String[rs.getInt(1)][3];

			if (ondeNome.isEmpty())
				sql = "SELECT * FROM vwClientesDividas";
			else
				sql = "SELECT nome_cliente, cpf, vl_divida FROM Cliente	USE INDEX (nomeCliente)"
						+ "WHERE nome_cliente LIKE '"
						+ ondeNome
						+ "%' AND vl_divida > 0 " + "ORDER BY nome_cliente";

			rs = stmt.executeQuery(sql);

			for (int contador = 0; (rs.next()) && (contador < linhas.length); contador++) {
				String nome = rs.getString(1);
				String cpf = rs.getString(2);
				double vl_divida = rs.getDouble(3);

				linhas[contador][0] = nome;
				linhas[contador][1] = cpf;
				linhas[contador][2] = String.valueOf(vl_divida);
			}
			rs.close();
		} catch (SQLException erro) {
			erro.printStackTrace();
		}

		return linhas;
	}

	public boolean InserirTabelaCliente(String nome, String rg, String cpf,
			String endereco, int n_endereco, String bairro, String cidade,
			String estado, String cep, String telefone, String celular,
			double vl_divida) {

		LerConfiguracoes();

		if (ConsultaExisteNaTabelaCliente(nome, rg, cpf) == false)
			try {
				try {
					Class.forName(servidor);
				} catch (ClassNotFoundException erro) {
					erro.printStackTrace();
				}
				con = DriverManager.getConnection(urlBanco, usuarioBanco,
						senhaBanco);

				sql = "INSERT INTO Cliente(nome_cliente, rg, cpf, endereco, n_endereco, bairro, cidade, estado, "
						+ "cep, telefone, celular, vl_divida, data_cadastro) VALUES"
						+ "( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, (SELECT NOW()) )";

				java.sql.PreparedStatement stmt = con.prepareStatement(sql);

				stmt.setString(1, nome);
				stmt.setString(2, rg);
				stmt.setString(3, cpf);
				stmt.setString(4, endereco);
				stmt.setInt(5, n_endereco);
				stmt.setString(6, bairro);
				stmt.setString(7, cidade);
				stmt.setString(8, estado);
				stmt.setString(9, cep);
				stmt.setString(10, telefone);
				stmt.setString(11, celular);
				stmt.setDouble(12, vl_divida);

				stmt.executeUpdate();
				stmt.close();

				JOptionPane.showMessageDialog(null,
						"Cadastro realizado com sucesso!",
						"Cadastro Realizado!", JOptionPane.INFORMATION_MESSAGE);

				return true;
			} catch (SQLException erro) {
				JOptionPane.showMessageDialog(null,
						"Erro ao efetuar cadastro!", "Erro",
						JOptionPane.ERROR_MESSAGE);
				erro.printStackTrace();

				return false;
			} finally {
				try {
					con.close();
				} catch (SQLException erro) {
					erro.printStackTrace();
				}
			}
		else {
			JOptionPane
					.showMessageDialog(
							null,
							"Erro ao efetuar cadastro!\n"
									+ "Já existe um cliente cadastrado com esses dados (RG ou CPF).",
							"Erro", JOptionPane.ERROR_MESSAGE);

			return false;
		}
	}

	public void AlterarTabelaCliente(String linhas[][], JTable tableClientes) {

		LerConfiguracoes();

		try {
			try {
				Class.forName(servidor);
			} catch (ClassNotFoundException erro) {
				erro.printStackTrace();
			}
			con = DriverManager.getConnection(urlBanco, usuarioBanco,
					senhaBanco);

			for (int contador = 0; contador < linhas.length; contador++) {
				sql = "UPDATE Cliente USE INDEX(codCliente) SET nome_cliente=?, rg=?, cpf=?, endereco=?, n_endereco=?, "
						+ "bairro=?, cidade=?, estado=?, cep=?, telefone=?, celular=? "
						+ "WHERE cod_cliente = ?";

				java.sql.PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setObject(1, tableClientes.getValueAt(contador, 1)
						.toString().trim());
				stmt.setObject(2, tableClientes.getValueAt(contador, 2));
				stmt.setObject(3, tableClientes.getValueAt(contador, 3));
				stmt.setObject(4, tableClientes.getValueAt(contador, 4)
						.toString().trim());
				stmt.setObject(5, tableClientes.getValueAt(contador, 5)
						.toString().trim());
				stmt.setObject(6, tableClientes.getValueAt(contador, 6)
						.toString().trim());
				stmt.setObject(7, tableClientes.getValueAt(contador, 7)
						.toString().trim());
				stmt.setObject(8, tableClientes.getValueAt(contador, 8)
						.toString().trim());
				stmt.setObject(9, tableClientes.getValueAt(contador, 9)
						.toString().trim());
				stmt.setObject(10, tableClientes.getValueAt(contador, 10)
						.toString().trim());
				stmt.setObject(11, tableClientes.getValueAt(contador, 11)
						.toString().trim());
				stmt.setObject(12, tableClientes.getValueAt(contador, 0));

				stmt.executeUpdate();
				stmt.close();
			}

			JOptionPane.showMessageDialog(null,
					"Alteração realizada com sucesso!", "Alteração Realizada!",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException erro) {
			JOptionPane.showMessageDialog(null, "Erro ao efetuar alteração!",
					"Erro", JOptionPane.ERROR_MESSAGE);
			erro.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException erro) {
				erro.printStackTrace();
			}
		}
	}

	public String[][] ConsultaTabelaProduto(String ondeNome, String ondeTamanho) {
		String linhas[][] = null;

		LerConfiguracoes();

		try {
			try {
				Class.forName(servidor);
			} catch (ClassNotFoundException erro) {
				erro.printStackTrace();
			}
			con = DriverManager.getConnection(urlBanco, usuarioBanco,
					senhaBanco);

			Statement stmt = con.createStatement();

			if (ondeNome.isEmpty() && ondeTamanho.isEmpty()
					|| ondeNome.equals("todos*"))
				sql = "SELECT * FROM vwCodigosProdutos";
			else if (ondeTamanho.isEmpty())
				sql = "SELECT COUNT(cod_produto) FROM Produto USE INDEX (nomeProduto) WHERE nome_produto LIKE '"
						+ ondeNome + "%'";
			else if (ondeNome.isEmpty())
				sql = "SELECT COUNT(cod_produto) FROM Produto WHERE tamanho LIKE '"
						+ ondeTamanho + "%'";

			if (ondeNome.equals("todos*estoque"))
				sql = "SELECT COUNT(cod_produto) FROM Produto WHERE quantidade > 0";

			ResultSet rs = stmt.executeQuery(sql);
			rs.next();

			if (ondeNome.equals("todos*"))
				linhas = new String[rs.getInt(1)][1];
			else
				linhas = new String[rs.getInt(1)][6];

			if (ondeNome.equals("todos*estoque"))
				linhas = new String[rs.getInt(1)][3];

			if (ondeNome.isEmpty() && ondeTamanho.isEmpty())
				sql = "SELECT * FROM vwProdutos";
			else if (ondeTamanho.isEmpty())
				sql = "SELECT * FROM Produto USE INDEX (nomeProduto) WHERE nome_produto LIKE '"
						+ ondeNome + "%' ORDER BY nome_produto, tamanho";
			else if (ondeNome.isEmpty())
				sql = "SELECT * from Produto WHERE tamanho LIKE '"
						+ ondeTamanho + "%' ORDER BY nome_produto, tamanho";

			if (ondeNome.equals("todos*"))
				sql = "SELECT * FROM vwNomesProdutos";

			if (ondeNome.equals("todos*estoque"))
				sql = "SELECT nome_produto, tamanho, quantidade, preco FROM Produto WHERE quantidade > 0 "
						+ "ORDER BY nome_produto, tamanho";

			rs = stmt.executeQuery(sql);

			for (int contador = 0; (rs.next()) && (contador < linhas.length); contador++) {
				String nome;
				String tamanho;
				int quantidade;
				double preco;
				if (!ondeNome.equals("todos*")
						&& !ondeNome.equals("todos*estoque")) {
					int cod = rs.getInt(1);
					nome = rs.getString(2);
					tamanho = rs.getString(3);
					quantidade = rs.getInt(4);
					preco = rs.getDouble(5);
					String detalhes = rs.getString(6);

					linhas[contador][0] = Integer.toString(cod);
					linhas[contador][1] = nome;
					linhas[contador][2] = tamanho;
					linhas[contador][3] = String.valueOf(quantidade);
					linhas[contador][4] = String.valueOf(preco);
					linhas[contador][5] = detalhes;
				} else {
					nome = rs.getString(1);
					linhas[contador][0] = nome;
				}
				if (ondeNome.equals("todos*estoque")) {
					nome = rs.getString(1);
					tamanho = rs.getString(2);
					quantidade = rs.getInt(3);
					preco = rs.getDouble(4);
					linhas[contador][0] = nome + " - " + tamanho + " (tamanho)";
					linhas[contador][1] = String.valueOf(quantidade);
					linhas[contador][2] = String.valueOf(preco);
				}
			}
			rs.close();
		} catch (SQLException erro) {
			erro.printStackTrace();
		}

		return linhas;
	}

	public boolean ConsultaExisteNaTabelaProduto(String ondeNome, String tamanho) {
		String linhas[][] = null;

		LerConfiguracoes();

		try {
			try {
				Class.forName(servidor);
			} catch (ClassNotFoundException erro) {
				erro.printStackTrace();
			}
			con = DriverManager.getConnection(urlBanco, usuarioBanco,
					senhaBanco);

			Statement stmt = con.createStatement();

			sql = "SELECT COUNT(cod_produto) FROM Produto USE INDEX (nomeProduto) WHERE nome_produto LIKE '"
					+ ondeNome + "' " + "&& tamanho LIKE '" + tamanho + "'";

			ResultSet rs = stmt.executeQuery(sql);
			rs.next();

			linhas = new String[rs.getInt(1)][1];

			rs.close();

			if (linhas.length > 0)
				return true;
			else
				return false;
		} catch (SQLException erro) {
			erro.printStackTrace();
			return true;
		}
	}

	public boolean InserirTabelaProduto(String nome, String tamanho,
			int quantidade, double preco, String detalhes) {

		LerConfiguracoes();

		if (ConsultaExisteNaTabelaProduto(nome, tamanho) == false)
			try {
				try {
					Class.forName(servidor);
				} catch (ClassNotFoundException erro) {
					erro.printStackTrace();
				}
				con = DriverManager.getConnection(urlBanco, usuarioBanco,
						senhaBanco);

				sql = "INSERT INTO Produto(nome_produto, tamanho, quantidade, preco, detalhes)"
						+ " VALUES(?, ?, ?, ?, ?)";

				java.sql.PreparedStatement stmt = con.prepareStatement(sql);

				stmt.setString(1, nome);
				stmt.setString(2, tamanho);
				stmt.setInt(3, quantidade);
				stmt.setDouble(4, preco);
				stmt.setString(5, detalhes);

				stmt.executeUpdate();
				stmt.close();

				JOptionPane.showMessageDialog(null,
						"Cadastro realizado com sucesso!",
						"Cadastro Realizado!", JOptionPane.INFORMATION_MESSAGE);

				return true;
			} catch (SQLException erro) {
				JOptionPane.showMessageDialog(null,
						"Erro ao efetuar cadastro!", "Erro",
						JOptionPane.ERROR_MESSAGE);
				erro.printStackTrace();

				return false;
			} finally {
				try {
					con.close();
				} catch (SQLException erro) {
					erro.printStackTrace();
				}
			}
		else {
			JOptionPane
					.showMessageDialog(
							null,
							"Erro ao efetuar cadastro!\n"
									+ "Já existe um produto cadastrado com esse nome e tamanho.",
							"Erro", JOptionPane.ERROR_MESSAGE);

			return false;
		}
	}

	public void AlterarTabelaProduto(String linhas[][], JTable tableProdutos) {

		LerConfiguracoes();

		try {
			try {
				Class.forName(servidor);
			} catch (ClassNotFoundException erro) {
				erro.printStackTrace();
			}
			con = DriverManager.getConnection(urlBanco, usuarioBanco,
					senhaBanco);

			for (int contador = 0; contador < linhas.length; contador++) {
				sql = "UPDATE Produto SET nome_produto=?, tamanho=?, quantidade=?, preco=?, "
						+ "detalhes=? WHERE cod_produto = ?";
				java.sql.PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setObject(1, tableProdutos.getValueAt(contador, 1)
						.toString().trim());
				stmt.setObject(2, tableProdutos.getValueAt(contador, 2)
						.toString().trim());
				stmt.setObject(3, tableProdutos.getValueAt(contador, 3));
				stmt.setObject(4, tableProdutos.getValueAt(contador, 4));
				stmt.setObject(5, tableProdutos.getValueAt(contador, 5)
						.toString().trim());
				stmt.setObject(6, tableProdutos.getValueAt(contador, 0));
				stmt.executeUpdate();
				stmt.close();
			}

			JOptionPane.showMessageDialog(null,
					"Alteração realizada com sucesso!", "Alteração Realizada!",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException erro) {
			JOptionPane.showMessageDialog(null, "Erro ao efetuar alteração!",
					"Erro", JOptionPane.ERROR_MESSAGE);
			erro.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException erro) {
				erro.printStackTrace();
			}
		}
	}

	public void DeletarProduto(int codigo) {

		LerConfiguracoes();

		try {
			try {
				Class.forName(servidor);
			} catch (ClassNotFoundException erro) {
				erro.printStackTrace();
			}
			con = DriverManager.getConnection(urlBanco, usuarioBanco,
					senhaBanco);

			sql = "DELETE FROM Produto WHERE cod_produto = ?";

			java.sql.PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setInt(1, codigo);
			stmt.executeUpdate();
			stmt.close();

			JOptionPane.showMessageDialog(null,
					"Produto excluído com sucesso!", "Exclusão Realizada!",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException erro) {
			JOptionPane.showMessageDialog(null, "Erro ao efetuar exclusão!",
					"Erro", JOptionPane.ERROR_MESSAGE);
			erro.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException erro) {
				erro.printStackTrace();
			}
		}
	}

	public void InserirTabelaVenda_Produto_Cliente(ModelNova_Venda modelo) {

		LerConfiguracoes();

		try {
			try {
				Class.forName(servidor);
			} catch (ClassNotFoundException erro) {
				erro.printStackTrace();
			}
			con = DriverManager.getConnection(urlBanco, usuarioBanco,
					senhaBanco);

			Statement stmt = con.createStatement();

			sql = "SELECT COUNT(cod_venda) FROM Venda WHERE valorTotal != 'null'";

			ResultSet rs = stmt.executeQuery(sql);
			rs.next();

			int pro_cod = rs.getInt(1) + 1;

			sql = "SELECT cod_cliente FROM Cliente USE INDEX(cpfCliente) WHERE CPF = '"
					.concat(modelo.getCPFCliente()).concat("'");

			rs = stmt.executeQuery(sql);
			rs.next();

			int cod_cliente = rs.getInt(1);

			for (int cont = 0; cont < modelo.getNomesProdutos().length; cont++) {

				sql = "SELECT cod_produto FROM Produto USE INDEX (nomeProduto) WHERE nome_produto = '"
						+ modelo.getNomesProdutos()[cont][0].substring(0,
								modelo.getNomesProdutos()[cont][0]
										.indexOf(" - "))
						+ "'"
						+ "&& tamanho = '"
						+ modelo.getTamanhosProdutos()[cont][0].substring(
								modelo.getTamanhosProdutos()[cont][0]
										.indexOf(" - ") + 3, modelo
										.getTamanhosProdutos()[cont][0]
										.indexOf(" (tamanho) - R$ ")) + "'";

				rs = stmt.executeQuery(sql);
				rs.next();

				int cod_produto = rs.getInt(1);

				sql = "INSERT INTO Venda(cod_venda, cod_cliente, cod_produto, quantidade, valor, valorTotal, "
						+ "data_venda) VALUES(?, ?, ?, ?, ?, ?, (SELECT NOW()) )";

				java.sql.PreparedStatement stmt2 = con.prepareStatement(sql);

				stmt2.setInt(1, pro_cod);
				stmt2.setInt(2, cod_cliente);
				stmt2.setInt(3, cod_produto);
				stmt2.setInt(4, Integer.parseInt(modelo
						.getQuantidadesProdutos()[cont][1]));
				stmt2.setDouble(5, Double.parseDouble(modelo
						.getValoresProdutos()[cont][2]));

				if (cont < modelo.getNomesProdutos().length - 1)
					stmt2.setObject(6, null);
				else
					stmt2.setDouble(6, modelo.getValorTotal());

				stmt2.executeUpdate();
				stmt2.close();

				sql = "UPDATE Produto SET quantidade = quantidade - ? WHERE cod_produto = "
						+ cod_produto;

				java.sql.PreparedStatement stmt3 = con.prepareStatement(sql);

				stmt3.setInt(1, Integer.parseInt(modelo
						.getQuantidadesProdutos()[cont][1]));
				stmt3.executeUpdate();
				stmt3.close();
			}

			double divida = 0;

			if (modelo.getPagoTotal() < modelo.getValorTotal()) {
				divida = modelo.getValorTotal() - modelo.getPagoTotal();

				sql = "UPDATE Cliente SET vl_divida = vl_divida + ? WHERE cod_cliente = "
						+ cod_cliente;

				java.sql.PreparedStatement stmt4 = con.prepareStatement(sql);

				stmt4.setDouble(1, divida);
				stmt4.executeUpdate();
				stmt4.close();
			}

			JOptionPane.showMessageDialog(null, "Venda realizada com sucesso!",
					"Venda Realizada!", JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException erro) {
			JOptionPane.showMessageDialog(null, "Erro ao efetuar cadastro!",
					"Erro", JOptionPane.ERROR_MESSAGE);
			erro.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException erro) {
				erro.printStackTrace();
			}
		}
	}

	public String[][] ConsultarTabelaVendas(String ondeNomeCliente,
			String ondeDataVenda, String ondeCPFCliente) {
		String linhas[][] = null;

		LerConfiguracoes();

		try {
			try {
				Class.forName(servidor);
			} catch (ClassNotFoundException erro) {
				erro.printStackTrace();
			}
			con = DriverManager.getConnection(urlBanco, usuarioBanco,
					senhaBanco);

			Statement stmt = con.createStatement();

			if (ondeNomeCliente.isEmpty() && ondeDataVenda.isEmpty()
					&& ondeCPFCliente.isEmpty())
				sql = "SELECT * FROM vwQuantasVendas";
			else if (ondeDataVenda.isEmpty() && ondeCPFCliente.isEmpty())
				sql = "SELECT COUNT(cod_venda) FROM Venda "
						+ "NATURAL JOIN Cliente USE INDEX(nomeCliente) WHERE nome_cliente LIKE '"
						+ ondeNomeCliente + "%' && valorTotal > 0";
			else if (ondeNomeCliente.isEmpty() && ondeCPFCliente.isEmpty())
				sql = "SELECT COUNT(cod_venda) FROM Venda "
						+ "WHERE data_venda LIKE '" + ondeDataVenda
						+ "%' && valorTotal > 0";
			else if (ondeNomeCliente.isEmpty() && ondeDataVenda.isEmpty())
				sql = "SELECT COUNT(cod_venda) FROM Venda "
						+ "NATURAL JOIN Cliente USE INDEX(cpfCliente) WHERE cpf LIKE '"
						+ ondeCPFCliente + "%' && valorTotal > 0";

			ResultSet rs = stmt.executeQuery(sql);
			rs.next();

			linhas = new String[rs.getInt(1)][4];

			if (ondeNomeCliente.isEmpty() && ondeDataVenda.isEmpty()
					&& ondeCPFCliente.isEmpty())
				sql = "SELECT * FROM vwVendas";
			else if (ondeDataVenda.isEmpty() && ondeCPFCliente.isEmpty())
				sql = "SELECT nome_cliente, cpf, valorTotal, data_venda FROM Venda "
						+ "NATURAL JOIN Cliente USE INDEX(nomeCliente) WHERE nome_cliente LIKE '"
						+ ondeNomeCliente
						+ "%' && valorTotal > 0 "
						+ "ORDER BY data_venda DESC";
			else if (ondeNomeCliente.isEmpty() && ondeCPFCliente.isEmpty())
				sql = "SELECT nome_cliente, cpf, valorTotal, data_venda FROM Venda "
						+ "NATURAL JOIN Cliente WHERE data_venda LIKE '"
						+ ondeDataVenda
						+ "%' && valorTotal > 0 "
						+ "ORDER BY data_venda DESC";
			else if (ondeNomeCliente.isEmpty() && ondeDataVenda.isEmpty())
				sql = "SELECT nome_cliente, cpf, valorTotal, data_venda FROM Venda "
						+ "NATURAL JOIN Cliente USE INDEX(cpfCliente) WHERE cpf LIKE '"
						+ ondeCPFCliente
						+ "%' && valorTotal > 0 "
						+ "ORDER BY data_venda DESC";

			rs = stmt.executeQuery(sql);

			for (int contador = 0; (rs.next()) && (contador < linhas.length); contador++) {

				String nome_cliente = rs.getString(1);
				String cpf = rs.getString(2);
				Double valorTotal = rs.getDouble(3);

				linhas[contador][0] = nome_cliente;
				linhas[contador][1] = cpf;
				linhas[contador][2] = "R$ " + valorTotal.toString();

				Date dataCadastro = rs.getTimestamp(4);
				SimpleDateFormat formataData = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				linhas[contador][3] = formataData.format(dataCadastro);

			}
			rs.close();
		} catch (SQLException erro) {
			erro.printStackTrace();
		}

		return linhas;
	}

	public String[][] RelatorioTabelaVendas(String dataInicial, String dataFinal) {
		String diaI = null, mesI = null, anoI = null, 
		   diaF = null, mesF = null, anoF = null;
		
		if (!dataInicial.isEmpty() && !dataFinal.isEmpty()) {
			diaI = dataInicial.substring(0, 2);
			mesI = dataInicial.substring(3, 5);
			anoI = dataInicial.substring(6, 10);
			dataInicial = anoI + "-" + mesI + "-" + diaI;
			
			diaF = dataFinal.substring(0, 2);
			mesF = dataFinal.substring(3, 5);
			anoF = dataFinal.substring(6, 10);
			dataFinal = anoF + "-" + mesF + "-" + diaF;
		}
		
		String linhas[][] = null;

		LerConfiguracoes();

		try {
			try {
				Class.forName(servidor);
			} catch (ClassNotFoundException erro) {
				erro.printStackTrace();
			}
			con = DriverManager.getConnection(urlBanco, usuarioBanco,
					senhaBanco);

			Statement stmt = con.createStatement();

			if (dataInicial.isEmpty() && dataFinal.isEmpty())
				sql = "SELECT * FROM vwQuantosRelatoriosVendas";
			else
				sql = "SELECT COUNT(cod_venda) FROM Venda "
						+ "WHERE valorTotal > 0 AND " + "data_venda BETWEEN '"
						+ dataInicial + "' AND ('" + dataFinal + "' + INTERVAL 1 DAY)"
						+ "GROUP BY DATE_FORMAT( data_venda, '%Y-%m-%d' )";

			ResultSet rs = stmt.executeQuery(sql);
			rs.last();

			linhas = new String[rs.getRow()][1];

			if (dataInicial.isEmpty() && dataFinal.isEmpty())
				sql = "SELECT * FROM vwRelatoriosVendas";
			else
				sql = "SELECT ROUND(SUM(valorTotal),2) FROM Venda "
						+ "WHERE valorTotal > 0 AND " + "data_venda BETWEEN '"
						+ dataInicial + "' AND ('" + dataFinal + "' + INTERVAL 1 DAY)"
						+ "GROUP BY DATE_FORMAT( data_venda, '%Y-%m-%d' )";

			rs = stmt.executeQuery(sql);

			for (int contador = 0; (rs.next()) && (contador < linhas.length); contador++) {
				linhas[contador][0] = "R$ " + rs.getDouble(1);
			}

			rs.close();
		} catch (SQLException erro) {
			erro.printStackTrace();
		}

		return linhas;
	}

	public void PagarDividaCliente(String nome_cliente, String cpf,
			double vl_pago) {

		LerConfiguracoes();

		try {
			try {
				Class.forName(servidor);
			} catch (ClassNotFoundException erro) {
				erro.printStackTrace();
			}
			con = DriverManager.getConnection(urlBanco, usuarioBanco,
					senhaBanco);

			sql = "UPDATE Cliente USE INDEX(nomeCliente, cpfCliente) SET vl_divida = vl_divida - ? WHERE nome_cliente = ? && cpf = ?";

			java.sql.PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setDouble(1, vl_pago);
			stmt.setString(2, nome_cliente);
			stmt.setString(3, cpf);

			stmt.executeUpdate();
			stmt.close();

			JOptionPane.showMessageDialog(null,
					"Pagamento efetuado com sucesso!", "Pagamento Realizado!",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException erro) {
			JOptionPane.showMessageDialog(null, "Erro ao efetuar pagamento!",
					"Erro", JOptionPane.ERROR_MESSAGE);
			erro.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException erro) {
				erro.printStackTrace();
			}
		}
	}

	public boolean ConsultaTabelaUsuario(String usuario, char[] password) {
		String senha = String.valueOf(password);

		LerConfiguracoes();

		try {
			try {
				Class.forName(servidor);
			} catch (ClassNotFoundException erro) {
				erro.printStackTrace();
			}
			con = DriverManager.getConnection(urlBanco, usuarioBanco,
					senhaBanco);

			Statement stmt = con.createStatement();

			sql = "SELECT CASE WHEN usuario LIKE BINARY '"
					+ usuario
					+ "' AND senha LIKE BINARY '"
					+ senha
					+ "' THEN true ELSE false "
					+ "END AS 'Status' FROM Usuario USE INDEX(usuario, senha) LIMIT 1";

			ResultSet rs = stmt.executeQuery(sql);
			rs.next();

			status = rs.getBoolean(1);

			rs.close();

			return status;
		} catch (SQLException erro) {
			return false;
		} finally {
			try {
				con.close();
			} catch (SQLException erro) {
				erro.printStackTrace();
				return false;
			}
		}
	}

	public String ConsultaTabelaUsuarioNome(String usuario, char[] password) {
		String senha = String.valueOf(password);

		LerConfiguracoes();

		try {
			try {
				Class.forName(servidor);
			} catch (ClassNotFoundException erro) {
				erro.printStackTrace();
			}
			con = DriverManager.getConnection(urlBanco, usuarioBanco,
					senhaBanco);

			Statement stmt = con.createStatement();

			sql = "SELECT nome_usuario FROM Usuario USE INDEX(usuario, senha) WHERE usuario LIKE BINARY '"
					+ usuario
					+ "' AND senha LIKE BINARY '"
					+ senha
					+ "'"
					+ "LIMIT 1";

			ResultSet rs = stmt.executeQuery(sql);
			rs.next();

			String nome_usuario = rs.getString(1);

			rs.close();

			return nome_usuario;
		} catch (SQLException erro) {
			return "";
		} finally {
			try {
				con.close();
			} catch (SQLException erro) {
				erro.printStackTrace();
				return "";
			}
		}
	}

	public String ConsultaTabelaUsuarioUser(String nome_usuario) {

		LerConfiguracoes();

		try {
			try {
				Class.forName(servidor);
			} catch (ClassNotFoundException erro) {
				erro.printStackTrace();
			}
			con = DriverManager.getConnection(urlBanco, usuarioBanco,
					senhaBanco);

			Statement stmt = con.createStatement();

			sql = "SELECT usuario FROM Usuario USE INDEX(nomeUsuario) WHERE nome_usuario LIKE '"
					+ nome_usuario + "' LIMIT 1";

			ResultSet rs = stmt.executeQuery(sql);
			rs.next();

			String usuario = rs.getString(1);

			rs.close();

			return usuario;
		} catch (SQLException erro) {
			return "";
		} finally {
			try {
				con.close();
			} catch (SQLException erro) {
				erro.printStackTrace();
				return "";
			}
		}
	}

	public void AlterarTabelaUsuario(String nome_usuario_ant,
			String nome_usuario, String usuario, char[] password) {
		String senha = String.valueOf(password);

		LerConfiguracoes();

		try {
			try {
				Class.forName(servidor);
			} catch (ClassNotFoundException erro) {
				erro.printStackTrace();
			}
			con = DriverManager.getConnection(urlBanco, usuarioBanco,
					senhaBanco);

			sql = "UPDATE Usuario USE INDEX(nomeUsuario) SET nome_usuario = ?, usuario = ?, senha = ? WHERE nome_usuario = ?";

			java.sql.PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setString(1, nome_usuario);
			stmt.setString(2, usuario);
			stmt.setString(3, senha);
			stmt.setString(4, nome_usuario_ant);

			stmt.executeUpdate();
			stmt.close();

			JOptionPane.showMessageDialog(null,
					"Alteração de usuário realizada com sucesso!",
					"Alteração Realizada!", JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException erro) {
			JOptionPane.showMessageDialog(null,
					"Erro ao realizar alteração de usuário!", "Erro",
					JOptionPane.ERROR_MESSAGE);
			erro.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException erro) {
				erro.printStackTrace();
			}
		}
	}
}