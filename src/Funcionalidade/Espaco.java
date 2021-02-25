/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcionalidade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Jean
 */
public abstract class Espaco {

    private Connection conexao;
    private PreparedStatement stm;
    private String sql;
    private String categoria;

    private int id;
    private String nome;
    private int lotacaoMax;
    private int lotacaoEtapaUm;
    private int lotacaoEtapaDois;

    //public ArrayList<Integer> idPessoasEtapaUm = new ArrayList<Integer>();
    //public ArrayList<Integer> idPessoasEtapaDois = new ArrayList<Integer>();
    public Espaco(int id, String nome, int lotacao, String categoria) {
        this.id = id;
        this.nome = nome;
        this.lotacaoMax = lotacao;
        this.categoria = categoria;
    }

    public Espaco(int id, String categoria) {
        this.id = id;
        this.categoria = categoria;
    }

    public String getNome() {
        return this.nome;
    }

    public Pessoa[] getNomePessoasEtapaUm(int etapa) throws Exception {
        String etapaTexto;
        if(etapa == 1){
            etapaTexto="Um";
        }else if(etapa == 2){
            etapaTexto="Dois";
        }else{
            throw new Exception("Etapa inexistente");
        }
        
        conexao = new Conexao().criarConexao();
        sql = "SELECT p.id, p.nomecompleto FROM ensalamento INNER JOIN pessoas p ON ensalamento.pessoa = p.id WHERE " + categoria + etapaTexto + " = ?;";
        stm = conexao.prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet res = stm.executeQuery();
        //SELECT p.nome FROM ensalamento INNER JOIN pessoas p ON ensalamento.pessoa = p.id WHERE salaUm = 1;
        res.last();

        int qtd = res.getRow();
         res.beforeFirst();

        if (qtd > 0) {
            
            Pessoa[] pessoas = new Pessoa[qtd];
            
            for(int a=0; a<qtd; a++){
                res.next();
                int pessoaId = res.getInt(1);
                String pessoaNome = res.getString(2);
                pessoas[a]= new Pessoa(pessoaId, pessoaNome);
            }
            return pessoas; 
        } else {
            throw new Exception("Sem pessoas");
        }
    }

    public int getId() {
        return this.id;
    }

    int getLotacaoEtapaUm() throws Exception {
        try {
            conexao = new Conexao().criarConexao();
            sql = "SELECT count(*) FROM ensalamento WHERE " + categoria + "Um = ?;";
            stm = conexao.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet resp = stm.executeQuery();
            resp.next();
            return resp.getInt(1);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    int getLotacaoMaxima() {
        return this.lotacaoMax;
    }

    int getLotacaoEtapaDois() throws Exception {
        try {
            conexao = new Conexao().criarConexao();
            sql = "SELECT count(*) FROM ensalamento WHERE " + categoria + "Dois = ?;";
            stm = conexao.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet resp = stm.executeQuery();
            resp.next();
            return resp.getInt(1);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

   
}
