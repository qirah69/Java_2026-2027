package controller;

import model.Animal;
import repository.AnimalRepository;
import repository.RepositoryException;

public class AnimalController {
    private final AnimalRepository repository;

    public AnimalController(AnimalRepository repository) {
        this.repository = repository;
    }

    public void addAnimal(Animal animal) throws RepositoryException {
        repository.add(animal);
    }

    public void removeAnimal(int position) throws RepositoryException {
        repository.remove(position);
    }

    public Animal[] getAllAnimals() {
        return repository.getAll();
    }

    /** Returns all animals whose weight is strictly greater than the given limit. */
    public Animal[] getAnimalsHeavierThan(double limit) {
        Animal[] all = repository.getAll();
        int count = 0;
        for (Animal a : all) {
            if (a.getWeight() > limit) {
                count++;
            }
        }
        Animal[] result = new Animal[count];
        int k = 0;
        for (Animal a : all) {
            if (a.getWeight() > limit) {
                result[k++] = a;
            }
        }
        return result;
    }
}
