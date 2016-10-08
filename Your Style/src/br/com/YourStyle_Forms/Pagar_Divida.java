package br.com.YourStyle_Forms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
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

import br.com.YourStyle_BD.ConexaoBD;
import java.awt.Dimension;
import java.awt.Component;

@SuppressWarnings("serial")
public class Pagar_Divida extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable tableClientesDividas;
	private JScrollPane scrollPane;
	private JTextField txtPago;
	private JButton okButton;
	private String linhas[][], 
				   colunas[] = {"Nome do cliente","CPF do cliente","Valor da dívida"},
				   caracteres="0987654321",
				   nome_cliente, cpf;
	private Double vl_divida;	
	
	private ConexaoBD connBD = new ConexaoBD();
	private JTextField txtNome;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {		
		}
		try {
			Pagar_Divida dialog = new Pagar_Divida();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {			
		}
	}

	/**
	 * Create the dialog.
	 */
	public Pagar_Divida() {
		setMinimumSize(new Dimension(460, 460));
		setType(Type.UTILITY);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setTitle("Pagamento D\u00EDvida");
		setBounds(100, 100, 460, 460);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel label = new JLabel("");
		label.setAlignmentY(Component.TOP_ALIGNMENT);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon(Pagar_Divida.class.getResource("/br/com/YourStyle_Imagens/Flaticon_13694.png")));
		JLabel lblCliente = new JLabel("Selecione o cliente na tabela abaixo:");
		scrollPane = new JScrollPane();
		
		JLabel lblPago = new JLabel("Pago pelo Cliente:");
		lblPago.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		
		txtPago = new JTextField();
		txtPago.setAlignmentX(Component.LEFT_ALIGNMENT);
		txtPago.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		txtPago.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {				
				if( !caracteres.concat(".").contains(arg0.getKeyChar()+"") )
					arg0.consume();
				
				try{				
					if( Double.parseDouble(txtPago.getText().concat("" + arg0.getKeyChar())) > vl_divida ){
						JOptionPane.showMessageDialog(null, "Valor pago pelo cliente excede o valor de sua dívida!", "Valor pago excede sua dívida!", JOptionPane.ERROR_MESSAGE);
						arg0.consume();
					}
					else
						if( Double.parseDouble(txtPago.getText().concat("" + arg0.getKeyChar())) > 0 )
							okButton.setEnabled(true);
						else
							okButton.setEnabled(false);
				}
				catch( NumberFormatException erro ){
					okButton.setEnabled(false);
					JOptionPane.showMessageDialog(null, "Insira o valor corretamente.","Valor incorreto!",JOptionPane.ERROR_MESSAGE);
					arg0.consume();					
				}					
			}
		});
		txtPago.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				txtPago.setText(txtPago.getText().replace("R$ ", ""));				
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				txtPago.setText("R$ " + txtPago.getText().trim());
			}
		});
		txtPago.setEnabled(false);
		txtPago.setText("R$ ");
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setAlignmentY(Component.TOP_ALIGNMENT);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setAlignmentY(Component.TOP_ALIGNMENT);
		btnPesquisar.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtPago.setText("R$ ");
				txtPago.setEnabled(false);
				okButton.setEnabled(false);
				nome_cliente = "";
				cpf = "";
				vl_divida = 0.0;
				
				linhas = connBD.ConsultaTabelaClienteDivida(txtNome.getText().trim());				
				CarregaTabelaClientesDividas();
			}
		});
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(7)
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPesquisar)
					.addContainerGap())
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblCliente)
					.addContainerGap(228, Short.MAX_VALUE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(147)
							.addComponent(label, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
							.addGap(143))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblPago)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtPago, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(199, Short.MAX_VALUE))))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(label)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnPesquisar))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblCliente)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPago)
						.addComponent(txtPago, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(0))
		);
		
		txtNome = new JTextField();
		tabbedPane.addTab("Nome do cliente", null, txtNome, null);
		tabbedPane.setEnabledAt(0, true);
		txtNome.setColumns(10);
		
		linhas = connBD.ConsultaTabelaClienteDivida("");
		
		CarregaTabelaClientesDividas();
		
		scrollPane.setViewportView(tableClientesDividas);

		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Acertar");
				okButton.setMnemonic(KeyEvent.VK_A);
				okButton.setEnabled(false);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						connBD.PagarDividaCliente(nome_cliente, cpf, Double.parseDouble(txtPago.getText().replace("R$ ", "")));
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.setMnemonic(KeyEvent.VK_CANCEL);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}		
	}
	
	private void CarregaTabelaClientesDividas(){		
		tableClientesDividas = new JTable(linhas, colunas){
		    public boolean isCellEditable(int rowIndex, int vColIndex) {
	             return false;
		    }
		};
		tableClientesDividas.getTableHeader().setReorderingAllowed(false);
		tableClientesDividas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				txtPago.setText("R$ ");
				txtPago.setEnabled(false);
				okButton.setEnabled(false);
				nome_cliente = "";
				cpf = "";
				vl_divida = 0.0;
				
				if (arg0.getClickCount() == 2){					
					nome_cliente = tableClientesDividas.getModel().getValueAt(tableClientesDividas.getSelectedRow(), 0).toString();
					cpf = tableClientesDividas.getModel().getValueAt(tableClientesDividas.getSelectedRow(), 1).toString();
					vl_divida = Double.parseDouble(tableClientesDividas.getModel().getValueAt(tableClientesDividas.getSelectedRow(), 2).toString());
					
					if( JOptionPane.showConfirmDialog(null, "Pagar dívida do cliente:\n" + nome_cliente + " - " + cpf + "\nCorreto?", "Confirmação de cliente", JOptionPane.YES_NO_OPTION) == 0 ){
						JOptionPane.showMessageDialog(null, "Digite o valor pago pelo cliente.", "Informação.", JOptionPane.INFORMATION_MESSAGE);					
						txtPago.setEnabled(true);
						txtPago.requestFocus();					
					}
					else{
						nome_cliente = "";
						cpf = "";
						vl_divida = 0.0;
					}
				}
			}
		});
		tableClientesDividas.setToolTipText("Selecione o cliente na tabela (Dois cliques sobre a linha).");
		tableClientesDividas.setShowVerticalLines(true);
		tableClientesDividas.setShowHorizontalLines(true);		
		tableClientesDividas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		TableColumn col = tableClientesDividas.getColumnModel().getColumn(0);
		TableColumn col2 = tableClientesDividas.getColumnModel().getColumn(1);
		TableColumn col3 = tableClientesDividas.getColumnModel().getColumn(2);		
		
		col.setPreferredWidth(226);
		col2.setPreferredWidth(100);
		col2.setResizable(false);
		col3.setPreferredWidth(90);
		
		scrollPane.setViewportView(tableClientesDividas);
	}
}
