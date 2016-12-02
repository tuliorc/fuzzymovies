
package fuzzymovies;

import java.sql.Connection;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import net.sourceforge.jFuzzyLogic.rule.Rule;
import net.sourceforge.jFuzzyLogic.rule.RuleBlock;
import net.sourceforge.jFuzzyLogic.rule.RuleExpression;
import net.sourceforge.jFuzzyLogic.rule.RuleTerm;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class FuzzyMoviesRatings
{
   public static void main(String[] args) throws Exception 
   {
       
      // Load from 'FCL' file
      String fileName = "fcl/fuzzymovies.fcl";
      FIS fis = FIS.load(fileName, true);
      
      if (fis == null) 
      { // Error while loading
         System.err.println("Can't load file: '" + fileName + "'");
         return;
      }
      
      FunctionBlock functionBlock = fis.getFunctionBlock(null);
      ConnectionFactory connectionFactory = new ConnectionFactory();
      Connection con = null;
                 con = connectionFactory.getConnection("data/netflix.db");
      
      DataSelect data = new DataSelect(con);
      Scanner sc = new Scanner(System.in);
 
      
      
    /* ===============================================
    * METODO DE DEFUZZIFICACAO
    * ===============================================
    */  
    
    /*
     Choice a Movie and a Customer from the List of Avaible Options
     The "today" date is 2005-07-25
     For Example Movie 1 -  and Customer 1959936
    */
      
      System.out.println("Choice a User by ID");
      int CustomerID = sc.nextInt();
    
      System.out.println("Choice a Movie by ID");
      int MovieID = sc.nextInt();
      int MovieGenre = data.discoverGenre(MovieID);
     /* 
       Através desses dois dados, vamos buscar as variaveis de entrada no banco
     */
      
      double mediaGenero  = data.selectGenreAverage(MovieGenre);
      double mediaUsuario = data.selectUserGenreAverage(CustomerID, MovieGenre);
      double mediaFilme   = data.selectMovieAverage(MovieID);
      double mediaData    = data.selectDateAverage(MovieID);
      
      functionBlock.setVariable("mediaGenero", mediaGenero);
      functionBlock.setVariable("mediaUsuario", mediaUsuario);
      functionBlock.setVariable("mediaFilme", mediaFilme);
      functionBlock.setVariable("mediaData", mediaData);
      
      // Evaluate 
      fis.evaluate();   
          
 /* ===============================================
 * METODO DE DEFUZZIFICACAO
 * ===============================================
 */      
      System.out.println("--------------------------");
      System.out.println("FASE DE DEFUZZIFICACAO");
      System.out.println("--------------------------");
      // Print outputs defuzzifier
      System.out.println("NOTA ESTIMADA: " + fis.getVariable("notaEstimada").getValue());
      con.close();
      
 /* ===============================================
 * FIM DO METODO DE DEFUZZIFICACAO
 * ===============================================
 */   
   }
}