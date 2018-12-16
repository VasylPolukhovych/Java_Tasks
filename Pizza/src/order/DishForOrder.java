package order;
public class DishForOrder {
    int count;
    String dishName;
    int cookedDishId;
    float sum;
    public DishForOrder() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getCookedDishId() {
        return cookedDishId;
    }

    public void setCookedDishId(int cookedDishId) {
        this.cookedDishId = cookedDishId;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }
}
