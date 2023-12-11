import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.*;
public class HippodromeTest {
    @Test
    public void nullException(){
    Throwable a = assertThrows(IllegalArgumentException.class, ()-> new Hippodrome(null));
    assertEquals("Horses cannot be null.",a.getMessage());
    }
    @Test
    public void empyListException(){
    Throwable t = assertThrows(IllegalArgumentException.class,()->new Hippodrome(Collections.emptyList()));
    assertEquals(t.getMessage(),"Horses cannot be empty.");
    }
    @Test
    public void getHorsesTest(){
        List<Horse> horses = new ArrayList<>();
        for(int i =0;i<30;i++) {
            horses.add(new Horse("Max", i, i));
        }
        Hippodrome hipp = new Hippodrome(horses);
        List<Horse> horses2 = hipp.getHorses();
        assertEquals(horses,horses2);
    }
    @Test
    public void moveTest(){
      try(MockedStatic<Horse>mockedStatic = mockStatic(Horse.class)){
                List<Horse> horses = new ArrayList<>();
                for(int i=0;i<50;i++){
                    Horse mockHorse = Mockito.mock(Horse.class);
                    horses.add(mockHorse);
                }
                Hippodrome hipp = new Hippodrome(horses);
                hipp.move();
                for(Horse h:horses){
                    verify(h).move();
                }
      }
    }
    @Test
    public void getWinnerTest(){
        List<Horse> horses = new ArrayList<>();
        for(int i=0;i<10;i++){
            horses.add(new Horse("Stas",i,i));
        }
        Hippodrome hipp = new Hippodrome(horses);
        // 1st option
        assertEquals(9,hipp.getWinner().getDistance());
        // 2nd option
        assertSame(horses.get(9),hipp.getWinner());
    }
}
