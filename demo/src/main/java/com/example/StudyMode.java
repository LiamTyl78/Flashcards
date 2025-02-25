package com.example;

import java.util.ArrayList;

public class StudyMode {
    void startMode(){

    };

    public void shuffle(ArrayList<Card> list) {
        RandomInteger random = new RandomInteger(0, list.size() - 1);
        for (int i = 0; i < 40; i++) {
            int randomindex = random.Generate();
            Card temp = list.get(randomindex);
            temp = list.set(random.Generate(), temp);
            list.set(randomindex, temp);
        }
    }
}
