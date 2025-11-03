package com.moviemania.MovieMania.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id; // <-- A IMPORTAÇÃO CORRETA!
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor; // Adicionado para JPA
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 
@Entity
public class AdminModel {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ID;
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getNome_completo() {
		return nome_completo;
	}
	public void setNome_completo(String nome_completo) {
		this.nome_completo = nome_completo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	private String nome_completo;
	private String email;
	private String senha;
	private String cargo;
	
}