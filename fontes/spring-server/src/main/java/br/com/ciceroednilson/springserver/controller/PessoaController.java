package br.com.ciceroednilson.springserver.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.ciceroednilson.springserver.http.HttpResultado;
import br.com.ciceroednilson.springserver.http.Pessoa;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

	/*LISTA ESTÁTICA PARA MANIPULAÇÃO DOS REGISTROS*/
	private static List<Pessoa> pessoas = new ArrayList<Pessoa>();
	
	public PessoaController() {
		
	}
	
	/***
	 * SALVANDO UM REGISTRO NA LISTA ESTÁTICA
	 * @param pessoa
	 * @return
	 */
	@PostMapping(value= "/salvar", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<HttpResultado> salvar(@RequestBody Pessoa pessoa){
		
		pessoas.add(pessoa);
		
		HttpResultado httpResultado = new HttpResultado(1, "Registro salvo com sucesso!");
		
		return new ResponseEntity<HttpResultado>(httpResultado, HttpStatus.OK);
	}

	/***
	 * CONSULTA TODOS OS REGISTROS NA LISTA
	 * @return
	 */
	@GetMapping(value="/consultar", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<Pessoa>> consultar(){
		
		return new ResponseEntity<List<Pessoa>>(pessoas, HttpStatus.OK);
	} 
	
	/***
	 * EDITANDO UM REGISTRO EXISTENTE NA LISTA
	 * 
	 * @param pessoa
	 * @return
	 */
	@PutMapping(value="/editar", produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<HttpResultado> editar(@RequestBody Pessoa pessoa){
		
		//removendo o registro antigo
		boolean isRemovido = pessoas.removeIf(pes -> pes.getCodigo() == pessoa.getCodigo());
		
		//se foi removido adiciona o novo
		if(isRemovido){
			pessoas.add(pessoa);
			return new ResponseEntity<HttpResultado>(new HttpResultado(1, "Registro alterado com sucesso!"), HttpStatus.OK);
		}
		
		return new ResponseEntity<HttpResultado>(new HttpResultado(1, "Registro não encontrado!"), HttpStatus.OK);
		
	}
	
	
	/***
	 * 
	 * EXCLUI UM REGISTRO DA LISTA PELO ID	 
	 * @param codigo
	 * @return
	 */
	@DeleteMapping(value="/remover/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<HttpResultado> remover(@PathVariable("id") Integer codigo){
				
		boolean isRemovido = pessoas.removeIf(pes -> pes.getCodigo() == codigo);
		
		if(isRemovido)
			return new ResponseEntity<HttpResultado>(new HttpResultado(1, "Registro excluido com sucesso!"), HttpStatus.OK);
		
		return new ResponseEntity<HttpResultado>(new HttpResultado(2, "Registro não encontrado!"), HttpStatus.OK);
	}
	
	

}
