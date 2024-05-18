package com.premiumminds.internship.teknonymy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class TeknonymyService implements ITeknonymyService {

    /**
     * Method to get a Person Teknonymy Name
     *
     * @param Person person
     * @return String which is the Teknonymy Name
     */
    public String getTeknonymy(Person person) {

        if (hasChildren(person)) {
            int counter = 1;
            Pair<Person, Integer> oldest = getOldestTek(person, counter);
            if (oldest.getSecond() == 1) {
                String tek = whichSex(person) ? "mother of " : "father of ";
                return tek + oldest.getFirst().name();
            } else {
                StringBuilder builder = new StringBuilder();
                String tek = whichSex(person) ? "grandmother of " : "grandfather of ";
                String great = "great-";
                int finalCounter = oldest.getSecond();

                while (finalCounter > 2){
                    builder.append(great);
                    finalCounter--;
                }
                return builder.append(tek).append(oldest.getFirst().name()).toString();
            }
        }

        return "";
    }

    private Pair<Person, Integer> getOldestTek(Person person, int counter) {
        List<Pair<Person, Integer>> oldestTeks = new ArrayList<>();
        int temp = counter;
        counter++;
        if (hasChildren(person)) {
            Person myOldestChild = null;
            for (Person child : person.children()) {
                if(hasChildren(child)) {
                    Pair<Person, Integer> c = getOldestTek(child, counter);
                    oldestTeks.add(c);
                }
                if (myOldestChild == null) {
                    myOldestChild = child;
                } else {
                   if (ageCompare(child, myOldestChild) == 1) {
                       myOldestChild = child;
                   }
                }
            }
            if (myOldestChild != null) {
                oldestTeks.add(new Pair<>(myOldestChild, temp));
            }
        }

        int max = oldestTeks.stream()
                .mapToInt(Pair::getSecond)
                .max()
                .orElse(-1);

        List<Pair<Person, Integer>> furtherAway = oldestTeks.stream()
                .filter(pair -> pair.getSecond() == max)
                .toList();

        return furtherAway.stream()
                .max((pair1, pair2) -> ageCompare(pair1.getFirst(), pair2.getFirst()))
                .orElse(null);
    }

    public boolean hasChildren(Person person) {
        return person.children() != null && person.children().length > 0;
    }

    public boolean whichSex(Person person) {
        return person.sex().equals('F');
    }

    public int ageCompare(Person person1, Person person2) {
        return person1.dateOfBirth().isBefore(person2.dateOfBirth()) ? 1 : -1;
    }
}
