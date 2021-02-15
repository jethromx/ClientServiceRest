package com.pokemon;

import java.text.MessageFormat;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

public class JavaClientService {
	
private static final String REST_URI = "https://pokeapi.co/api/v2/pokemon?limit={0}&offset={1}";
	
	public static void main(String args[]) {
		
		List<Pokemon> pokemons = JavaClientService.getPokemonsList(100, 0);		
		for(Pokemon p: pokemons) {
			System.out.println(p.getName() + " -- "+  p.getUrl());
		}				
	}
	
	
	public static List<Pokemon> getPokemonsList(double limit,double offset) {
		ResponsePokemon response = executeClient(limit,offset,ResponsePokemon.class);		
		if (response != null && response.getResults() != null && response.getResults().size() > 0) {
			return response.getResults();
		} else
			return null;
	}
	
	private static String formatURL(double limit, double offset){
		return  MessageFormat.format(REST_URI,limit,offset );		
	}

	private static  <T> T executeClient(double limit,double offset,Class<T> type) {
		Client client = ClientBuilder.newClient();				
		return (T) client.target(formatURL(limit,offset)).request(MediaType.APPLICATION_JSON)
				.get(type);
		
	}

}
