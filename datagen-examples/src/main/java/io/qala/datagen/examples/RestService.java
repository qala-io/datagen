package io.qala.datagen.examples;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** We should pretend that this is a real RESTful service or any storage like DB for that matter. */
class RestService {
    static final RestService instance = new RestService();

    private final Map<String, Person> db = new ConcurrentHashMap<>();

    private RestService() {}

    Person save(Person newPerson) {
        String username = newPerson.username();
        if (db.containsKey(username))
            throw new IllegalArgumentException("Unique constraint violation for username: " + username);
        db.put(username, newPerson);
        return newPerson;
    }
}
