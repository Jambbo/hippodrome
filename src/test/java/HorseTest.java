import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;


public class HorseTest {
        @Test
    public void nullNameException(){
            assertThrows(IllegalArgumentException.class, () ->new Horse(null,1,1));
        }
        @Test
    public void nullNameMessage(){
            Throwable exception = assertThrows(IllegalArgumentException.class, () ->new Horse(null,1,1));
            assertEquals("Name cannot be null.",exception.getMessage());
    }
    @ParameterizedTest
    @CsvSource({"''","' '","'\t'","'\n'","'\r\n'"})
    public void symbolException(String input){
        assertThrows(IllegalArgumentException.class, () ->new Horse(input,1,1));
    }
    @ParameterizedTest
    @CsvSource({"''","' '","'\t'","'\n'","'\r\n'"})
    public void spaceNameException(String input){
            Throwable e = assertThrows(IllegalArgumentException.class, () ->new Horse(input,1,1));
        assertEquals("Name cannot be blank.",e.getMessage());
    }
    @Test
    public void negativeSecondParam(){
           Throwable e = assertThrows(IllegalArgumentException.class,() -> new Horse("Max",-1,1));
           assertEquals("Speed cannot be negative.",e.getMessage());
    }
    @Test
    public void negativeThirdParam(){
        Throwable e = assertThrows(IllegalArgumentException.class,() -> new Horse("Max",1,-1));
        assertEquals("Distance cannot be negative.",e.getMessage());
    }
    // 1st option
    @Test
    public void getNameTest(){
            String name = "Max";
           Horse horse =  new Horse (name,1,1);
            String actualName = horse.getName();
            assertEquals(name,actualName);
    }
    // 2nd option
    @Test
    public void getNameTest2() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Stas",1,1);
        Field name = Horse.class.getDeclaredField("name");
        name.setAccessible(true);
        String nameValue = (String)name.get(horse);
        assertEquals("Stas",nameValue);
    }
    @Test
    public void getSpeedTest(){
            double speed = 12;
            Horse horse = new Horse("Stas",speed,1);
            double actualSpeed = horse.getSpeed();
            assertEquals(speed,actualSpeed);
    }
    @Test
    public void getSpeedTest2() throws NoSuchFieldException, IllegalAccessException {
            Horse horse = new Horse("Bomb",1,1);
            Field speed = Horse.class.getDeclaredField("speed");
            speed.setAccessible(true);
            double speedValue =(double) speed.get(horse);
            assertEquals(1,speedValue);
    }


    @Test
    public void getDistanceTest(){
            double distance = 15;
            Horse horse = new Horse("Max",1,distance);
            double actualDistance = horse.getDistance();
            assertEquals(distance,actualDistance);
    }
    @Test
    public void getDistanceTest2() throws NoSuchFieldException, IllegalAccessException {
            Horse horse = new Horse("Max",1,1);
            Field distance = Horse.class.getDeclaredField("distance");
            distance.setAccessible(true);
            double distance2 = (double)distance.get(horse);
            assertEquals(1,distance2);
    }
    @Test
    public void distanceZero(){
            Horse horse = new Horse("Max",1);
            assertEquals(0,horse.getDistance());
    }
    @Test
    public void moveCallsGetRandomDouble(){
      try(MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)){
          double param1 = 0.2;
          double param2 = 0.9;
          Horse horse = new Horse("Max",1,1);
          horse.move();
          mockedStatic.verify(()->Horse.getRandomDouble(param1,param2));
      }
    }
    @Test
    public void calculateDistance(){
            double param1 =0.2;
            double param2 = 0.9;
            double speed = 10.0;
            double initialDistance = 5.0;
            try(MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)){
                mockedStatic.when(()->Horse.getRandomDouble(param1,param2)).thenReturn(0.5);
                Horse horse = new Horse("Max",speed,initialDistance);
                horse.move();
                assertEquals(initialDistance+speed*0.5,horse.getDistance());
            }
    }



}
