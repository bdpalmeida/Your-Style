package br.com.YourStyle_Forms;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import br.com.YourStyle_BD.ConexaoBD;

@SuppressWarnings("serial")
public class Principal extends JFrame{
	
	private ConexaoBD connBD = new ConexaoBD();
	
	private Abrir abrir = new Abrir();
	
	private String nome_usuario;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {					
					Principal window = new Principal(null);									
					window.setVisible(true);					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Principal( String nome_usuario ) {
		setNome_usuario(nome_usuario);		
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/br/com/YourStyle_Imagens/IconeYourStyle(Quadrado).png")));
		setMinimumSize(new Dimension(1024, 768));
		setTitle("Your Style");
		setExtendedState(MAXIMIZED_BOTH);		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();		
		setJMenuBar(menuBar);
		
		JMenu mnGerenciar = new JMenu("Gerenciar");		
		mnGerenciar.setMnemonic(KeyEvent.VK_G);		
		menuBar.add(mnGerenciar);
		
		JMenu mnClientes = new JMenu("Clientes");
		mnClientes.setMnemonic(KeyEvent.VK_C);
		mnGerenciar.add(mnClientes);
		
		JMenuItem mntmCadastrar_1 = new JMenuItem("Cadastrar");
		mntmCadastrar_1.setMnemonic(KeyEvent.VK_C);
		mntmCadastrar_1.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				AbreCadastroCliente();
			}
		});
		mnClientes.add(mntmCadastrar_1);
		
		JMenuItem mntmAlterar_1 = new JMenuItem("Alterar");
		mntmAlterar_1.setMnemonic(KeyEvent.VK_A);
		mntmAlterar_1.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				AbreAlterarClientes();
			}
		});
		mnClientes.add(mntmAlterar_1);
		
		JMenu mnProdutos = new JMenu("Produtos");
		mnProdutos.setMnemonic(KeyEvent.VK_P);
		mnGerenciar.add(mnProdutos);
		
		JMenuItem mntmCadastrar = new JMenuItem("Cadastrar");
		mntmCadastrar.setMnemonic(KeyEvent.VK_C);
		mntmCadastrar.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				AbreCadastroProduto();
			}
		});
		mnProdutos.add(mntmCadastrar);
		
		JMenuItem mntmAlterar = new JMenuItem("Alterar");
		mntmAlterar.setMnemonic(KeyEvent.VK_A);
		mntmAlterar.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				AbreAlterarProdutos();
			}
		});
		mnProdutos.add(mntmAlterar);
		
		JMenu mnVendas = new JMenu("Vendas");
		mnVendas.setMnemonic(KeyEvent.VK_V);
		menuBar.add(mnVendas);
		
		JMenuItem mntmNova = new JMenuItem("Nova");
		mntmNova.setMnemonic(KeyEvent.VK_N);
		mntmNova.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				AbreNovaVenda();
			}
		});
		mnVendas.add(mntmNova);
		
		JMenu mnPesquisar = new JMenu("Pesquisar");
		mnPesquisar.setMnemonic(KeyEvent.VK_P);
		menuBar.add(mnPesquisar);
		
		JMenuItem mntmClientes = new JMenuItem("Clientes");
		mntmClientes.setMnemonic(KeyEvent.VK_C);
		mntmClientes.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				AbrePesquisaClientes();
			}
		});
		mnPesquisar.add(mntmClientes);
		
		JMenuItem mntmProdutos = new JMenuItem("Produtos");
		mntmProdutos.setMnemonic(KeyEvent.VK_P);
		mntmProdutos.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				AbrePesquisaProdutos();				
			}
		});
		mnPesquisar.add(mntmProdutos);
		
		JMenuItem mntmVendas = new JMenuItem("Vendas");
		mntmVendas.setMnemonic(KeyEvent.VK_V);
		mntmVendas.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {				
				AbrePesquisaVendas();
			}
		});
		mnPesquisar.add(mntmVendas);
		
		JMenu mnDvida = new JMenu("Clientes");
		mnDvida.setMnemonic(KeyEvent.VK_C);
		menuBar.add(mnDvida);
		
		JMenuItem mntmDivida = new JMenuItem("D\u00EDvidas");
		mntmDivida.setMnemonic(KeyEvent.VK_D);
		mntmDivida.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				AbrePagarDivida();
			}
		});
		mnDvida.add(mntmDivida);
		
		JMenu mnRelatrio = new JMenu("Relat\u00F3rio");
		mnRelatrio.setMnemonic(KeyEvent.VK_R);
		menuBar.add(mnRelatrio);
		
		JMenuItem mntmVenda = new JMenuItem("Venda");
		mntmVenda.setMnemonic(KeyEvent.VK_V);
		mntmVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AbreRelatorioVenda();
			}
		});
		mnRelatrio.add(mntmVenda);
		
		JMenu mnControle = new JMenu("Controle");
		mnControle.setMnemonic(KeyEvent.VK_O);
		menuBar.add(mnControle);
		
		JMenuItem mntmUsurio = new JMenuItem("Usu\u00E1rio");
		mntmUsurio.setMnemonic(KeyEvent.VK_U);
		mntmUsurio.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				AbreControleUsuarios();
			}
		});
		mnControle.add(mntmUsurio);
		
		JMenu mnSobre = new JMenu("Ajuda");			
		mnSobre.setMnemonic(KeyEvent.VK_A);
		menuBar.add(mnSobre);
		
		JMenuItem mntmSobre = new JMenuItem("Sobre");
		mntmSobre.setMnemonic(KeyEvent.VK_S);
		mntmSobre.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				new Sobre().show();
			}
		});
		
		JMenuItem mntmManual = new JMenuItem("Manual");
		mntmManual.setMnemonic(KeyEvent.VK_M);
		mntmManual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String caminho = "C:/Conexão 3/Your Style/Manual.pdf";
				
				try {
					abrir.Mostrar(caminho);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Erro ao abrir o arquivo: " + caminho, 
							"Erro ao abrir arquivo", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnSobre.add(mntmManual);
		mnSobre.add(mntmSobre);
		
		JButton btnNovaVenda = new JButton("Nova Venda");
		btnNovaVenda.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				AbreNovaVenda();
			}
		});
		btnNovaVenda.setFont(new Font("SansSerif", Font.PLAIN, 18));
		btnNovaVenda.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNovaVenda.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnNovaVenda.setIcon(new ImageIcon(Principal.class.getResource("/br/com/YourStyle_Imagens/Flaticon_34330.png")));
		
		JButton btnPesquisarVenda = new JButton("Pesquisar Venda");
		btnPesquisarVenda.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				AbrePesquisaVendas();
			}
		});
		btnPesquisarVenda.setFont(new Font("SansSerif", Font.PLAIN, 18));
		btnPesquisarVenda.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPesquisarVenda.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnPesquisarVenda.setIcon(new ImageIcon(Principal.class.getResource("/br/com/YourStyle_Imagens/Flaticon_34202.png")));
		
		JButton btnCadastrarCliente = new JButton("Cadastrar Cliente");
		btnCadastrarCliente.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				AbreCadastroCliente();
			}
		});
		btnCadastrarCliente.setIcon(new ImageIcon(Principal.class.getResource("/br/com/YourStyle_Imagens/Flaticon_3673.png")));
		btnCadastrarCliente.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnCadastrarCliente.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCadastrarCliente.setFont(new Font("SansSerif", Font.PLAIN, 18));
		
		JButton btnCadastrarProduto = new JButton("Cadastrar Produtos");
		btnCadastrarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbreCadastroProduto();
			}
		});
		btnCadastrarProduto.setIcon(new ImageIcon(Principal.class.getResource("/br/com/YourStyle_Imagens/Flaticon_34341.png")));
		btnCadastrarProduto.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnCadastrarProduto.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCadastrarProduto.setFont(new Font("SansSerif", Font.PLAIN, 18));
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(Principal.class.getResource("/br/com/YourStyle_Imagens/YourStyleOKazul.png")));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNovaVenda, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnPesquisarVenda, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCadastrarCliente, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCadastrarProduto))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 1074, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(8)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnNovaVenda, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(6)
							.addComponent(btnPesquisarVenda, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
							.addGap(6)
							.addComponent(btnCadastrarCliente, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
							.addGap(6)
							.addComponent(btnCadastrarProduto, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)))
					.addGap(9))
		);
		groupLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {btnNovaVenda, btnPesquisarVenda, btnCadastrarCliente, btnCadastrarProduto});
		getContentPane().setLayout(groupLayout);		
	}

	protected void AbreRelatorioVenda() {
		if( connBD.RelatorioTabelaVendas("", "").length > 0)
			new Rel_Vendas().setVisible(true);
		else
			JOptionPane.showMessageDialog(null, "Nenhuma venda foi realizada!", "Vendas não realizadas!", JOptionPane.INFORMATION_MESSAGE);
	}

	protected void AbreControleUsuarios() {		
		new Controle_Usuario(getNome_usuario()).setVisible(true);
	}
	
	protected void AbrePagarDivida() {
		if( connBD.ConsultaTabelaClienteDivida("").length > 0 )
			new Pagar_Divida().setVisible(true);
		else
			JOptionPane.showMessageDialog(null, "Não há clientes com dívidas!", "Clientes não têm dívidas!", JOptionPane.INFORMATION_MESSAGE);
	}

	protected void AbrePesquisaVendas() {
		if( connBD.ConsultarTabelaVendas("", "", "").length > 0)
			new Pesq_Vendas().setVisible(true);
		else
			JOptionPane.showMessageDialog(null, "Nenhuma venda foi realizada!", "Vendas não realizadas!", JOptionPane.INFORMATION_MESSAGE);
	}

	protected void AbrePesquisaProdutos() {
		if( connBD.ConsultaTabelaProduto("","").length > 0 )
			new Pesq_Produtos().setVisible(true);
		else
			JOptionPane.showMessageDialog(null, "Não há produtos cadastrados!", "Produtos não cadastrados!", JOptionPane.ERROR_MESSAGE);
	}

	protected void AbrePesquisaClientes() {
		if( connBD.TestaConexao() )
			if( connBD.ConsultaTabelaCliente("todos*","","").length > 0 )
				new Pesq_Clientes().setVisible(true);
			else
				JOptionPane.showMessageDialog(null, "Não há clientes cadastrados!", "Clientes não cadastrados!", JOptionPane.ERROR_MESSAGE);		
	}

	protected void AbreNovaVenda() {
		if( connBD.TestaConexao() )
			if( connBD.ConsultaTabelaCliente("todos*","","").length == 0 )
				JOptionPane.showMessageDialog(null, "Não há clientes cadastrados!", "Clientes não cadastrados!", JOptionPane.ERROR_MESSAGE);
			else
				if( connBD.ConsultaTabelaProduto("todos*estoque","").length == 0 )
					JOptionPane.showMessageDialog(null, "Não há produtos cadastrados, ou não há estoque de produtos!", "Produtos não cadastrados, ou estoque vazio!", JOptionPane.ERROR_MESSAGE);
				else						
					new Nova_Venda().setVisible(true);		
	}

	protected void AbreAlterarProdutos() {
		if( connBD.TestaConexao() )
			if( connBD.ConsultaTabelaProduto("","").length > 0)
				new Alterar_Produtos().setVisible(true);
			else
				JOptionPane.showMessageDialog(null, "Não há produtos cadastrados!", "Produtos não cadastrados!", JOptionPane.ERROR_MESSAGE);		
	}

	protected void AbreCadastroProduto() {
		if( connBD.TestaConexao() )
			new Cad_Produtos().setVisible(true);		
	}

	protected void AbreAlterarClientes() {
		if( connBD.TestaConexao() )
			if( connBD.ConsultaTabelaCliente("", "", "").length > 0 )						
				new Alterar_Clientes().setVisible(true);
			else
				JOptionPane.showMessageDialog(null, "Não há clientes cadastrados!", "Clientes não cadastrados!", JOptionPane.ERROR_MESSAGE);		
	}

	protected void AbreCadastroCliente() {
		if( connBD.TestaConexao() )
			new Cad_Clientes().setVisible(true);		
	}
	
	public String getNome_usuario() {
		return nome_usuario;
	}

	public void setNome_usuario(String nome_usuario) {
		this.nome_usuario = nome_usuario;
	}
}