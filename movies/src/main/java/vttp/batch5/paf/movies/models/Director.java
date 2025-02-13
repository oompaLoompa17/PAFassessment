package vttp.batch5.paf.movies.models;

public class Director {
    private String director_name;
    private int movies_count;
    private Double total_revenue;
    private Double total_budget;

    public Director(){}

    public Director(String director_name, int movies_count, Double total_revenue, Double total_budget) {
        this.director_name = director_name;
        this.movies_count = movies_count;
        this.total_revenue = total_revenue;
        this.total_budget = total_budget;
    }

    public String getDirector_name() {
        return director_name;
    }
    public void setDirector_name(String director_name) {
        this.director_name = director_name;
    }
    public int getMovies_count() {
        return movies_count;
    }
    public void setMovies_count(int movies_count) {
        this.movies_count = movies_count;
    }
    public Double getTotal_revenue() {
        return total_revenue;
    }
    public void setTotal_revenue(Double total_revenue) {
        this.total_revenue = total_revenue;
    }
    public Double getTotal_budget() {
        return total_budget;
    }
    public void setTotal_budget(Double total_budget) {
        this.total_budget = total_budget;
    }
    
    
}
