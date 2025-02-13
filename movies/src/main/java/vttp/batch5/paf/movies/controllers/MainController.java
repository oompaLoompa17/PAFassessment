package vttp.batch5.paf.movies.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.json.JsonArray;
import vttp.batch5.paf.movies.services.MovieService;

@Controller
@RequestMapping
public class MainController {

  @Autowired 
  private MovieService movieSvc;


  // TODO: Task 3
  @GetMapping("")
  public String getProlificDirectors(@PathVariable int n){
    JsonArray directors = movieSvc.getProlificDirectors(n);
    return "";
  } 

  
  // TODO: Task 4


}
