/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcionalidade;

/**
 *
 * @author Jean
 */
public class Ensalamento {
    public String nome;
    public String sobrenome;
    public String salaUm;
    public String salaDois;
    public String cafeUm;
    public String cafeDois;
    
    public Ensalamento(String nome,String sobrenome,String salaUm, String salaDois, String cafeUm, String cafeDois){
        this.nome= nome;
        this.sobrenome = sobrenome;
        this.salaUm = salaUm;
        this.salaDois = salaDois;
        this.cafeUm = cafeUm;
        this.cafeDois= cafeDois;
    }
    public Ensalamento(String idPessoa, int salaUmId, int salaDoisId, int cafeUmId, int cafeDoisId){
       
    }
}
