package enums;

public enum GrowthStage {
    SEED(0, 24),
    SPROUT(25, 49),
    LEAF(50, 74),
    BUD(75, 99),
    FLOWER(100, 100);
    
    private final int minGrowth;
    private final int maxGrowth;
    
    GrowthStage(int minGrowth, int maxGrowth) {
        this.minGrowth = minGrowth;
        this.maxGrowth = maxGrowth;
    }
    
    public boolean isWithinRange(int growth) {
        return growth >= minGrowth && growth <= maxGrowth;
    }
    
    public static GrowthStage getStageByGrowth(int growth) {
        for (GrowthStage stage : GrowthStage.values()) {
            if (stage.isWithinRange(growth)) {
                return stage;
            }
        }
        return null;
    }
}
