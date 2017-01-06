package snake.models;

/**
 * Created by niklas.fassbender on 04.01.2017.
 */
public class Object {
    private Position position;
    private ObjectType type;

    public Object(Position position, ObjectType type){
        this.position = position;
        this.type = type;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public ObjectType getType() {
        return type;
    }

    public void setType(ObjectType type) {
        this.type = type;
    }
}
