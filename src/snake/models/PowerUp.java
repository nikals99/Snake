package snake.models;

/**
 * Created by niklas.fassbender on 04.01.2017.
 */
public class PowerUp {
    ObjectType type;
    long endTime;

    public PowerUp(ObjectType type, long endTime) {
        this.type = type;
        this.endTime = endTime;
    }

    public ObjectType getType() {
        return type;
    }

    public void setType(ObjectType type) {
        this.type = type;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
