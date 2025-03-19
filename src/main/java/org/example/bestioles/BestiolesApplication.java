package org.example.bestioles;

import org.example.bestioles.model.Animal;
import org.example.bestioles.model.Espece;
import org.example.bestioles.model.Personne;
import org.example.bestioles.repository.AnimalRepository;
import org.example.bestioles.repository.EspeceRepository;
import org.example.bestioles.repository.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class BestiolesApplication implements CommandLineRunner {

    @Autowired
    private PersonneRepository personneRepository;
    @Autowired
    private EspeceRepository especeRepository;
    @Autowired
    private AnimalRepository animalRepository;

    public static void main(String[] args) {
        SpringApplication.run(BestiolesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("DÉBUT TP03");

        System.out.println("Liste des personnes en base :");
        personneRepository.findAll().forEach(System.out::println);

        Personne newPerson = new Personne();
        newPerson.setPrenom("Alice");
        newPerson.setNom("desmerveilles");
        newPerson.setLogin("Ado");
        newPerson.setMdp("epsiepsi");
        newPerson.setAge(30);
        newPerson.setActive(true);

        personneRepository.save(newPerson);
        System.out.println("Nouvelle personne ajoutée : " + newPerson);

        personneRepository.findAll().forEach(System.out::println);

        personneRepository.deleteById(newPerson.getId());
        System.out.println("Personne supprimée (ID " + newPerson.getId() + ")");

        System.out.println("Liste des personnes après suppression :");
        personneRepository.findAll().forEach(System.out::println);

        System.out.println("FIN TP03");
        System.out.println("DÉBUT TP04");

        Optional<Espece> espece1 = especeRepository.findFirstByNomcommun("Chat");
        espece1.ifPresent(E -> System.out.println("Espèce trouvée : " + E.getNomcommun()));

        List<Espece> espece2 = especeRepository.findByNomlatinIsContainingIgnoreCase("Canis");
        System.out.println("Espèces trouvées : " + espece2);

        List<Personne> personnes1 = personneRepository.findByNomOrPrenom("Nero", "Bill");
        System.out.println("Personnes ou nom demandé: " + personnes1);

        List<Personne> personnes2 = personneRepository.findByAgeGreaterThanEqual(33);
        System.out.println("Personnes de 33 ans ou plus : " + personnes2);

        //fonctionne pas
        //Espece espece = especeRepository.findById(1).orElseThrow();
        //List<Animal> animal1 = animalRepository.findAnimalByEspece(1);
        //System.out.println("Animaux par espèce : " + animal1);

        List<String> couleurs = List.of("Noir", "Blanc", "Brun");
        List<Animal> animal2 = animalRepository.findByCouleurIn(couleurs);
        System.out.println("Animaux par list couleur : " + animal2);

        System.out.println("FIN TP04");
        System.out.println("DÉBUT TP05");

        List<Espece> especeTriees = especeRepository.findAllOrderedByNomCommun();
        System.out.println("Espèces triées query: " + especeTriees);

        List<Espece> especeExacte = especeRepository.findByNomCommunExact("Lapin");
        System.out.println("Espèce trouvée avec le nom query : " + especeExacte);

        List<Personne> personnesAge = personneRepository.findByAgeBetween(20, 30);
        System.out.println("Personnes entre 20 et 30 ans query: " + personnesAge);

        Animal animal3 = animalRepository.findById(1).orElseThrow();
        List<Personne> personnesAvecAnimal = personneRepository.findByAnimal(animal3);
        System.out.println("Personnes possédant l'animal query: " + personnesAvecAnimal);

        long  nbMales = animalRepository.countBySex("M");
        System.out.println("Nombre d'animaux mâles query : " + nbMales);

        Animal animal4 = animalRepository.findById(1).orElseThrow();
        boolean appartient = animalRepository.existsByPerson(animal4);
        System.out.println("L'animal appartient-il à une personne query ? " + appartient);

        System.out.println("Fin TP05");
        System.out.println("DÉBUT TP06");

        long beforeCount1 = personneRepository.count();
        System.out.println("Nombre de personnes AVANT suppression custom: " + beforeCount1);

        personneRepository.deletePersonsWithoutAnimals();

        long afterCount1 = personneRepository.count();
        System.out.println("Nombre de personnes APRÈS suppression custom: " + afterCount1);

        long beforeCount2 = personneRepository.count();
        System.out.println("Nombre de personnes AVANT génération custom: " + beforeCount2);

        personneRepository.generateRandomPersons(10);

        long afterCount2 = personneRepository.count();
        System.out.println("Nombre de personnes APRÈS génération custom: " + afterCount2);
    }

}
