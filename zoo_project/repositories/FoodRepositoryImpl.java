package zoo.repositories;

import zoo.entities.foods.Food;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FoodRepositoryImpl implements FoodRepository{

    private Collection<Food> foods;
    private Map<String, Integer> foodStorage;


    public FoodRepositoryImpl() {
        foods = new ArrayList<>();
        this.foodStorage = new HashMap<>();
    }

    public void add(Food food) {
        foods.add(food);
    }

    public boolean remove(Food food) {
        return foods.remove(food);
    }

    public Food findByType(String type) {
        if (type == null) {
            return null;
        }

        for (Food food : foods) {
            if (type.equalsIgnoreCase(food.getClass().getSimpleName())) {
                return food;
            }
        }

        return null;
    }

    @Override
    public void addFood(String foodType) {

    }

    @Override
    public boolean hasFoodType(String foodType) {
        return foodStorage.containsKey(foodType);
    }

    @Override
    public void removeFood(String foodType) {
        if (!foodStorage.containsKey(foodType)) {
            throw new IllegalArgumentException("There isn't a food of type " + foodType + ".");
        }
        int currentAmount = foodStorage.get(foodType);
        if (currentAmount > 1) {
            foodStorage.put(foodType, currentAmount - 1);
        } else {
            foodStorage.remove(foodType);
        }
    }
}
