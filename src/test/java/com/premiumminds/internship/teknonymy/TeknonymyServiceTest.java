package com.premiumminds.internship.teknonymy;

import static org.junit.Assert.assertEquals;
import java.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class TeknonymyServiceTest {

    /**
     * The corresponding implementations to test.
     * <p>
     * If you want, you can make others :)
     */
    public TeknonymyServiceTest() {
    }

    ;

    @Test
    public void PersonNoChildrenTest() {
        Person person = new Person("John", 'M', null, LocalDateTime.of(1046, 1, 1, 0, 0));
        String result = new TeknonymyService().getTeknonymy(person);
        String expected = "";
        assertEquals(expected, result);
    }

    @Test
    public void PersonOneChildTest() {
        Person person = new Person(
                "John",
                'M',
                new Person[]{new Person("Holy", 'F', null, LocalDateTime.of(1046, 1, 1, 0, 0))},
                LocalDateTime.of(1046, 1, 1, 0, 0));
        String result = new TeknonymyService().getTeknonymy(person);
        String expected = "father of Holy";
        assertEquals(expected, result);
    }

    @Test
    public void PersonGranFatherOneChildTest() {
        Person person = new Person("John", 'M',
                new Person[]{new Person("Holy", 'F',
                        new Person[]{new Person("Joana", 'F', null, LocalDateTime.of(1046, 1, 1, 0, 0))},
                        LocalDateTime.of(1046, 1, 1, 0, 0))}, LocalDateTime.of(1999, 5, 1, 1, 0));
        String result = new TeknonymyService().getTeknonymy(person);
        String expected = "grandfather of Joana";
        assertEquals(expected, result);
    }

    @Test
    public void PersonGreatGranMotherTwoChildTest() {
        Person person = new Person("A", 'F', new Person[]{
                new Person("B", 'F', new Person[] {
                        new Person("C", 'M', new Person[] {
                                new Person("D", 'M', null, LocalDateTime.of(2023, 1, 1, 0, 0))
                        }, LocalDateTime.of(2020, 1, 1, 0, 0)),
                }, LocalDateTime.of(2000, 1, 1, 0, 0)),
                new Person("B1", 'M', null, LocalDateTime.of(1950, 1, 1, 0, 0))
        }, LocalDateTime.of(1046, 1, 1, 0, 0));

        String result = new TeknonymyService().getTeknonymy(person);
        String expected = "great-grandmother of D";
        assertEquals(expected, result);
    }


    @Test
    public void PersonGreatGreatGrandFatherTest() {
        Person a = new Person(
                "A", 'M',
                new Person[] {
                    new Person("B", 'M', new Person[] {
                            new Person("C", 'M', new Person[] {
                                    new Person("D", 'M', null, LocalDateTime.of(2000, 1, 1, 0, 0)),
                                    new Person("E", 'M', new Person[] {
                                            new Person("F", 'M', null, LocalDateTime.of(2020, 1, 1, 0, 0))
                                    }, LocalDateTime.of(2001, 1, 1, 0, 0))
                            }, LocalDateTime.of(1980, 1, 1, 0, 0))
                    }, LocalDateTime.of(1950, 1, 1, 0, 0))
                }, LocalDateTime.of(1930, 1, 1, 0, 0)
        );

      String result = new TeknonymyService().getTeknonymy(a);
      String expected = "great-great-grandfather of F";
      assertEquals(expected, result);

    }
}