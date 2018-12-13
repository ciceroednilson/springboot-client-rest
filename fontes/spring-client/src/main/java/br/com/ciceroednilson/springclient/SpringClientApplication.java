package br.com.ciceroednilson.springclient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestClientException;

import br.com.ciceroednilson.springclient.service.PessoaService;
import br.com.ciceroednilson.springserver.http.HttpResultado;
import br.com.ciceroednilson.springserver.http.Pessoa;

@SpringBootApplication
public class SpringClientApplication {

	
	public static void main(String[] args) throws IOException, RestClientException, URISyntaxException {
			
		PessoaService pessoaService =  new PessoaService();
		
		boolean isContinue = true;
		
		HttpResultado resultado;
		 
		
		do {			
			
			int opcao = Integer.valueOf(JOptionPane.showInputDialog(null,
					" 1-CADASTRAR | 2-CONSULTAR | 3-EDITAR | 4-EXCLUIR ",
					"OPÇÕES", JOptionPane.PLAIN_MESSAGE));
			
			
			/*CADASTRAR UM NOVO REGISTRO*/
			if(opcao == 1){
				
				String nome = JOptionPane.showInputDialog(null, "Nome:", "Novo Cadastro", JOptionPane.PLAIN_MESSAGE);
				
			 	resultado = pessoaService.salvar(new Pessoa(new Random().nextInt(), nome));
			 				 	
			 	JOptionPane.showMessageDialog(null, resultado.getMensagem());
			 	
			}
			else if(opcao == 2){ /*CONSULTAR TODOS OS REGISTROS*/
				
				StringBuilder stringBuiderDetalhesPessoa = new StringBuilder();
				
				List<Pessoa> pessoas = pessoaService.consultar();
				// MONTANDO A LSITA DE PESSOAS
				for (Pessoa pessoa : pessoas) {
		 
					stringBuiderDetalhesPessoa.append("Código: ");
					stringBuiderDetalhesPessoa.append(pessoa.getCodigo());
					stringBuiderDetalhesPessoa.append(" Nome: ");
					stringBuiderDetalhesPessoa.append(pessoa.getNome());					
					stringBuiderDetalhesPessoa.append("\n\n");
		 
				}

				JOptionPane.showMessageDialog(null, stringBuiderDetalhesPessoa.toString());
			}
			
			else if(opcao == 3){ /*EDITAR UM REGISTRO EXISTENTE*/
				
				int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Código:", "Editar Cadastro", JOptionPane.PLAIN_MESSAGE));
				String nome = JOptionPane.showInputDialog(null, "Nome:", "Editar Cadastro", JOptionPane.PLAIN_MESSAGE);
				
				resultado = pessoaService.editar(new Pessoa(codigo, nome));

			 	JOptionPane.showMessageDialog(null,resultado.getMensagem());
			}			
			else if(opcao == 4){ /*EXCLUIR UM REGISTRO*/
				
				int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Código:", "Deletar Cadastro", JOptionPane.PLAIN_MESSAGE));
								
				resultado = pessoaService.deletar(codigo);
							 	
			 	JOptionPane.showMessageDialog(null, resultado.getCodigo() + " " + resultado.getMensagem());
			}
			else{
								
				JOptionPane.showMessageDialog(null, "OPÇÃO INVÁLIDA!!!!");
			}
			
			
			String sair = JOptionPane.showInputDialog(null, "|[1] = SAIR | [2] = CONTINUAR |", "SISTEMA", JOptionPane.PLAIN_MESSAGE);
					
			
			if(sair.equals("1"))
				isContinue=false;
			
		} while (isContinue);
	}

}

