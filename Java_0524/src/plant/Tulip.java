package plant;

public class Tulip extends Plant {

	public Tulip(int growth) {
        super("튤립", growth);
    }

    @Override
    public String talk(String word) {
        this.word = word;
        return this.word;
    }

}
