package io.qala.datagen.examples;

import org.junit.Test;

import java.util.List;

import static io.qala.datagen.RandomShortApi.sample;

@SuppressWarnings("unchecked")
public class _45_ModelBasedTest {
    @Test public void addingMaterials() {
        OrderingTestModel model = new OrderingTestModel();
        model.createExperiment();
        goOverModel(model.allActions(), 10000);
    }

    private static void goOverModel(List<Runnable> actions, int nTimes) {
        for (int i = 0; i < nTimes; i++) sample(actions).run();
    }
}
