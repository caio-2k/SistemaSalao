package br.edu.ifrn.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class Cliente {
    
    //Atributos da Classe
    private String nome;
    private String contato;
    private String endereco;
    private String servico;
    private String dia;
    private String hora;
    private String id;
    private float preco;
    private float desconto;
    private float valFinal;
    
    //ArrayList de Clientes
    List <Cliente> clientes = new ArrayList<>();
    
    //Getteres e Setteres da Classe
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public float getDesconto() {
        return desconto;
    }

    public void setDesconto(float desconto) {
        this.desconto = desconto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getValFinal() {
        return valFinal;
    }

    public void setValFinal(float valFinal) {
        this.valFinal = valFinal;
    }
    
    //Método Cadastrar Cliente/Serviço
    //A função recebe um objeto cliente como parâmetro
    public void cadastrarServico(Cliente serv){
        
        //Chamando a função "add" da classe List passando serv como parâmetro
        clientes.add(serv);
        
        //Chamando o método escrever e passando o arraylist clientes como parâmetro
        Escrever(clientes);
        
    }
    
    
    // ---------------------- MÉTODO ESTÁTICO DE ESCRITA --------------------
	private static void Escrever(List clientes) {
            
                //Cria um arquivo (file arq) no diretório indicado (file dir).
		File dir = new File("C:\\Users\\Eau de Vie\\Desktop\\Projeto - Sistema Salão 1.1\\SistemaSalao\\dist");

		File arq = new File(dir, "logServicos.txt");

		try {
                        //Passando no construtor do FileWriter qual arquivo irá ser manipulado.
                        //O parâmetro true indica que reescrevemos no arquivo sem sobrescrever.
                        //O false apagaria o conteúdo do arquivo e escreveria o novo conteúdo.
                        //Por padrão ele é false (caso não tenha segundo parâmetro).
			FileWriter fileWriter = new FileWriter(arq, true);
                        
			//A classe PrintWriter serve para escrever fisicamente no arquivo.
                        //É necessário passar o objeto fileWriter no seu construtor.
			PrintWriter printWriter = new PrintWriter(fileWriter);
    
			for (int i = 0; i < clientes.size(); i++) {
				Cliente u = (Cliente) clientes.get(i);
				printWriter.print(u.getId() + ",");
                                printWriter.print(u.getNome() + ",");
				printWriter.print(u.getContato() + ",");
				printWriter.print(u.getEndereco() + ",");
                                printWriter.print(u.getServico() + ",");
                                printWriter.print(u.getPreco() + ",");
                                printWriter.print(u.getDesconto() + ",");
                                printWriter.print(u.getDia() + ",");
                                printWriter.print(u.getHora() + ","); 
                                printWriter.print(u.getValFinal());
				printWriter.println("");
			}
			
                        //O método flush libera a escrita no arquivo.
			printWriter.flush();
			
                        //O método close fecha o arquivo (obrigatório);
			printWriter.close();
			
		} catch (IOException e) {
			e.getMessage(); //Informando o erro após abrir a excessão
		}
		
	}
    
    
}
