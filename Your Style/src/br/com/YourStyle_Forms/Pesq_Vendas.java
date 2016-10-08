package br.com.YourStyle_Forms;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;

import javax.swing.DebugGraphics;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.text.MaskFormatter;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import br.com.YourStyle_BD.ConexaoBD;

@SuppressWarnings("serial")
public class Pesq_Vendas extends JDialog {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable tableVenda;
	private JButton btnFechar;
	
	private String linhas[][] = null,
				   colunas[] = {"Nome do cliente", "CPF do cliente", "Valor total", "Data da venda"},
				   CPF;
	
	private ConexaoBD connBD = new ConexaoBD();
	private JTextField txtNomeCliente;
	private JFormattedTextField frmtdtxtfldData;
	private JButton btnPesquisar;
	
	private MaskFormatter data, cpf;
	private JFormattedTextField frmtdtxtfldCpf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pesq_Vendas frame = new Pesq_Vendas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Pesq_Vendas() {
		setType(Type.UTILITY);
		setMinimumSize(new Dimension(450, 360));
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Pesquisar Vendas");
		setBounds(100, 100, 580, 480);
		setLocationRelativeTo(null);		
		contentPane = new JPanel();
		contentPane.setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel label = new JLabel("");
		label.setLabelFor(contentPane);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon(Pesq_Vendas.class.getResource("/br/com/YourStyle_Imagens/Flaticon_34202.png")));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("SansSerif", Font.PLAIN, 12));	
		
		scrollPane = new JScrollPane();
		
		btnFechar = new JButton("Fechar");
		btnFechar.setMnemonic(KeyEvent.VK_F);
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setMnemonic(KeyEvent.VK_P);
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if( txtNomeCliente.isShowing() )
					PesquisarNomeCliente(txtNomeCliente.getText().trim(),"");
				if( frmtdtxtfldCpf.isShowing() )
					PesquisarCPFCliente("", "", CPF);
				if( frmtdtxtfldData.isShowing() )
					if( frmtdtxtfldData.getText().replace("/", "").replace("_", "").length() == 8 )
						PesquisarData("", frmtdtxtfldData.getText().replace("/", ""));
					else
						JOptionPane.showMessageDialog(null, "Complete a data!", "Data incompleta!", JOptionPane.INFORMATION_MESSAGE);				
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap(482, Short.MAX_VALUE)
							.addComponent(btnFechar))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(153)
									.addComponent(label, GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
									.addGap(47))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addContainerGap()
									.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addComponent(btnPesquisar)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(label)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnPesquisar))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnFechar))
		);
		
		txtNomeCliente = new JTextField();
		txtNomeCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {			
				if( arg0.getKeyChar() == KeyEvent.VK_ENTER )
					PesquisarNomeCliente(txtNomeCliente.getText().trim(),"");				
			}
		});
		tabbedPane.addTab("Nome do cliente", null, txtNomeCliente, null);
		txtNomeCliente.setColumns(10);
		
		try {
			data = new MaskFormatter("##/##/####");
			data.setPlaceholderCharacter('_');
		} catch (ParseException e1) {			
			e1.printStackTrace();
		}
		
		frmtdtxtfldData = new JFormattedTextField(data);
		frmtdtxtfldData.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {	
				if( arg0.getKeyChar() == KeyEvent.VK_ENTER )					
					if( frmtdtxtfldData.getText().replace("_", "").replace("/", "").length() == 8 )
						PesquisarData("",frmtdtxtfldData.getText().replace("/", ""));
					else
						JOptionPane.showMessageDialog(null, "Complete a data!", "Data incompleta!", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		try {
			cpf = new MaskFormatter("###.###.###-##");
			cpf.setPlaceholderCharacter('_');
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		frmtdtxtfldCpf = new JFormattedTextField(cpf);
		frmtdtxtfldCpf.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				CPF = frmtdtxtfldCpf.getText().trim();
			}
		});
		frmtdtxtfldCpf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if( arg0.getKeyChar() == KeyEvent.VK_ENTER )
					PesquisarCPFCliente("", "", frmtdtxtfldCpf.getText());
			}
		});
		tabbedPane.addTab("CPF do cliente", null, frmtdtxtfldCpf, null);
		tabbedPane.addTab("Data", null, frmtdtxtfldData, null);
		frmtdtxtfldData.setColumns(10);
		
		linhas = connBD.ConsultarTabelaVendas("","","");		
		CarregarTabela();

		contentPane.setLayout(gl_contentPane);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{scrollPane, tableVenda, btnFechar, tabbedPane, label}));
	}
	
	public void PesquisarNomeCliente(String ondeNomeCliente, String data) {		
		linhas = connBD.ConsultarTabelaVendas(ondeNomeCliente, data, "");
		
		CarregarTabela();
	}

	public void PesquisarData(String ondeNomeCliente, String data) {		
		
		String dia, mes, ano;
		dia = String.valueOf(data.charAt(0)) + String.valueOf(data.charAt(1));
		mes = String.valueOf(data.charAt(2)) + String.valueOf(data.charAt(3));
		ano = String.valueOf(data.charAt(4)) + String.valueOf(data.charAt(5)) + 
				String.valueOf(data.charAt(6)) + String.valueOf(data.charAt(7));	
		
		linhas = connBD.ConsultarTabelaVendas(ondeNomeCliente, ano + "-" + mes + "-" + dia, "");
		
		CarregarTabela();
	}
	
	public void PesquisarCPFCliente(String ondeNomeCliente, String data, String cpf){		
		linhas = connBD.ConsultarTabelaVendas("", "", cpf);
		
		CarregarTabela();		
	}

	private void CarregarTabela() {		
		tableVenda = new JTable(linhas, colunas){
		    public boolean isCellEditable(int rowIndex, int vColIndex) {
	             return false;
		    }
		};		
		tableVenda.setShowVerticalLines(true);
		tableVenda.setShowHorizontalLines(true);
		tableVenda.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);		
		
		TableColumn col = tableVenda.getColumnModel().getColumn(0);
		TableColumn col2 = tableVenda.getColumnModel().getColumn(1);
		TableColumn col3 = tableVenda.getColumnModel().getColumn(2);	
		TableColumn col4 = tableVenda.getColumnModel().getColumn(3);
		
		col.setPreferredWidth(200);
		col2.setPreferredWidth(100);
		col2.setResizable(false);
		col3.setPreferredWidth(111);		
		col4.setPreferredWidth(125);
		col4.setResizable(false);		
		
		scrollPane.setViewportView(tableVenda);		
	}
}