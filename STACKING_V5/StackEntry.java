import shapes.*; 

public class StackEntry {
    private String type;   
    private int cupId;     
    private Cup cup;       
    private Lid lid;       

    public StackEntry(Cup cup) {
        this.type = "cup";
        this.cup = cup;
        this.cupId = cup.getId();
        this.lid = null;
    }

    public StackEntry(Lid lid, int cupId) {
        this.type = "lid";
        this.lid = lid;
        this.cupId = cupId;
        this.cup = null;
    }

    public String getType() {
        return type;
    }

    public int getCupId() {
        return cupId;
    }

    public Cup getCup() {
        return cup;
    }

    public Lid getLid() {
        return lid;
    }

    public int heightCm() {
        if (type.equals("cup")) return cup.getBlockSize();
        return 1; 
    }
}