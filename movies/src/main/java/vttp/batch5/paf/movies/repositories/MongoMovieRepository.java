package vttp.batch5.paf.movies.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.AddFieldsOperation;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;


public class MongoMovieRepository {

    @Autowired
    private MongoTemplate template;

    // TODO: Task 2.3
    // You can add any number of parameters and return any type from the method
    // You can throw any checked exceptions from the method
    // Write the native Mongo query you implement in the method in the comments
    //
    //    native MongoDB query here
    //
    public void batchInsertMovies() {

    }

    // TODO: Task 2.4
    // You can add any number of parameters and return any type from the method
    // You can throw any checked exceptions from the method
    // Write the native Mongo query you implement in the method in the comments
    //
    //    native MongoDB query here
    //
    public void logError() {

    }

    // TODO: Task 3
    // Write the native Mongo query you implement in the method in the comments
    //    
    //    native MongoDB query here
    //    
    /*     db.raw.aggregate(
            {$group: {_id: '$director', movies_count:{$sum:1}, total_revenue:{$sum:'$revenue'}, total_budget:{$sum:'$budget'}}}, 
            {$sort: {'movies_directed':-1}},
            {$limit: n}
            )

    *     
    */
    public List<Document> getNDirectors(int n){
        GroupOperation GO = Aggregation.group("director");
            // .and(.count("director").as("movies_directed"))
            // .and()
            // .and();
        
        SortOperation SO = Aggregation.sort(Sort.Direction.DESC, "movies_directed");
        LimitOperation LO = Aggregation.limit(n);

        Aggregation pipeline = Aggregation.newAggregation(GO,SO,LO);

        return template.aggregate(pipeline, "raw", Document.class).getMappedResults();
    }
}
