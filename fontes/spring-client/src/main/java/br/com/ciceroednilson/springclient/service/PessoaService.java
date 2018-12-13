package br.com.ciceroednilson.springclient.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.ciceroednilson.springserver.http.HttpResultado;
import br.com.ciceroednilson.springserver.http.Pessoa;

public class PessoaService {

	/*URL BASE DO NOSSO SERVIÇO REST*/
	private final String urlBase ="http://localhost:8090/pessoa/";
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public HttpResultado salvar(Pessoa pessoa){
		
		//SALVA UM NOVO REGISTRO
		return this.restTemplate.postForObject(this.urlBase.concat("salvar"), pessoa, HttpResultado.class);
	}
	
	/***
	 * REALIZA A CONSULTA DOS REGISTROS NO SERVIÇO REST
	 * @return
	 * @throws RestClientException
	 * @throws URISyntaxException
	 */
	public List<Pessoa> consultar() throws RestClientException, URISyntaxException{
		
		//CONSULTA AS PESSOAS
		ResponseEntity<List<Pessoa>> pessoas = this.restTemplate.exchange(new URI(this.urlBase.concat("consultar")), HttpMethod.GET,null,new ParameterizedTypeReference<List<Pessoa>>(){});
		
		return pessoas.getBody();
	}
	
	/***
	 * EDITA UM REGISTRO 
	 * @param pes
	 * @return
	 * @throws RestClientException
	 * @throws URISyntaxException
	 */
	public HttpResultado editar(Pessoa pes) throws RestClientException, URISyntaxException{
		
		HttpEntity<Pessoa> pessoa = new HttpEntity<Pessoa>(pes);
		
		//CHAMA O MÉTODO DE EDITAR
		 ResponseEntity<HttpResultado>  resultado = this.restTemplate.exchange(new URI(this.urlBase.concat("editar")), HttpMethod.PUT, pessoa , HttpResultado.class);
		 
		 return resultado.getBody();
	}

	/***
	 * EXCLUIR UM REGISTRO
	 * @param codigo
	 * @return
	 * @throws RestClientException
	 * @throws URISyntaxException
	 */
	public HttpResultado deletar(int codigo) throws RestClientException, URISyntaxException{
				
  		 // MONTA A URL FINAL
		 URI uri = new URI(this.urlBase.concat("remover").concat("/").concat(String.valueOf(codigo)));
		 
		 //CHAMA O MÉTODO DE DELETE
		 ResponseEntity<HttpResultado>  resultado = this.restTemplate.exchange(uri, HttpMethod.DELETE, null , HttpResultado.class);
		 
		 return resultado.getBody();
	}
	
}
