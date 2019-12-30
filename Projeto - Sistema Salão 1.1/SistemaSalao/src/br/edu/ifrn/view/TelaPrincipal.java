package br.edu.ifrn.view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author eaudevie
 */

public class TelaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form TelaPrincipal
     */
    public TelaPrincipal() {
        initComponents();
        this.setLocationRelativeTo(null); //Fixar tela no meio ao abrir.
        setResizable(false); //Função para não permitir o rendimensionamento da tela.
        importarDados(); //Importando os dados do arquivo no exato momento que a tela é aberta.
        ordenarTabela();
    }

    //Função utilitária necessária para importar os dados gravados no arquivo para a JTable.
    public void importarDados() {
        String filePath = "C:\\Users\\Eau de Vie\\Desktop\\Projeto - Sistema Salão 1.1\\SistemaSalao\\dist\\logServicos.txt";
        File file = new File(filePath);

        try {
            FileReader fr = new FileReader(file); //Construtor que recebe o objeto do tipo arquivo
            BufferedReader br = new BufferedReader(fr); //Lendo a entrada de dado, no caso, o buffer do arquivo.
            //Pegando o modelo da minha tabela através da classse DefaultTableModel
            DefaultTableModel model = (DefaultTableModel) tabelaservicos.getModel();

            // Pegando as linhas do arquivo TXT (linha por linha e adicionando em um array)
            Object[] lines = br.lines().toArray();

            // Extraindo os dados do arquivo
            // Setando os dados na Jtable
            for (int i = 0; i < lines.length; i++) {

                String line = lines[i].toString().trim(); //Retirando espaços fora de padrão do inicio e do fim das linhas
                String[] dataRow = line.split(","); //Separando os dados da linha por ","
                model.addRow(dataRow);

            }
        } catch(Exception ex) {
            ex.getMessage();
        }

    }
    //Função utilitária necessária para exportar os dados gravados na JTable para o arquivo.
    public void exportarDados() {
        String filePath = "C:\\Users\\Eau de Vie\\Desktop\\Projeto - Sistema Salão 1.1\\SistemaSalao\\dist\\logServicos.txt";
        File file = new File(filePath);
        try {
            FileWriter fw = new FileWriter(file); //Construtor que recebe o objeto do tipo arquivo
            BufferedWriter bw = new BufferedWriter(fw); //Construtor dp BW recebendo como argumento o objeto do tipo FileWriter
            //Percorrendo todas as linhas e colunas da tabela e escrevendo tudo
            //no arquivo de texto utilizando a função "write" da classe BufferedWriter.
            for (int i = 0; i < tabelaservicos.getRowCount(); i++) { //Linhas
                for (int j = 0; j < tabelaservicos.getColumnCount(); j++) { //Colunas
                    bw.write(tabelaservicos.getValueAt(i, j).toString() + ",");
                }
                bw.newLine(); //Quebra de linha (método exclusivo do BufferedWriter).
            }

            bw.close();
            fw.close();

        } catch(IOException ex) {
            ex.getMessage();
        }
    }

    //Função para apagar determinada linha da JTable
    public void deletarJTable() {

        //Pegando o modelo da minha tabela através da classse DefaultTableModel
        DefaultTableModel model = (DefaultTableModel) tabelaservicos.getModel();

        if (tabelaservicos.getSelectedRow() == -1) {
            if (tabelaservicos.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "A tabela está vazia!", "Erro!", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Você deve selecionar o serviço que deseja excluir!", "Erro!", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            try {
                Object[] op = {
                    "Sim",
                    "Não"
                };
                Object selected = op[0];
                selected = JOptionPane.showInputDialog(null, "Deseja remover o serviço Selecionado?", "Remover Serviço", JOptionPane.INFORMATION_MESSAGE, null, op, op[0]);
                switch (selected.toString()) {

                case "Sim":
                    JPasswordField password = new JPasswordField(15);

                    JLabel rotulo = new JLabel("Insira sua senha: ");
                    JPanel entUsuario = new JPanel();
                    entUsuario.add(rotulo);
                    entUsuario.add(password);

                    JOptionPane.showMessageDialog(null, entUsuario, "Acesso restrito", JOptionPane.PLAIN_MESSAGE);

                    String senha = password.getText();

                    if (senha.equals("fran123")) {

                        int rows[] = tabelaservicos.getSelectedRows();

                        for (int row: rows) {
                            model.removeRow(tabelaservicos.convertRowIndexToModel(row));
                        }

                        txtfieldpesquisar.setText("");
                        exportarDados();

                    } else {
                        JOptionPane.showMessageDialog(null, "Senha incorreta, tente novamente!", "ERRO!", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case "Não":
                    JOptionPane.showMessageDialog(null, "Ok", "Remover Serviço", JOptionPane.INFORMATION_MESSAGE);
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opção Inválida", "Remover Serviço", JOptionPane.ERROR_MESSAGE);
                    break;

                }
            } catch(NullPointerException npe) {
                JOptionPane.showMessageDialog(null, "Operação Cancelada!", "Cancel", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    //Limpando os JTextFields da Tela
    public void limparCampos() {
        txtfieldnome.setText("");
        txtfieldcontato.setText("");
        txtfieldendereco.setText("");
        txtfieldvalor.setText("");
        txtfielddesconto.setText("");
        txtfielddia.setText("");
        txtfieldhora.setText("");
        txtfieldid.setText("");
        txtfieldservico.setText("");
        txtfieldfinal.setText("");
    }

    //Função que ordena a tabela por data (padrão) de forma Crescente
    public void ordenarTabela() {

        //Criando um modelo específico de RowSorter e associando o RowSorter com o JTable.    
        TableRowSorter < TableModel > sorter = new TableRowSorter < TableModel > (tabelaservicos.getModel());
        tabelaservicos.setRowSorter(sorter);

        //Criando um ArrayList com as chaves de ordenação.
        ArrayList < RowSorter.SortKey > sortKeys = new ArrayList < >();

        int indiceColuna = 7;
        sortKeys.add(new RowSorter.SortKey(indiceColuna, SortOrder.ASCENDING));

        sorter.setSortKeys(sortKeys);
        sorter.sort();

    }

    //Método Simples onde a cada cadastramento ou atualização ele remove todas as linhas antigas da 
    //JTable e adiciona as novas linhas (importação).    
    public void limparTabela() {

        //Pegando o modelo da minha tabela através da classse DefaultTableModel
        DefaultTableModel model = (DefaultTableModel) tabelaservicos.getModel();

        int rows = model.getRowCount();

        for (int i = rows - 1; i >= 0; i--) { //Apagando todas as linhas da tabela.
            model.removeRow(i);
        }

    }

    public void atualizarDadosTabela() {
        limparTabela();
        importarDados();
    }

    public void pesquisarDados() {
        TableRowSorter < TableModel > rowSorter = new TableRowSorter < >(tabelaservicos.getModel());
        tabelaservicos.setRowSorter(rowSorter);

        txtfieldpesquisar.getDocument().addDocumentListener(new DocumentListener() {
            
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    String text = txtfieldpesquisar.getText();

                    if (text.trim().length() == 0) {
                        rowSorter.setRowFilter(null);
                    } else {
                        rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                    }
                } catch(PatternSyntaxException pse) {
                    pse.getMessage();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    String text = txtfieldpesquisar.getText();

                    if (text.trim().length() == 0) {
                        rowSorter.setRowFilter(null);
                    } else {
                        rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                    }
                } catch(PatternSyntaxException pse) {
                    pse.getMessage();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });
    }
    
    public void pegarLinhaSelecionada(){
        // get the selected row index
        int linhaSelecionada = tabelaservicos.getSelectedRow();
        linhaSelecionada = tabelaservicos.convertRowIndexToModel(linhaSelecionada);

        //int linhaSelecionada = tabelaservicos.getSelectedRow();
        // get the model from the jtable
        DefaultTableModel model = (DefaultTableModel) tabelaservicos.getModel();

        // set the selected row data into jtextfields
        txtfieldnome.setText(model.getValueAt(linhaSelecionada, 1).toString());
        txtfieldcontato.setText(model.getValueAt(linhaSelecionada, 2).toString());
        txtfieldendereco.setText(model.getValueAt(linhaSelecionada, 3).toString());
        txtfieldservico.setText(model.getValueAt(linhaSelecionada, 4).toString());
        txtfieldvalor.setText(model.getValueAt(linhaSelecionada, 5).toString());
        txtfielddesconto.setText(model.getValueAt(linhaSelecionada, 6).toString());
        txtfielddia.setText(model.getValueAt(linhaSelecionada, 7).toString());
        txtfieldhora.setText(model.getValueAt(linhaSelecionada, 8).toString());
        txtfieldid.setText(model.getValueAt(linhaSelecionada, 0).toString());
        txtfieldfinal.setText(model.getValueAt(linhaSelecionada, 9).toString());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaservicos = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        btnexcluir = new javax.swing.JButton();
        btnatt = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btnattTabela = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        txtfieldnome = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtfieldendereco = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtfieldservico = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtfieldvalor = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtfielddesconto = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtfieldid = new javax.swing.JTextField();
        txtfieldhora = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtfieldfinal = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtfieldcontato = new javax.swing.JTextField();
        txtfielddia = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtfieldpesquisar = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema Salão");

        jPanel2.setBackground(java.awt.Color.darkGray);
        jPanel2.setForeground(java.awt.Color.darkGray);

        tabelaservicos.setBackground(java.awt.Color.darkGray);
        tabelaservicos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tabelaservicos.setForeground(new java.awt.Color(255, 255, 255));
        tabelaservicos.setModel(new javax.swing.table.DefaultTableModel(
        new Object[][] {

},
        new String[] {
            "ID",
            "Nome do Cliente",
            "Contato",
            "Endereço",
            "Serviço",
            "Valor/Preço",
            "Desconto",
            "Dia",
            "Hora",
            "Valor Final"
        }) {
            boolean[] canEdit = new boolean[] {
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tabelaservicos.getTableHeader().setResizingAllowed(false);
        tabelaservicos.getTableHeader().setReorderingAllowed(false);
        tabelaservicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaservicosMouseClicked(evt);
            }
        });
        tabelaservicos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabelaservicosKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaservicos);

        jButton1.setBackground(java.awt.Color.darkGray);
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Agendar Serviço");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnexcluir.setBackground(java.awt.Color.darkGray);
        btnexcluir.setForeground(new java.awt.Color(255, 255, 255));
        btnexcluir.setText("Excluir Serviço");
        btnexcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnexcluirActionPerformed(evt);
            }
        });

        btnatt.setBackground(java.awt.Color.darkGray);
        btnatt.setForeground(new java.awt.Color(255, 255, 255));
        btnatt.setText("Atualizar Serviço");
        btnatt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnattActionPerformed(evt);
            }
        });

        jButton4.setBackground(java.awt.Color.darkGray);
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Sair do Sistema");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btnattTabela.setBackground(java.awt.Color.darkGray);
        btnattTabela.setForeground(new java.awt.Color(255, 255, 255));
        btnattTabela.setText("Atualizar Tabela");
        btnattTabela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnattTabelaActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nome");

        txtfieldnome.setEditable(false);
        txtfieldnome.setBackground(java.awt.Color.darkGray);
        txtfieldnome.setForeground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Contato");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Endereço");

        txtfieldendereco.setEditable(false);
        txtfieldendereco.setBackground(java.awt.Color.darkGray);
        txtfieldendereco.setForeground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Serviço");

        txtfieldservico.setEditable(false);
        txtfieldservico.setBackground(java.awt.Color.darkGray);
        txtfieldservico.setForeground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Valor/Preço");

        txtfieldvalor.setEditable(false);
        txtfieldvalor.setBackground(java.awt.Color.darkGray);
        txtfieldvalor.setForeground(new java.awt.Color(255, 255, 255));
        txtfieldvalor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfieldvalorActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Desconto");

        txtfielddesconto.setEditable(false);
        txtfielddesconto.setBackground(java.awt.Color.darkGray);
        txtfielddesconto.setForeground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Dia");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Hora");

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("ID");

        txtfieldid.setEditable(false);
        txtfieldid.setBackground(java.awt.Color.darkGray);
        txtfieldid.setForeground(new java.awt.Color(255, 255, 255));

        txtfieldhora.setEditable(false);
        txtfieldhora.setBackground(java.awt.Color.darkGray);
        txtfieldhora.setForeground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Valor Final");

        txtfieldfinal.setEditable(false);
        txtfieldfinal.setBackground(java.awt.Color.darkGray);
        txtfieldfinal.setForeground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Dialog", 3, 10)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 0, 0));
        jLabel11.setText("Sistema Desenvolvido por: Caio Vinicius");

        txtfieldcontato.setEditable(false);
        txtfieldcontato.setBackground(java.awt.Color.darkGray);
        txtfieldcontato.setForeground(new java.awt.Color(255, 255, 255));

        txtfielddia.setEditable(false);
        txtfielddia.setBackground(java.awt.Color.darkGray);
        txtfielddia.setForeground(new java.awt.Color(255, 255, 255));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/StudioIlma2.png"))); // NOI18N
        jLabel14.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Pesquisar");
        jLabel14.setToolTipText("");

        txtfieldpesquisar.setBackground(java.awt.Color.darkGray);
        txtfieldpesquisar.setForeground(new java.awt.Color(255, 255, 255));
        txtfieldpesquisar.setToolTipText("Pesquisar por Nome");
        txtfieldpesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfieldpesquisarActionPerformed(evt);
            }
        });
        txtfieldpesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfieldpesquisarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtfieldpesquisarKeyTyped(evt);
            }
        });

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/pesquisaricon.png"))); // NOI18N
        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane1).addComponent(jSeparator2).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addGroup(jPanel2Layout.createSequentialGroup().addComponent(jLabel4).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(txtfieldendereco, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel3).addComponent(jLabel2)).addGap(21, 21, 21).addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(txtfieldnome, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(txtfieldcontato, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)))).addGap(16, 16, 16).addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(jLabel5).addComponent(jLabel6).addGroup(jPanel2Layout.createSequentialGroup().addComponent(jLabel7).addGap(8, 8, 8))).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addComponent(txtfieldservico, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE).addComponent(txtfieldvalor).addComponent(txtfielddesconto)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel9).addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING).addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))).addGroup(jPanel2Layout.createSequentialGroup().addComponent(jLabel11).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jLabel12))).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(txtfieldid).addComponent(txtfieldhora).addComponent(txtfieldfinal).addComponent(txtfielddia))))).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGap(222, 222, 222).addComponent(jLabel13)).addGroup(jPanel2Layout.createSequentialGroup().addGap(252, 252, 252).addComponent(jLabel1)).addGroup(jPanel2Layout.createSequentialGroup().addGap(35, 35, 35).addComponent(jButton1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(btnatt).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(btnexcluir).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(btnattTabela).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jButton4).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(txtfieldpesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE).addGroup(jPanel2Layout.createSequentialGroup().addComponent(jLabel14).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel15))))).addGap(0, 37, Short.MAX_VALUE))).addContainerGap()));
        jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGap(14, 14, 14).addComponent(jLabel1).addGap(18, 18, 18).addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addGroup(jPanel2Layout.createSequentialGroup().addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel14)).addComponent(jLabel15)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButton1).addComponent(btnexcluir).addComponent(btnatt).addComponent(btnattTabela).addComponent(jButton4).addComponent(txtfieldpesquisar)).addGap(18, 19, Short.MAX_VALUE).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(txtfieldnome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel5).addComponent(txtfieldservico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel8).addComponent(txtfielddia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel2)).addGap(18, 18, 18).addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel3).addComponent(txtfieldcontato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel6).addComponent(txtfieldvalor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel9).addComponent(txtfieldhora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))).addGap(18, 18, 18).addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(txtfieldendereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel7).addComponent(txtfielddesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel10).addComponent(txtfieldid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel4)).addGap(18, 18, 18).addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(txtfieldfinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel12)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel11).addContainerGap()));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));
        layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));

        pack();
    } // </editor-fold>//GEN-END:initComponents
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        TelaCadastro tC = new TelaCadastro(this, rootPaneCheckingEnabled);

        tC.setVisible(true);

        tC.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {

                atualizarDadosTabela();

                limparCampos();

                ordenarTabela();

                exportarDados();

                atualizarDadosTabela();

            }
        });
    }//GEN-LAST:event_jButton1ActionPerformed
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        try {
            Object[] op = {
                "Sim",
                "Não"
            };
            Object selected = op[0];
            selected = JOptionPane.showInputDialog(null, "Deseja sair do Sistema?", "Sair", JOptionPane.QUESTION_MESSAGE, null, op, op[0]);
            switch (selected.toString()) {

            case "Sim":
                System.exit(0);
                break;

            case "Não":
                JOptionPane.showMessageDialog(null, "Ok", "Sair", JOptionPane.INFORMATION_MESSAGE);
                break;

            default:
                JOptionPane.showMessageDialog(null, "Opção Inválida", "Sair", JOptionPane.ERROR_MESSAGE);
                break;

            }
        } catch(NullPointerException npe) {
            JOptionPane.showMessageDialog(null, "Operação Cancelada!", "Cancel", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton4ActionPerformed
    private void btnexcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnexcluirActionPerformed
        // TODO add your handling code here:
        deletarJTable();
        limparCampos();
    }//GEN-LAST:event_btnexcluirActionPerformed
    private void tabelaservicosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaservicosMouseClicked
        // TODO add your handling code here:
        pegarLinhaSelecionada();
    }//GEN-LAST:event_tabelaservicosMouseClicked
    private void btnattActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnattActionPerformed
        // TODO add your handling code here:
        if (tabelaservicos.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "A tabela está vazia!", "Erro!", JOptionPane.ERROR_MESSAGE);
        } else {
            TelaAtualizar tA = new TelaAtualizar(this, rootPaneCheckingEnabled);

            tA.setVisible(true);

            tA.addWindowListener(new WindowAdapter() {

                public void windowClosed(WindowEvent e) {

                    limparTabela();
                    importarDados();
                    ordenarTabela(); //OPC
                    limparCampos();
                    exportarDados(); //OPC  
                }

            });
        }
    }//GEN-LAST:event_btnattActionPerformed
    private void txtfieldvalorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfieldvalorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfieldvalorActionPerformed
    private void btnattTabelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnattTabelaActionPerformed
        // TODO add your handling code here:
        if (tabelaservicos.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "A tabela está vazia!", "Erro!", JOptionPane.ERROR_MESSAGE);
        } else {
            atualizarDadosTabela();
            limparCampos();
        }
    }//GEN-LAST:event_btnattTabelaActionPerformed
    private void tabelaservicosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaservicosKeyReleased
        // get the selected row index
        pegarLinhaSelecionada();
    }//GEN-LAST:event_tabelaservicosKeyReleased
    private void txtfieldpesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfieldpesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfieldpesquisarActionPerformed
    private void txtfieldpesquisarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfieldpesquisarKeyPressed
        // TODO add your handling code here:                                  
    }//GEN-LAST:event_txtfieldpesquisarKeyPressed
    private void txtfieldpesquisarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfieldpesquisarKeyTyped
        // TODO add your handling code here: 
        pesquisarDados();
    }//GEN-LAST:event_txtfieldpesquisarKeyTyped
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info: javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch(ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch(InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch(IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch(javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnatt;
    private javax.swing.JButton btnattTabela;
    private javax.swing.JButton btnexcluir;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable tabelaservicos;
    private javax.swing.JTextField txtfieldcontato;
    private javax.swing.JTextField txtfielddesconto;
    private javax.swing.JTextField txtfielddia;
    private javax.swing.JTextField txtfieldendereco;
    private javax.swing.JTextField txtfieldfinal;
    private javax.swing.JTextField txtfieldhora;
    private javax.swing.JTextField txtfieldid;
    private javax.swing.JTextField txtfieldnome;
    private javax.swing.JTextField txtfieldpesquisar;
    private javax.swing.JTextField txtfieldservico;
    private javax.swing.JTextField txtfieldvalor;
    // End of variables declaration//GEN-END:variables
}