package model;

public class Cow implements Animal {
    private final double weight;

    public Cow(double weight) { this.weight = weight; }

    @Override
    public double getWeight() { return weight; }

    @Override
    public String getName() {
        return "Cow";
    }

    @Override
    public String toString() {
        return getName() + " (" + weight + " kg)";
    }
}
