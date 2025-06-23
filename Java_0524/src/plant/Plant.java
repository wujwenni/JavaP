package plant;


public class Plant {
    protected final String name;
    protected int growth;

    public Plant(String name, int growth) {
        this.name = name;
        this.growth = growth;
    }

    public String getName() {
        return name;
    }

    public int getGrowth() {
        return growth;
    }

    public void increaseGrowth(int amount) {
        if (amount > 0) {
            this.growth = Math.min(100, this.growth + amount);
        }
    }
}
