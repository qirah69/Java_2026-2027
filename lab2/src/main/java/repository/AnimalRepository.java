package repository;

import model.Animal;

public interface AnimalRepository {
    void add(Animal animal) throws RepositoryException;
    void remove(int position) throws RepositoryException;
    Animal[] getAll();
    int size();
}
