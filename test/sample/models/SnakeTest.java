package snake.models;

import org.junit.Test;
import org.junit.Assert;

/**
 * Created by niklas.fassbender on 05.01.2017.
 */
public class SnakeTest {

    @Test
    public void testExtendEmptySnake() {
        //given
        Snake snake = new Snake();

        //when
        snake.extend();

        //then
        Assert.assertEquals("Leere extendete Schlange soll Länge 1 haben",1, snake.getPositions().size());

    }



}
