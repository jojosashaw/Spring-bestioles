package org.example.bestioles.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.bestioles.model.Personne;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Random;

@Repository
public class PersonneRepositoryCustomImpl implements PersonneRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    private static final List<String> PRENOMS = List.of("Alex", "Marie", "Jean", "Sophie", "Luc", "Emma", "Thomas", "Léa");
    private static final List<String> NOMS = List.of("Durand", "Martin", "Bernard", "Dubois", "Morel", "Lefevre", "Simon", "Laurent");

    private static final Random RANDOM = new Random();

    @Override
    @Transactional
    public void deletePersonsWithoutAnimals() {
        em.createQuery("DELETE FROM Personne p WHERE p.animaux IS EMPTY")
                .executeUpdate();
    }

    @Override
    @Transactional
    public void generateRandomPersons(int count) {
        for (int i = 0; i < count; i++) {
            Personne person = new Personne();
            String firstname = PRENOMS.get(RANDOM.nextInt(PRENOMS.size()));
            String lastname = NOMS.get(RANDOM.nextInt(NOMS.size()));

            person.setPrenom(firstname);
            person.setNom(lastname);
            person.setAge(18 + RANDOM.nextInt(50));

            String login = firstname.toLowerCase() + "." + lastname.toLowerCase() + RANDOM.nextInt(1000);
            person.setLogin(login);

            person.setMdp("password" + RANDOM.nextInt(1000));
            person.setActive(Boolean.TRUE);

            em.persist(person);
        }
        em.flush();
    }

}
