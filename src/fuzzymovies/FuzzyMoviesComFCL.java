/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzymovies;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

/**
 *
 * @author tuliocarreira
 */
public class FuzzyMoviesComFCL {
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
      
    // Set inputs
      FunctionBlock functionBlock = fis.getFunctionBlock(null);
      functionBlock.setVariable("mediaGenero", 2);
      functionBlock.setVariable("mediaUsuario", 1);
      functionBlock.setVariable("mediaFilme", 1);
      functionBlock.setVariable("mediaData", 1);
      JFuzzyChart.get().chart(functionBlock);
 
      // Evaluate 
      fis.evaluate();
      
      // Show output variables chart
      Variable notaEstimada = functionBlock.getVariable("notaEstimada");
      JFuzzyChart.get().chart(notaEstimada, notaEstimada.getDefuzzifier(), true);
      System.out.println("Nota Estimada:" + fis.getVariable("notaEstimada").getValue());
      
      //Print ruleSet
      System.out.println("\n====================================\n");
      System.out.println(fis);
   }
}
