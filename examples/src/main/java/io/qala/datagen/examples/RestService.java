package io.qala.datagen.examples;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** We should pretend that this is a real RESTful service or any storage like DB for that matter. */
@SuppressWarnings("Convert2Diamond")
class RestService {
    static final RestService instance = new RestService();

    private final Map<String, Person> users = new ConcurrentHashMap<String, Person>();
    private final Map<String, Experiment> experiments = new ConcurrentHashMap<String, Experiment>();

    private RestService() {}

    Person save(Person newPerson) {
        String username = newPerson.username();
        if (users.containsKey(username))
            throw new IllegalArgumentException("Unique constraint violation for username: " + username);
        users.put(username, newPerson);
        return newPerson;
    }

    Person searchByUsername(String username) {//a.k.a searching by query params
        return users.get(username);
    }
    Person searchByUsername(SearchRequest req) {//a.k.a searching by request body
        return users.get(req.username);
    }

    Experiment createExperiment() {
        Experiment experiment = new Experiment();
        experiments.put(experiment.id(), experiment);
        return experiment;
    }

    Experiment findExperiment(String id) {
        Experiment fromDb = this.experiments.get(id);
        if (fromDb == null) throw new ObjectNotFoundException(Experiment.class, id);
        return experiments.get(id);
    }

    void addMaterials(Experiment experiment, List<Material> materials) {
        findExperiment(experiment.id()).addMaterials(materials);
    }
    void receiveOrderedMaterials(Experiment experiment, List<Material> materials) {
        findExperiment(experiment.id()).receiveOrder(materials);
    }

    void closeExperiment(String id) {
        findExperiment(id).close();
    }

    void reopenExperiment(String id) {
        findExperiment(id).reopenExperiment();
    }

    static class SearchRequest {
        String username;

        SearchRequest(String username) {
            this.username = username;
        }
    }
}
