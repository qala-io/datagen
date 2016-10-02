package io.qala.datagen.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("Convert2Diamond")
class Experiment {
    private String id = UUID.randomUUID().toString();
    private boolean editable = true;
    private final List<Material> materials = new ArrayList<Material>();
    private final List<Material> receivedOrder = new ArrayList<Material>();

    void addMaterials(List<Material> materials) {
        if (!editable) throw new EditingReadOnlyExperimentException(id());
        this.materials.addAll(materials);
    }

    void receiveOrder(List<Material> actualMaterial) {
        if (!editable) throw new EditingReadOnlyExperimentException(id());
        this.receivedOrder.addAll(actualMaterial);
    }

    String id() {
        return id;
    }

    boolean editable() {
        return editable;
    }

    void close() {
        editable = false;
    }

    void reopenExperiment() {
        editable = true;
    }

    boolean containsMaterials(List<Material> materials) {
        return this.materials.containsAll(materials);
    }

    boolean containsOrderedMaterials(List<Material> materials) {
        return this.receivedOrder.containsAll(materials);
    }
}