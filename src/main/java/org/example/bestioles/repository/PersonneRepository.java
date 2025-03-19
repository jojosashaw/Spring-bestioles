package org.example.bestioles.repository;

import org.example.bestioles.model.Animal;
import org.example.bestioles.model.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonneRepository extends JpaRepository<Personne, Integer>, PersonneRepositoryCustom {

    List<Personne> findByNomOrPrenom(String nom, String prenom);

    List<Personne> findByAgeGreaterThanEqual(int age);

    @Query("SELECT p FROM Personne p WHERE p.age BETWEEN :ageMin AND :ageMax")
    List<Personne> findByAgeBetween(@Param("ageMin") int ageMin, @Param("ageMax") int ageMax);

    @Query("SELECT p FROM Personne p JOIN p.animaux a WHERE a = :animal")
    List<Personne> findByAnimal(@Param("animal") Animal animal);
}
