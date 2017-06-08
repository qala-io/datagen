package io.qala.datagen.examples;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

class OrderingTestModel {
    private final RestService restService = RestService.instance;
    private final List<Material> materials = new ArrayList<>();
    private final List<Material> receivedMaterial = new ArrayList<>();
    private Experiment experiment;

    List<Runnable> allActions() {
        return asList(
                () -> addMaterialsSuccessfully(), () -> addMaterialToClosedExperimentFails(),
                () -> sendOrderedMaterialToExperimentSuccessfully(), () -> sendOrderedMaterialToClosedExperimentFails(),
                () -> closeExperiment(), () -> reopenExperiment());
    }

    void createExperiment() {
        System.out.println("Creating experiment");
        experiment = restService.createExperiment();
        assertNotNull(restService.findExperiment(experiment.id()));
    }

    private void addMaterialsSuccessfully() {
        if (!experiment.editable()) return;
        System.out.println("Adding new materials to experiment " + experiment.id());
        List<Material> newMaterials = asList(Material.random(), Material.random());
        materials.addAll(newMaterials);

        restService.addMaterials(experiment, newMaterials);

        Experiment fromDb = restService.findExperiment(this.experiment.id());
        assertTrue(fromDb.containsMaterials(this.materials));
    }

    private void sendOrderedMaterialToExperimentSuccessfully() {
        if (!experiment.editable() || !materials.isEmpty()) return;
        System.out.println("Receive ordered material for experiment " + experiment.id());
        List<Material> newMaterials = asList(Material.random(), Material.random());
        receivedMaterial.addAll(newMaterials);

        restService.receiveOrderedMaterials(experiment, newMaterials);

        Experiment fromDb = restService.findExperiment(this.experiment.id());
        assertTrue(fromDb.containsOrderedMaterials(this.receivedMaterial));
    }

    private void closeExperiment() {
        System.out.println("Closing experiment " + experiment.id());
        restService.closeExperiment(experiment.id());
        assertFalse(restService.findExperiment(experiment.id()).editable());
    }

    private void reopenExperiment() {
        System.out.println("Reopening experiment " + experiment.id());
        restService.reopenExperiment(experiment.id());
        assertTrue(restService.findExperiment(experiment.id()).editable());
    }

    private void sendOrderedMaterialToClosedExperimentFails() {
        if (experiment.editable()) return;
        System.out.println("Receive ordered material for closed experiment (has to fail) " + experiment.id());

        List<Material> newMaterials = asList(Material.random(), Material.random());
        try {
            restService.receiveOrderedMaterials(experiment, newMaterials);
            fail("Receiving ordered material to closed experiment had to fail, but it didn't");
        } catch (EditingReadOnlyExperimentException e) {}
    }

    private void addMaterialToClosedExperimentFails() {
        if (experiment.editable()) return;
        System.out.println("Adding material to closed experiment (has to fail): " + experiment.id());
        try {
            restService.addMaterials(experiment, asList(Material.random(), Material.random()));
            fail("Adding material to closed experiment had to fail, but it didn't");
        } catch (EditingReadOnlyExperimentException e) {}
    }
}
