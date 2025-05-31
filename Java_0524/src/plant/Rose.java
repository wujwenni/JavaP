package plant;

public class Rose extends Plant {

	public Rose(int growth) {
        super("장미", growth);
    }

    @Override
    public String talk(String word) {
        this.word = word;
        return this.word;
    }

}
