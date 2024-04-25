package zoo.entities.areas;

import zoo.entities.animals.Animal;
import zoo.entities.foods.Food;

import java.util.ArrayList;
import java.util.Collection;

import static zoo.common.ExceptionMessages.AREA_NAME_NULL_OR_EMPTY;
import static zoo.common.ExceptionMessages.NOT_ENOUGH_CAPACITY;

public abstract class BaseArea implements Area {

    private String name;
    private int capacity;
    private Collection<Food> foods;
    private Collection<Animal> animals;

    public BaseArea(String name, int capacity) {
        setName(name);
        this.capacity = capacity;
        foods = new ArrayList<>();
        animals = new ArrayList<>();
    }


    private void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(AREA_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public Collection<Animal> getAnimals() {
        return animals;
    }

    @Override
    public Collection<Food> getFoods() {
        return foods;
    }

    @Override
    public int sumCalories() {
        return foods.stream().mapToInt(Food::getCalories).sum();
    }

    public void addAnimal(Animal animal) {
        if (animals.size() >= capacity) {
            throw new IllegalStateException(NOT_ENOUGH_CAPACITY);
        }
        animals.add(animal);
    }

    @Override
    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    @Override
    public void addFood(Food food) {
        foods.add(food);
    }

    @Override
    public void feed() {
        animals.forEach(Animal::eat);
    }

    public String getInfo() {
        StringBuilder info = new StringBuilder();
        info.append(name).append(" (").append(getClass().getSimpleName()).append("):").append(System.lineSeparator());
        info.append("Animals: ");

        if (animals.isEmpty()) {
            info.append("none");
        } else {
            animals.stream()
                    .map(Animal::getName)
                    .forEach(animalName -> info.append(animalName).append(" "));
        }

        info.append(System.lineSeparator())
                .append("Foods: ").append(foods.size()).append(System.lineSeparator())
                .append("Calories: ").append(sumCalories());

        return info.toString();
    }

}
