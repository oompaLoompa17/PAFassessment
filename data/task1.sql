create database movies;
use movies;

create table imdb(
	imdb_id varchar(16) not null,
    vote_average float not null default 0.0,
    vote_count int not null default 0,
    release_date date not null,
    revenue decimal(15,2) not null default 1000000,
    budget decimal(15,2) not null default 1000000,
    runtime int not null default 90
);