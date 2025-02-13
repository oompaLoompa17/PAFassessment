package vttp.batch5.paf.movies.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp.batch5.paf.movies.models.Director;
import vttp.batch5.paf.movies.repositories.MongoMovieRepository;
import vttp.batch5.paf.movies.repositories.MySQLMovieRepository;

@Service
public class MovieService {

  @Autowired
  private MongoMovieRepository mongoRepo;

  @Autowired
  private MySQLMovieRepository sqlRepo;


  // TODO: Task 2
  @Transactional
  public void doSomething(){
    
  }

  // TODO: Task 3
  // You may change the signature of this method by passing any number of parameters
  // and returning any type
  public JsonArray getProlificDirectors(int n) {
      List<Director> directors = sqlRepo.getNDirectors(n);
      JsonArrayBuilder ja = Json.createArrayBuilder();
      for(Director d : directors){
        JsonObject jo = Json.createObjectBuilder()
          .add("director_name", d.getDirector_name())
          .add("movies_count", d.getMovies_count())
          .add("total_revenue", d.getTotal_revenue())
          .add("total_budget", d.getTotal_budget())
          .build();
        ja.add(jo);
      }
      return ja.build();
  }


  // TODO: Task 4
  // You may change the signature of this method by passing any number of parameters
  // and returning any type
  public void generatePDFReport() {

  }

}
