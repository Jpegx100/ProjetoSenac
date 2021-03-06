/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProjetoSenac;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Patricio JP
 */
public class Controle {
    Connection conexao;
    private int ultimo;
    public Controle(){
        FabricaDeConexao fabrica = new FabricaDeConexao("bd", "root", "root");
        conexao = fabrica.obterConexao();
    }
    public int inserirDados(String nome, String endereco, int matricula, String data){
        ConsultaSQL consult = new ConsultaSQL();
      if(consult.verificaMatricula(matricula) == 0){
        //Falta fazer a DATA DE NASCIMENTO
        String sql = "INSERT INTO bd.Alunos (nome, endereco, matricula, data) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt;
        try {
            pstmt = conexao.prepareStatement(sql);
            pstmt.setString(1, nome);
            pstmt.setString(2, endereco);
            pstmt.setInt(3, matricula);
            pstmt.setString(4, data);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Dados salvos com sucesso!");
        } catch (SQLException ex) {
            Logger.getLogger(Controle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 1;
      }else{
          JOptionPane.showMessageDialog(null, "Matricula já cadastrada! \nPor favor escolha outra...", "Matricula inválida", JOptionPane.PLAIN_MESSAGE);
          return 0;
      }
    }
    public void buscarDados(){
        ConsultaSQL consult = new ConsultaSQL();
        consult.buscarDados();
    }
    public void buscarPorNome(String nome){
        ConsultaSQL consult = new ConsultaSQL();
        consult.buscarNome(nome);
    }
    public void buscarPorMatricula(int matricula){
        ConsultaSQL consult = new ConsultaSQL();
        consult.buscaMatricula(matricula);
    }
    /**
     *
     * @param matricula
     */
    public void excluirDados(int matricula){
        ConsultaSQL consult = new ConsultaSQL();
        if(consult.verificaMatricula(matricula)==1){
        String sql = "DELETE FROM bd.Alunos WHERE matricula = ?";
        PreparedStatement pstmt;
        try {
            pstmt = conexao.prepareStatement(sql);
            pstmt.setInt(1, matricula);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Aluno Excluido do registro!", "Excluido", JOptionPane.PLAIN_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(Controle.class.getName()).log(Level.SEVERE, null, ex);
        }
      }else{
            JOptionPane.showMessageDialog(null, "Matricula nao encontrada!", "Não Excluido", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
