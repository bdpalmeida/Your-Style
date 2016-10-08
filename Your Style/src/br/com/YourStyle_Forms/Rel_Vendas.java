package br.com.YourStyle_Forms;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import br.com.YourStyle_BD.ConexaoBD;

@SuppressWarnings("serial")
public class Rel_Vendas extends JDialog {
	private JLabel label;
	private final JScrollPane scrollPane = new JScrollPane();
	private JButton btnFechar;
	private JTable tableRelatorio;
	private final JSpinner sDataInicio, sDataFim;
	private final JButton btnPesquisar = new JButton("Pesquisar");
	private final JButton btnHoje = new JButton("Hoje");
	private JPanel panel;
	private JLabel lblPeriodoVenda;
	private final JTextField txtRendimentoTotal = new JTextField();
	private JLabel lblRendimentoTotal;
	private final JButton btnTodas = new JButton("Todas");
	
	private String linhas[][],
				   colunas[] = {"Rendimento"};
	
	private Date dataSistema = Calendar.getInstance().getTime();
	private SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
	
	private ConexaoBD connBD = new ConexaoBD();
	

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
					Rel_Vendas dialog = new Rel_Vendas();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Rel_Vendas() {
		setMinimumSize(new Dimension(450, 395));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setType(Type.UTILITY);
		setTitle("Relat\u00F3rio de Venda");
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 450, 483);
		setLocationRelativeTo(null);
		
		label = new JLabel("");
		label.setIcon(new ImageIcon(Rel_Vendas.class.getResource("/br/com/YourStyle_Imagens/Flaticon_32021.png")));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		btnFechar = new JButton("Fechar");
		btnFechar.setMnemonic(KeyEvent.VK_F);
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnFechar.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnFechar.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtRendimentoTotal.setEditable(false);
		txtRendimentoTotal.setColumns(10);
		
		lblRendimentoTotal = new JLabel("Rendimento Total");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(153)
					.addComponent(label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(153))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnPesquisar, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
						.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE))
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(362, Short.MAX_VALUE)
					.addComponent(btnFechar)
					.addContainerGap())
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblRendimentoTotal)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtRendimentoTotal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(202, Short.MAX_VALUE))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
					.addContainerGap())
		);
		btnPesquisar.setToolTipText("Pesquisar redimento por per\u00EDodo");
		btnPesquisar.setMnemonic(KeyEvent.VK_ENTER);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPesquisar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRendimentoTotal)
						.addComponent(txtRendimentoTotal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnFechar)
					.addContainerGap())
		);
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				linhas = connBD.RelatorioTabelaVendas(formatoData.format(sDataInicio.getValue()).toString(),														
														formatoData.format(sDataFim.getValue()).toString());
				CarregaTabela();
				
			}
		});
		
		lblPeriodoVenda = new JLabel("Per\u00EDodo da venda");
		lblPeriodoVenda.setHorizontalAlignment(SwingConstants.CENTER);		
		
		SpinnerDateModel sDateModelI = new SpinnerDateModel(dataSistema, null, null, Calendar.HOUR_OF_DAY);		
		sDataInicio = new JSpinner();
		sDataInicio.setModel(sDateModelI);
		JSpinner.DateEditor dEditorI = new DateEditor(sDataInicio, "dd/MM/yyyy");
		sDataInicio.setEditor(dEditorI);
		
		sDataInicio.setPreferredSize(new Dimension(74, 28));
		sDataInicio.setMinimumSize(new Dimension(74, 28));
		
		
		SpinnerDateModel sDateModelF = new SpinnerDateModel(dataSistema, null, null, Calendar.HOUR_OF_DAY);
		sDataFim = new JSpinner();
		sDataFim.setModel(sDateModelF);
		JSpinner.DateEditor dEditorF = new DateEditor(sDataFim, "dd/MM/yyyy");
		sDataFim.setEditor(dEditorF);
		
		sDataFim.setPreferredSize(new Dimension(74, 28));
		sDataFim.setMinimumSize(new Dimension(74, 28));
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnTodas)
					.addGap(43)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(lblPeriodoVenda, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(sDataInicio, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(sDataFim, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(50)
					.addComponent(btnHoje)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblPeriodoVenda)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(6)
							.addComponent(btnTodas)
							.addContainerGap())
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(6)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(sDataInicio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(sDataFim, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnHoje, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())))
		);
		gl_panel.linkSize(SwingConstants.HORIZONTAL, new Component[] {sDataInicio, sDataFim});
		btnHoje.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		btnHoje.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnTodas.setToolTipText("Todos os rendimentos");
		btnTodas.setMnemonic(KeyEvent.VK_T);
		btnTodas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				linhas = connBD.RelatorioTabelaVendas("", "");
				CarregaTabela();
			}
		});
		btnHoje.setToolTipText("Redimento do dia");
		btnHoje.setMnemonic(KeyEvent.VK_H);
		btnHoje.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {				
				sDataInicio.setValue(dataSistema);
				sDataFim.setValue(dataSistema);
			}
		});
		sDataInicio.setToolTipText("Data inicial");
		sDataFim.setToolTipText("Data final");
		panel.setLayout(gl_panel);
		
		linhas = connBD.RelatorioTabelaVendas("", "");		
		CarregaTabela();
		
		getContentPane().setLayout(groupLayout);
	}

	protected void CarregaTabela() {
		tableRelatorio = new JTable(linhas, colunas){
		    public boolean isCellEditable(int rowIndex, int vColIndex) {
	             return false;
		    }
		};	
		tableRelatorio.setShowHorizontalLines(true);
		scrollPane.setViewportView(tableRelatorio);
		
		
		double rendTotal = 0;
		for( int cont = 0; cont < linhas.length; cont++ ){			
			rendTotal += Double.parseDouble(linhas[cont][0].replace("R$ ", "")); 
		}
		txtRendimentoTotal.setText("R$ " + rendTotal);
	}
}
