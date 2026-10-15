package repository;

import model.Animal;

public class ArrayAnimalRepository implements AnimalRepository {
    private final Animal[] animals;
    private int size;

    public ArrayAnimalRepository(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.animals = new Animal[capacity];
        this.size = 0;
    }

    @Override
    public void add(Animal animal) throws RepositoryException {
        if (animal == null) {
            throw new RepositoryException("Cannot add a null animal");
        }
        if (size == animals.length) {
            throw new RepositoryException("Repository is full (capacity " + animals.length + ")");
        }
        animals[size++] = animal;
    }

    @Override
    public void remove(int position) throws RepositoryException {
        if (position < 0 || position >= size) {
            throw new RepositoryException("Invalid position: " + position);
        }
        for (int i = position; i < size - 1; i++) {
            animals[i] = animals[i + 1];
        }
        animals[--size] = null;
    }

    @Override
    public Animal[] getAll() {
        Animal[] copy = new Animal[size];
        for (int i = 0; i < size; i++) {
            copy[i] = animals[i];
        }
        return copy;
    }

    @Override
    public int size() {
        return size;
    }
}
