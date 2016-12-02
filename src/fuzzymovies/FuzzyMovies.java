package fuzzymovies;

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


public class FuzzyMovies
{
   public static void main(String[] args) throws Exception 
   {
      int i=0;
       
      // Load from 'FCL' file
      String fileName = "fcl/fuzzymovies.fcl";
      FIS fis = FIS.load(fileName, true);
      
      if (fis == null) 
      { // Error while loading
         System.err.println("Can't load file: '" + fileName + "'");
         return;
      }
      
      // Show variables
      FunctionBlock functionBlock = fis.getFunctionBlock(null);
      JFuzzyChart.get().chart(functionBlock);
 
      // Set inputs
      functionBlock.setVariable("mediaGenero", 2);
      functionBlock.setVariable("mediaUsuario", 1);
      functionBlock.setVariable("mediaFilme", 1);
      functionBlock.setVariable("mediaData", 1);
      
      // Evaluate 
      fis.evaluate();
       
/* ===============================================
 * FASE DE FUZZIFICACAO
 * Mostrar o valor das funcoes de pertinencia
 * das variaveis lingui�sticas de entrada
 * ===============================================
 */         
      System.out.println("--------------------------");
      System.out.println("FASE DE FUZZIFICACAO");
      System.out.println("--------------------------");
      double mediaGeneroBaixa = functionBlock.getVariable("mediaGenero").getMembership("baixa");
      double mediaGeneroMedia = functionBlock.getVariable("mediaGenero").getMembership("media");
      double mediaGeneroAlta = functionBlock.getVariable("mediaGenero").getMembership("alta");
      
      double mediaUsuarioBaixa = functionBlock.getVariable("mediaUsuario").getMembership("baixa");
      double mediaUsuarioMedia = functionBlock.getVariable("mediaUsuario").getMembership("media");
      double mediaUsuarioAlta = functionBlock.getVariable("mediaUsuario").getMembership("alta");
      
      double mediaFilmeBaixa = functionBlock.getVariable("mediaFilme").getMembership("baixa");
      double mediaFilmeMedia = functionBlock.getVariable("mediaFilme").getMembership("media");
      double mediaFilmeAlta = functionBlock.getVariable("mediaFilme").getMembership("alta");
      
      double mediaDataBaixa = functionBlock.getVariable("mediaData").getMembership("baixa");
      double mediaDataMedia = functionBlock.getVariable("mediaData").getMembership("media");
      double mediaDataAlta = functionBlock.getVariable("mediaData").getMembership("alta");

      System.out.println("mediaGenero(baixa):" + mediaGeneroBaixa);
      System.out.println("mediaGenero(media):" + mediaGeneroMedia);
      System.out.println("mediaGenero(alta):" + mediaGeneroAlta);
      
      System.out.println("mediaUsuario(baixa):" + mediaUsuarioBaixa);
      System.out.println("mediaUsuario(media):" + mediaUsuarioMedia);
      System.out.println("mediaUsuario(alta):" + mediaUsuarioAlta);
      
      System.out.println("mediaFilme(baixa):" + mediaFilmeBaixa);
      System.out.println("mediaFilme(media):" + mediaFilmeMedia);
      System.out.println("mediaFilme(alta):" + mediaFilmeAlta);
      
      System.out.println("mediaData(baixa):" + mediaDataBaixa);
      System.out.println("mediaData(media):" + mediaDataMedia);
      System.out.println("mediaData(alta):" + mediaDataAlta);
      
/* ===============================================
 * FIM DA FASE DE FUZZIFICACAO
 * ===============================================
 */         
      
 
/* ===============================================
 * METODOS DE AGREGACAO
 * Aplicar os operadores fuzzy AND (t-norma) 
 * OR (t-conorma) nos antecedentes das regras
 * Assim, encontra-se o valor dos antecedentes
 * das regras
 * ===============================================
 */
      HashMap<String, RuleBlock> todasRegras = new HashMap<String, RuleBlock>();
      todasRegras = functionBlock.getRuleBlocks();
      
      //Pegar o Bloco de Regras que esta no arquivo FLC
      RuleBlock blocoDeRegras = todasRegras.get("No1");
      //Mostrar o Bloco de Regras que foi retornado pelo comando acima
      //System.out.println("\n==================================");
      //System.out.println("BLOCO DE REGRAS DO ARQUIVO FCL");
      //System.out.println(blocoDeRegras.toString());
      
      //Pegar as regras do Bloco de Regras
      List<Rule> regras;
      regras = blocoDeRegras.getRules();
      
      System.out.println("------------------------------------");
      System.out.println("CALCULO DOS ANTECEDENTES DAS REGRAS");
      System.out.println("------------------------------------");
      //Para cada regra mostar seu antecedente e o valor deste antecedente,
      //usando os metodos de agregacao MIN e MAX
      for (Rule umaRegra : regras) 
      {
         i++;
         RuleExpression regraExpressao = umaRegra.getAntecedents();
         System.out.println("- Antecedente da Regra " + i + ": " + regraExpressao.toString());
         //O metodo getDegreeOfSupport() retorna o valor do antecedente da regra,
         //apos a aplicacao dos operadores OR, AND
         System.out.println("  Valor do Antecedente da Regra " + i + ": " + umaRegra.getDegreeOfSupport());
      }
      
      // Show each rule (and degree of support)
      //System.out.println("Regras e seus respectivos graus de ativação");
      //for( Rule r : fis.getFunctionBlock("gorjeta").getFuzzyRuleBlock("No1").getRules() )
      //   System.out.println(r);
    
/* ===============================================
 * FIM DA APLICACAO DOS METODOS DE AGREGACAO
 * ===============================================
 */
      
 /* ===============================================
 * METODOS DE ATIVACAO
 * Definir como os antecedentes de uma regra 
 * modificam os consequentes.
 * Como o metodo de inferecia de Mamdani esta 
 * sendo utilizado neste programa, entao o metodo
 * de ativacao sera MIN
 * ===============================================
 */      
      System.out.println("------------------------------------");
      System.out.println("RESULTADO METODO DE ATIVACAO");
      System.out.println("------------------------------------");
  
      i=0;
      for (Rule umaRegra : regras) 
      {
         i++;
         RuleTerm termo = umaRegra.getConsequents().element();
         System.out.println("- Consequente da Regra " + i + ": " + termo.toString());
      }

 /* ===============================================
 * FIM DOS METODOS DE ATIVACAO
 * ==============================================
 */      
      
 /* ===============================================
 * METODO DE ACUMULACAO
 * Os conjuntos fuzzy que representam as saidas
 * das regras sao combinados em um unico conjunto
 * fuzzy
 * ===============================================
 */              
      System.out.println("--------------------------");
      System.out.println("FASE DE ACUMULACAO");
      System.out.println("--------------------------");
      System.out.println("Veja grafico com os consequentes combinados pelo metodo MAX");      
      // Show the accumulation method�s result: combining of
      //the consequentes
      Variable notaEstimada = functionBlock.getVariable("notaEstimada");
      JFuzzyChart.get().chart(notaEstimada, notaEstimada.getDefuzzifier(), true);
 /* ===============================================
 * FIM DO METODO DE ACUMULACAO
 * ===============================================
 */              
      
 /* ===============================================
 * METODO DE DEFUZZIFICACAO
 * ===============================================
 */      
      System.out.println("--------------------------");
      System.out.println("FASE DE DEFUZZIFICACAO");
      System.out.println("--------------------------");
      // Print outputs defuzzifier
      System.out.println("NOTA ESTIMADA: " + fis.getVariable("notaEstimada").getValue());
      
 /* ===============================================
 * FIM DO METODO DE DEFUZZIFICACAO
 * ===============================================
 */   
   }
}