package vttp.batch5.paf.movies.bootstrap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.bson.Document;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonStructure;
import vttp.batch5.paf.movies.models.Movie;


public class Dataloader implements CommandLineRunner{

  @Autowired
  private MongoTemplate mtemplate;

  @Autowired
  private JdbcTemplate stemplate;
  
  public static final String source = "C:\\Users\\darre\\Coding\\VTTP\\PAF\\paf_assessment\\paf_b5_assessment_template\\data\\movies_post_2010.zip";
  public static final String destination = "C:\\Users\\darre\\Coding\\VTTP\\PAF\\paf_assessment\\paf_b5_assessment_template\\data";
  public static final String SQL_CHECK = "select title from movie_data where title = 'The Cabin in the Woods'";
  public static final String SQL_CREATE_MOVIE_RECORD = """
            insert into imdb(imdb_id, vote_average, vote_count, release_date, revenue, budget, runtime)  values (?, ?, ?, ?, ?, ?, ?)
        """;

  
  //TODO: Task 2
  /*
   * select title from imdb where title = 'The Cabin in the Woods';
   * 
   * db.imdb.aggregate(
      {$match:{'title':'The Cabin in the Woods'}},
      {$project: {title:1, _id:0}})
   */
  @Override
  public void run(String... args) throws FileNotFoundException, IOException, ParseException{
    
    String check1 = stemplate.queryForObject(SQL_CHECK, String.class);

    MatchOperation MO = Aggregation.match(Criteria.where("title").is("The Cabin in the Woods"));
    ProjectionOperation PO = Aggregation.project("title").andExclude("_id");
    Aggregation pipeline = Aggregation.newAggregation(MO, PO);
    AggregationResults<Document> ar = mtemplate.aggregate(pipeline, "imdb", Document.class);
    Document result = ar.getMappedResults().get(0);

    if (check1 == null || result.isEmpty()) { // low lvl check if sample record exists
      
      ZipFile zf = new ZipFile(source);
      ZipEntry ze = zf.getEntry("movies_post_2010");
      InputStream inputs = zf.getInputStream(ze);
      BufferedReader br = new BufferedReader(new InputStreamReader(inputs, "UTF-8"));
      BufferedWriter wr = new BufferedWriter(new FileWriter(destination));
      
      JsonReader jsonReader = Json.createReader(br);
      JsonObject jo = null;

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  
      List<Object[]> toUpdate = new ArrayList<>();
      List<Document> docsToInsert = new ArrayList<>();
      
      while ((jo = jsonReader.readObject()) != null){
        int i = 1;
        if (sdf.parse(jo.getString("release_date")).after(sdf.parse("2017-12-31")) && i < 26){
          Object[] oa = new Object[7];
          oa[0] = jo.getString("imdb_id");
          oa[1] = jo.getInt("vote_average");
          oa[2] = jo.getInt("vote_count");
          oa[3] = sdf.parse(jo.getString("release_date"));
          oa[4] = Double.valueOf(jo.getInt("revenue"));
          oa[5] = Double.valueOf(jo.getInt("budget"));
          oa[6] = jo.getInt("runtime");
          toUpdate.add(oa);
          
          Document doc = new Document("_id", jo.getString("imdb_id"));
            // .append("title", jo.getString("title"))
            // .append("directors", jo.getString("directors"))
            // .append("overview", jo.getString("overview"))
            // .append("tagline", jo.getString("tagline"))
            // .append("genres", jo.getString("genres"))
            // .append("imdb_rating", jo.getInt("imdb_rating"))
            // .append("imdb_votes", jo.getInt("imdb_votes"));
          docsToInsert.add(doc);
          i++;
        } 
        stemplate.batchUpdate(SQL_CREATE_MOVIE_RECORD, toUpdate);
        Collection<Document> newDocs = mtemplate.insert(docsToInsert, "imdb");
      }
      br.close();

    }

    
   
  }


}
