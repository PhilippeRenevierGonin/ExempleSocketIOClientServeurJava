package client;

public class Coup {
    private int coup ;
    private boolean plusGrand;

    public int getCoup() {
        return coup;
    }

    public void setCoup(int coup) {
        this.coup = coup;
    }

    public boolean isPlusGrand() {
        return plusGrand;
    }

    public void setPlusGrand(boolean plusGrand) {
        this.plusGrand = plusGrand;
    }

    public Coup() {}
    public Coup(int val, boolean sup) {
        setCoup(val);
        setPlusGrand(sup);
    }

    @Override
    public String toString() {
        return ""+getCoup()+"/"+(isPlusGrand()?">":"<");
    }
}
