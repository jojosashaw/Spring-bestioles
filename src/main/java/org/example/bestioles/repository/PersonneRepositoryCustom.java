package org.example.bestioles.repository;

public interface PersonneRepositoryCustom {

    void deletePersonsWithoutAnimals();

    void generateRandomPersons(int count);
}
