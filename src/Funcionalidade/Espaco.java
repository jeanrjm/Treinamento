/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcionalidade;

import java.util.ArrayList;

/**
 *
 * @author Jean
 */
public abstract class Espaco {
    int id;
    int lotacaoMax; 
    int lotacaoEtapaUm;
    int lotacaoEtapaDois;
    
    public ArrayList<Integer> idPessoasEtapaUm = new ArrayList<Integer>();
    public ArrayList<Integer> idPessoasEtapaDois = new ArrayList<Integer>();
    
    public Espaco(int id, int lotacao, int lotacaoum, int lotacaodois){
        this.id = id;
        this.lotacaoMax = lotacao;
        this.lotacaoEtapaUm = lotacaoum;
        this.lotacaoEtapaDois = lotacaodois;
    }
    
    public Espaco (int id){
        
    }

    
    
}
