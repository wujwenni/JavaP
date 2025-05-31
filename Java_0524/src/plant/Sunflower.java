package plant;

public class Sunflower extends Plant {

	public Sunflower(int growth) {
        super("해바라기", growth);
    }

    @Override
    public String talk(String word) {
        this.word = word;
        return this.word;
    }

}
