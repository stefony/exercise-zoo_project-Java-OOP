package zoo.repositories;

import zoo.entities.foods.Food;

public interface FoodRepository {
    void add(Food food);

    boolean remove(Food food);

    Food findByType(String type);

    void addFood(String foodType);

    boolean hasFoodType(String foodType);

    void removeFood(String foodType);

}
