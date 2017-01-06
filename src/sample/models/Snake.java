package sample.models;

import java.util.ArrayList;

/**
 * Created by niklas.fassbender on 02.01.2017.
 */
public class Snake {
    ArrayList<Position> positions;
    Direction dir;
    boolean invincible;
    int score;
    ArrayList<PowerUp> acticePowerUps;

    public Snake(){
        positions = new ArrayList<Position>();
        dir = Direction.DOWN;
        invincible = false;
        acticePowerUps = new ArrayList<PowerUp>();
        int score = 0;
    }

    public ArrayList<Position> getPositions() {
        return positions;
    }




    public boolean move(Direction direction){
        ArrayList<Position> tempPos = new ArrayList<Position>();
        tempPos.addAll(positions);
        boolean moveIsValid = true;
        switch (positions.get(0).direction){
            case DOWN:
                if(direction == Direction.UP)
                    moveIsValid = false;
                break;
            case UP:
                if(direction == Direction.DOWN)
                    moveIsValid = false;
                break;
            case LEFT:
                if(direction == Direction.RIGHT)
                    moveIsValid = false;
                break;
            case RIGHT:
                if(direction == Direction.LEFT)
                    moveIsValid = false;
                break;
        }

        if(!moveIsValid) {
            direction = positions.get(0).getDirection();
        }
            switch (direction) {
                case LEFT:
                    positions.set(0, new Position((tempPos.get(0).getX() - 1), tempPos.get(0).getY(), direction));
                    break;
                case RIGHT:
                    positions.set(0, new Position((tempPos.get(0).getX() + 1), tempPos.get(0).getY(), direction));
                    break;
                case DOWN:
                    positions.set(0, new Position((tempPos.get(0).getX()), tempPos.get(0).getY() + 1, direction));
                    break;
                case UP:
                    positions.set(0, new Position((tempPos.get(0).getX()), tempPos.get(0).getY() - 1, direction));
                    break;
            }

            for (int i = 1; i < positions.size(); i++) {

                positions.set(i, new Position(tempPos.get(i - 1).getX(), tempPos.get(i - 1).getY(), tempPos.get(i - 1).getDirection()));

            }


            if (positions.get(0).getX() > GameSettings.gridWidth - 1) {
                positions.get(0).setX(0);
            } else if (positions.get(0).getX() < 0) {
                positions.get(0).setX(GameSettings.gridWidth - 1);
            } else if (positions.get(0).getY() > GameSettings.gridHeight - 1) {
                positions.get(0).setY(0);
            } else if (positions.get(0).getY() < 0) {
                positions.get(0).setY(GameSettings.gridHeight - 1);
            }



        return true;
    }

    public boolean extend(){
        score++;
        for(int i = 0; i < GameSettings.growthPerFood; i++) {
            positions.add(new Position(GameSettings.gridHeight + 2, GameSettings.gridWidth + 2, Direction.DOWN));
        }



        return true;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public ArrayList<PowerUp> getActivePowerUps() {
        return acticePowerUps;
    }

    public void setActicePowerUps(ArrayList<PowerUp> acticePowerUps) {
        this.acticePowerUps = acticePowerUps;
    }

    public int getScore() {
        return score;
    }
}
