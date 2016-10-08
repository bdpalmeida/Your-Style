package br.com.YourStyle_Forms;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import br.com.YourStyle_BD.ConexaoBD;
import br.com.YourStyle_Models.ModelCad_Produtos;

@SuppressWarnings("serial")
public class Cad_Produtos extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNome,  txtPreco, txt = new JTextField();
	private JComboBox<String> cbxTamanho;
	private JSpinner sQuantidade, spinner = new JSpinner();
	private JTextArea txtrDetalhes;
	private String caracteres="0987654321";
	
	private ConexaoBD connBD = new ConexaoBD();
	
	private ModelCad_Produtos modelo = new ModelCad_Produtos();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			Cad_Produtos dialog = new Cad_Produtos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Cad_Produtos() {
		setType(Type.UTILITY);
		setResizable(false);
		setModal(true);
		setTitle("Cadastro de Produtos");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 468, 447);
		setLocationRelativeTo(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setMnemonic(KeyEvent.VK_C);
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( ColoreCamposInvalidos() )
					JOptionPane.showMessageDialog(null, 
							"Preencha corretamente os campos obrigatórios", "Erro, campos inválidos!", 
							JOptionPane.ERROR_MESSAGE);	
				else{
					modelo.setNome(txtNome.getText().trim());
					modelo.setTamanho(cbxTamanho.getSelectedItem().toString().trim());
					modelo.setQuantidade(Integer.parseInt(sQuantidade.getValue().toString()));
					modelo.setPreco(Double.parseDouble("0" + txtPreco.getText().replace("R$ ", "")));
					modelo.setDetalhes(txtrDetalhes.getText().trim());
					
					if( connBD.InserirTabelaProduto(modelo.getNome(), modelo.getTamanho(), modelo.getQuantidade(), 
							modelo.getPreco(), modelo.getDetalhes()) )
						LimparCampos();
					else
						LimparCampoNomeTamanho();					
				}
			}
		});
		btnCadastrar.setActionCommand("OK");
		
		JButton btnFechar = new JButton("Fechar");
		btnFechar.setMnemonic(KeyEvent.VK_F);
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnFechar.setActionCommand("Cancel");
		
		JLabel lblDetalhes = new JLabel("Detalhes");
		lblDetalhes.setAlignmentY(Component.TOP_ALIGNMENT);
		
		JLabel lblPreco = new JLabel("Pre\u00E7o (U)*");
		lblPreco.setAlignmentY(Component.TOP_ALIGNMENT);
		
		txtPreco = new JTextField();
		txtPreco.setAlignmentY(Component.TOP_ALIGNMENT);
		txtPreco.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if( !caracteres.concat(".").contains(e.getKeyChar()+"") )
					e.consume();	
				try{					
					Double.parseDouble(txtPreco.getText() + e.getKeyChar());										
				}
				catch(NumberFormatException erro){					
					JOptionPane.showMessageDialog(null, "Insira o valor unitário do produto corretamente!", "Valor incorreto!", JOptionPane.ERROR_MESSAGE);
					e.consume();
				}
			}
		});
		txtPreco.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if( Double.parseDouble("0" + txtPreco.getText().replace("R$ ", "")) <= 0 )
					txtPreco.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));			
				else
					txtPreco.setBorder(txt.getBorder());
				
				if( !txtPreco.getText().startsWith("R$ ") )
					txtPreco.setText("R$ " + txtPreco.getText());
			}
			@Override
			public void focusGained(FocusEvent arg0) {
				txtPreco.setText(txtPreco.getText().replace("R$ ", ""));
			}
		});
		txtPreco.setColumns(10);
		
		sQuantidade = new JSpinner();
		sQuantidade.setAlignmentY(Component.TOP_ALIGNMENT);
		sQuantidade.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		
		JLabel lblQuantidade = new JLabel("Quantidade (U)*");
		lblQuantidade.setAlignmentY(Component.TOP_ALIGNMENT);
		
		JLabel lblTamanho = new JLabel("Tamanho");
		lblTamanho.setAlignmentY(Component.TOP_ALIGNMENT);
		
		cbxTamanho = new JComboBox<String>();
		cbxTamanho.setEditable(true);
		cbxTamanho.setAlignmentY(Component.TOP_ALIGNMENT);
		cbxTamanho.addItem("");
		cbxTamanho.addItem("PP");
		cbxTamanho.addItem("P");
		cbxTamanho.addItem("M");
		cbxTamanho.addItem("G");
		cbxTamanho.addItem("GG");
		
		JLabel lblNome = new JLabel("Nome*");
		lblNome.setAlignmentY(Component.TOP_ALIGNMENT);
		
		txtNome = new JTextField();
		txtNome.setAlignmentY(Component.TOP_ALIGNMENT);
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(caracteres.contains(e.getKeyChar()+""))
					e.consume();
			}
		});
		txtNome.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if( txtNome.getText().length() < 2 )
					txtNome.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));			
				else
					txtNome.setBorder(txt.getBorder());	
			}
		});
		txtNome.setColumns(10);
		
		JLabel label_5 = new JLabel("");
		label_5.setAlignmentY(Component.TOP_ALIGNMENT);
		label_5.setIcon(new ImageIcon(Cad_Produtos.class.getResource("/br/com/YourStyle_Imagens/Flaticon_34341.png")));
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel label = new JLabel("* Campos Obrigat\u00F3rios");
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(153)
					.addComponent(label_5, GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
					.addGap(149))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap(285, Short.MAX_VALUE)
					.addComponent(btnCadastrar)
					.addGap(5)
					.addComponent(btnFechar))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(6)
							.addComponent(lblTamanho, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNome))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblQuantidade)
								.addComponent(lblDetalhes, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblPreco)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(txtPreco, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
							.addGap(211))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(sQuantidade, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
							.addGap(258))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(cbxTamanho, 0, 75, Short.MAX_VALUE)
							.addGap(258))
						.addComponent(txtNome, GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE))
					.addContainerGap())
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(306, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(txtNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(146)
							.addComponent(lblNome)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(lblTamanho))
						.addComponent(cbxTamanho, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(sQuantidade, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(6)
							.addComponent(lblQuantidade)))
					.addGap(6)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(txtPreco, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(6)
							.addComponent(lblPreco)))
					.addGap(12)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDetalhes)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnCadastrar)
						.addComponent(btnFechar)))
		);
		gl_contentPanel.linkSize(SwingConstants.VERTICAL, new Component[] {txtPreco, sQuantidade, cbxTamanho, txtNome});
		
		txtrDetalhes = new JTextArea();
		txtrDetalhes.setAlignmentY(Component.TOP_ALIGNMENT);
		scrollPane.setViewportView(txtrDetalhes);
		contentPanel.setLayout(gl_contentPanel);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(contentPanel, GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(contentPanel, GroupLayout.PREFERRED_SIZE, 406, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(7, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
	}
	
	protected void LimparCampoNomeTamanho() {
		txtNome.setText("");
		cbxTamanho.setSelectedIndex(0);
	}

	public boolean ColoreCamposInvalidos(){
		boolean camposInvalidos = false;	
		
		if( txtNome.getText().length() < 3 ){
			txtNome.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));
			camposInvalidos = true;
		}		
		if( Integer.parseInt(sQuantidade.getValue().toString()) == 0 ){
			sQuantidade.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));
			camposInvalidos = true;
		}
		else
			sQuantidade.setBorder(spinner.getBorder());		
		if( Double.parseDouble("0" + txtPreco.getText().replace("R$ ", "")) <= 0 ){
			txtPreco.setBorder(new LineBorder(new Color(255, 0, 0), 1, true));		
			camposInvalidos = true;
		}

		return camposInvalidos;		
	}
	
	public void LimparCampos() {
		txtNome.setText("");
		cbxTamanho.setSelectedIndex(0);
		sQuantidade.setValue(1);
		txtPreco.setText("");
		txtrDetalhes.setText("");
	}
}
