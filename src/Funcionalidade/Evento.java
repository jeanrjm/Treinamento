/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcionalidade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jean
 */
public class Evento {

    private Connection conexao;
    private PreparedStatement stm;
    private String sql;

    public void cadastrarNovaSala(String nome, String lotacao) throws Exception {
        conexao = new Conexao().criarConexao();
        try {
            sql = "INSERT INTO salas (nome, lotacao) VALUES (? , ?);";
            stm = conexao.prepareStatement(sql);
            stm.setString(1, nome);
            stm.setInt(2, Integer.parseInt(lotacao));
            stm.execute();
            conexao.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            conexao.close();
            throw new Exception("Erro: Houve um erro ao cadastrar " + nome);
        }

    }

    public void cadastrarNovoEspacoCafe(String nome, String lotacao) throws Exception {
        conexao = new Conexao().criarConexao();

        try {
            sql = "INSERT INTO cafe (nome, lotacao) VALUES (? , ?);";
            stm = conexao.prepareStatement(sql);
            stm.setString(1, nome);
            stm.setInt(2, Integer.parseInt(lotacao));
            stm.execute();
            conexao.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            conexao.close();
            throw new Exception("Erro: Houve um erro ao cadastrar " + nome);
        }

    }

    public void cadastrarNovaPessoa(String nome, String sobrenome) throws Exception {
        String nomecompleto = nome+" "+sobrenome;
        conexao = new Conexao().criarConexao();
        try {
            
            sql = "INSERT INTO pessoas (nome, sobrenome, nomecompleto) VALUES (? , ?, ?);";
            stm = conexao.prepareStatement(sql);
            stm.setString(1, nome);
            stm.setString(2, sobrenome);
            stm.setString(3, nomecompleto);
            stm.execute();
            conexao.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            conexao.close();
            throw new Exception("Erro: Houve um erro ao cadastrar " + nome + " " + sobrenome);
        }
        
        try {
            new Organizador().alocarNovaPessoa(nomecompleto);
        }catch(Exception e){
            System.out.println("Nao alocou pessoa");
        }

    }
    
    public Espaco[] getSalas() throws Exception {
        conexao = new Conexao().criarConexao();
        sql = "SELECT count(*) FROM salas;";
        stm = conexao.prepareStatement(sql);
        ResultSet res = stm.executeQuery();

        res.next();
        int nSalas = res.getInt(1);

        
        Espaco[] salas = new Espaco[nSalas];
        int index = 0;

        if (nSalas > 0) {
            sql = "SELECT id, lotacao FROM salas;";
            stm = conexao.prepareStatement(sql);
            res = stm.executeQuery();

            while (res.next()) {
                int id = res.getInt(1);
                int lotacaoMax = res.getInt(2);

                sql = "SELECT count(*) FROM ensalamento WHERE salaUm = ?;";
                stm = conexao.prepareStatement(sql);
                stm.setInt(1, id);
                ResultSet resp = stm.executeQuery();
                resp.next();
                int lotEtapaUm = resp.getInt(1);

                sql = "SELECT count(*) FROM ensalamento WHERE salaDois = ?;";
                stm = conexao.prepareStatement(sql);
                stm.setInt(1, id);
                resp = stm.executeQuery();
                resp.next();
                int lotEtapaDois = resp.getInt(1);

                //Salas.add(new Sala(id, lotacaoMax, lotEtapaUm, lotEtapaDois));
                salas[index] = new Sala(id, lotacaoMax, lotEtapaUm, lotEtapaDois);
                
                index++;
            }

        } else {
            throw new Exception("Não existem salas cadastradas");

        }
        return salas;

    }
    
    public ArrayList<Pessoa> getPessoas() throws Exception{
        conexao = new Conexao().criarConexao();
        sql = "SELECT * FROM pessoas;";
        stm = conexao.prepareStatement(sql);
        ResultSet res = stm.executeQuery();
        
        res.last();
        int numPessoas = res.getRow();
        res.first();
        
        ArrayList<Pessoa> pessoas = new ArrayList<Pessoa>();
        
        if (numPessoas>0){
            while (!res.isAfterLast()){
                int id = res.getInt(1);
                String nome = res.getString(2);
                String sobre = res.getString(3);
                String completo = res.getString(4);
                
               
                pessoas.add(new Pessoa(id, nome, sobre, completo));
                res.next();
            } 
        }
        return pessoas;
    }

    public Espaco[] getCafes() throws Exception {
        conexao = new Conexao().criarConexao();

        sql = "SELECT count(*) FROM cafe;";
        stm = conexao.prepareStatement(sql);
        ResultSet res = stm.executeQuery();

        res.next();
        int numCafes = res.getInt(1);
     

        Espaco[] cafes = new Espaco[numCafes];
        int index = 0;

        if (numCafes > 0) {
            sql = "SELECT id, lotacao FROM cafe;";
            stm = conexao.prepareStatement(sql);
            res = stm.executeQuery();

            while (res.next()) {
                int id = res.getInt(1);
                int lotacaoMax = res.getInt(2);

                sql = "SELECT count(*) FROM ensalamento WHERE cafeUm = ?;";
                stm = conexao.prepareStatement(sql);
                stm.setInt(1, id);
                ResultSet resp = stm.executeQuery();
                resp.next();
                int lotEtapaUm = resp.getInt(1);

                sql = "SELECT count(*) FROM ensalamento WHERE cafeDois = ?;";
                stm = conexao.prepareStatement(sql);
                stm.setInt(1, id);
                resp = stm.executeQuery();
                resp.next();
                int lotEtapaDois = resp.getInt(1);

                //System.out.println(id+" "+lotacaoMax+" "+lotEtapaUm+" "+lotEtapaDois);      
                //cafes.add(new EspacoCafe(id, lotacaoMax, lotEtapaUm, lotEtapaDois));
                cafes[index] = new EspacoCafe(id, lotacaoMax, lotEtapaUm, lotEtapaDois);
                index++;
            }

        }else {
            throw new Exception("Não existem cafes cadastrados");

        }
        return cafes;

    }

    

    
}
