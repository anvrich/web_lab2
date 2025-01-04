package servlets.lab2;
import java.io.Serializable;


public class Model implements Serializable {
    private double x, y, r;
    private boolean hit;
    private String time;
    private double executionTime;

    public Model(double x, double y, double r, boolean hit, String time, double executionTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
        this.time = time;
        this.executionTime = executionTime;
    }

    public double getX() {
        return x;
    }
    public double getExecutionTime() {
        return executionTime;
    }

    public double getY() {
        return y;
    }
    public double getR() {
        return r;
    }
    public boolean isHit() {
        return hit;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Model{" +
                "x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", hit=" + hit +
                ", time='" + time + '\'' +
                ", executionTime=" + executionTime +
                '}';
    }
}
