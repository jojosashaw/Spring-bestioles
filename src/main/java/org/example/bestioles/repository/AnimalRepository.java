package org.example.bestioles.repository;


import org.example.bestioles.model.Animal;
import org.example.bestioles.model.Espece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer> {

    //fonctionne pas
    //List<Animal> findAnimalByEspece(int espece_id);

    List<Animal> findByCouleurIn(List<String> couleurs);

    @Query("SELECT COUNT(a) FROM Animal a WHERE a.sexe = :sex")
    long countBySex(@Param("sex") String sex);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM Personne p WHERE :animal MEMBER OF p.animaux")
    boolean existsByPerson(@Param("animal") Animal animal);


}
