create database emploidutemps_db;
use emploidutemps_db;

create table enseignants(
id varchar primary key not null,
nom varchar(20) not null,
contact varchar(30) not null,
)

    insert into  enseignants(nom,contact) values (Ens5,"amal","90174975");

create table tb_cours(
                         id int auto_increment primary key,
                         classe varchar(30) not null,
                         matiere varchar(80) not null,
                         num_jour smallint,
                         Jour varchar(20) not null,
                         heure varchar(20) not null,
                         matricule_ens varchar(20) not null)
    engine=innodb;

