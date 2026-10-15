package tests;

import controller.AnimalController;
import model.Animal;
import model.Bird;
import model.Cow;
import model.Pig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.AnimalRepository;
import repository.ArrayAnimalRepository;
import repository.RepositoryException;

import static org.junit.jupiter.api.Assertions.*;

class GeneralTests {
    private AnimalController controller;

    @BeforeEach
    void setUp() {
        AnimalRepository repository = new ArrayAnimalRepository(3);
        controller = new AnimalController(repository);
    }

    @Test
    void returnsOnlyAnimalsHeavierThan3Kg() throws RepositoryException {
        controller.addAnimal(new Bird(1.5));
        controller.addAnimal(new Cow(450));
        controller.addAnimal(new Pig(120));

        Animal[] result = controller.getAnimalsHeavierThan(3);

        assertEquals(2, result.length);
        assertEquals(450, result[0].getWeight());
        assertEquals(120, result[1].getWeight());
    }

    @Test
    void animalOfExactly3KgIsNotIncluded() throws RepositoryException {
        controller.addAnimal(new Bird(3));
        assertEquals(0, controller.getAnimalsHeavierThan(3).length);
    }

    @Test
    void removeAnimalUpdatesRepository() throws RepositoryException {
        controller.addAnimal(new Cow(450));
        controller.addAnimal(new Pig(120));

        controller.removeAnimal(0);

        Animal[] all = controller.getAllAnimals();
        assertEquals(1, all.length);
        assertEquals(120, all[0].getWeight());
    }

    @Test
    void addingToFullRepositoryThrows() throws RepositoryException {
        controller.addAnimal(new Bird(1));
        controller.addAnimal(new Bird(2));
        controller.addAnimal(new Bird(3));

        assertThrows(RepositoryException.class, () -> controller.addAnimal(new Bird(4)));
    }

    @Test
    void removingInvalidPositionThrows() {
        assertThrows(RepositoryException.class, () -> controller.removeAnimal(5));
    }

    @Test
    void addingNullThrows() {
        assertThrows(RepositoryException.class, () -> controller.addAnimal(null));
    }
}