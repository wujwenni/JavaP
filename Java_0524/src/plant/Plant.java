package plant;


public class Plant {
    protected final String name;
    protected int growth;
    protected String word;

    public Plant(String name, int growth) {
        this.name = name;
        this.growth = growth;
        this.word = "";
    }

    public String getName() {
        return name;
    }

    public int getGrowth() {
        return growth;
    }

    public String getWord() {
        return word;
    }

    public void increaseGrowth(int amount) {
        if (amount > 0) {
            this.growth = Math.min(100, this.growth + amount);
        }
    }

    public void setWord(String word) {
        this.word = word;
    }

}
