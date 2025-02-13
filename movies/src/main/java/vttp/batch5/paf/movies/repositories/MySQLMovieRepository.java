package vttp.batch5.paf.movies.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch5.paf.movies.models.Director;

@Repository
public class MySQLMovieRepository {

    @Autowired
    private JdbcTemplate template;

    // TODO: Task 2.3
    // You can add any number of parameters and return any type from the method
    public void batchInsertMovies() {
      
    }
    
    // TODO: Task 3
    /*
    * select director, sum(title) as movies_directed, sum(revenue) as total_revenue, sum(budget) as total_budget from imdb 
        group by director
        order by movies_directed
        limit 5;
    */
     public List<Director> getNDirectors(int n) {
        final String SQL_SEARCH_N_DIRECTORS = """
            select director, sum(title) as movies_directed, sum(revenue) as total_revenue, sum(budget) as total_budget from imdb 
              group by director
              order by movies_directed
              limit ?
            """;

        List<Director> directors = new ArrayList<>();
        directors = template.query(SQL_SEARCH_N_DIRECTORS, BeanPropertyRowMapper.newInstance(Director.class), n);

        // FOR SQL QUERY W PLACEHOLDERS
        // List<Customer> customers = template.query(
        //     sql.sql_getAllCustomers, new BeanPropertyRowMapper<>(Customer.class),
        //     "sup"  // Argument for the placeholder
        // );

        return directors;
    }

}
