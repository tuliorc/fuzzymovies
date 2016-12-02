package fuzzymovies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataSelect {
	 
	Connection c = null;
	
	public DataSelect(Connection c){
		this.c = c;
		
	}
	
        public int discoverGenre(int MovieID){
                
            int Genre = 0;	
            
            try {
				
                PreparedStatement discoverGenre = null;  	
                c.setAutoCommit(false); 
				
                String sql =    "SELECT Genre from Movies WHERE MovieID = ?";
				
                discoverGenre = c.prepareStatement(sql);
                discoverGenre.setInt(1,MovieID);
			    
			     
                ResultSet rs = discoverGenre.executeQuery();
                Genre = rs.getInt(1);
                
                rs.close();
                discoverGenre.close();      
                    
		} 
		
            catch (Exception e ) {
		    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
                
            return Genre;
                
        }
        
	public double selectUserGenreAverage(int CustomerID, int Genre){
                
            double avg = 0;	
            
            try {
				
                PreparedStatement selectUserGenreAverage = null;  	
                c.setAutoCommit(false); 
				
                String sql =    "SELECT sum(Ratings.rate)*1.0/count(*) from Movies,Ratings WHERE Movies.Genre = ? AND Ratings.CustomerID = ?";
				
                selectUserGenreAverage = c.prepareStatement(sql);
                selectUserGenreAverage.setInt(1,CustomerID);
                selectUserGenreAverage.setInt(2,Genre);
			    
			     
                ResultSet rs = selectUserGenreAverage.executeQuery();
                    
                avg = rs.getDouble(1);
                
                rs.close();
                selectUserGenreAverage.close();      
                    
		} 
		
            catch (Exception e ) {
		    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
                
            return avg;
                
        }
	
        public double selectGenreAverage(int Genre){
                
            double avg = 0;	
            
            try {
				
                PreparedStatement selectGenreAverage = null;  	
                c.setAutoCommit(false); 
				
                String sql =    "SELECT sum(Ratings.Rate)*1.0/count(*) from Movies,Ratings WHERE Movies.Genre = ?";
				
                selectGenreAverage = c.prepareStatement(sql);
                selectGenreAverage.setInt(1,Genre);
			    
			     
                ResultSet rs = selectGenreAverage.executeQuery();
                    
                avg = rs.getDouble(1);
                
                rs.close();
                selectGenreAverage.close();      
                    
		} 
		
            catch (Exception e ) {
		    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
                
            return avg;
                
        }
        
        public double selectMovieAverage(int MovieID){
                
            double avg = 0;	
            
            try {
				
                PreparedStatement selectMovieAverage = null;  	
                c.setAutoCommit(false); 
				
                String sql =    "SELECT sum(Ratings.Rate)*1.0/count(*) from Movies,Ratings WHERE Ratings.MovieID = ?";
				
                selectMovieAverage = c.prepareStatement(sql);
                selectMovieAverage.setInt(1,MovieID);
			    
			     
                ResultSet rs = selectMovieAverage.executeQuery();
                    
                avg = rs.getDouble(1);
                    
                rs.close();
                selectMovieAverage.close();      
                    
		} 
		
            catch (Exception e ) {
		    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
                
            return avg;
                
        }
        
        public double selectDateAverage(int MovieID){
                
            double avg = 0;	
            
            try {
				
                PreparedStatement selectDateAverage = null;  	
                c.setAutoCommit(false); 
				
                String sql =    "SELECT sum(Ratings.Rate)*1.0/count(*) from Movies,Ratings WHERE Ratings.MovieID = ? AND Ratings.Date = \"2005-07-25\"";
				
                selectDateAverage = c.prepareStatement(sql);
                selectDateAverage.setInt(1,MovieID);
			    
			     
                ResultSet rs = selectDateAverage.executeQuery();
                
                if(!rs.isBeforeFirst() ){
                    avg = 3;
                }
                else{
                    avg = rs.getDouble(1);
                }
                   
                rs.close();
                selectDateAverage.close();      
                    
		} 
		
            catch (Exception e ) {
		    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
                
            return avg;
                
        }
	
}
