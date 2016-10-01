package io.qala.datagen.examples;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** We should pretend that this is a real RESTful service or any storage like DB for that matter. */
class RestService {
    static final RestService instance = new RestService();

    private final Map<String, Person> db = new ConcurrentHashMap<String, Person>();

    private RestService() {}

    Person save(Person newPerson) {
        String username = newPerson.username();
        if (db.containsKey(username))
            throw new IllegalArgumentException("Unique constraint violation for username: " + username);
        db.put(username, newPerson);
        return newPerson;
    }

    Person searchByUsername(String username) {//a.k.a searching by query params
        return db.get(username);
    }
    Person searchByUsername(SearchRequest req) {//a.k.a searching by request body
        return db.get(req.username);
    }

    static class SearchRequest {
        String username;

        SearchRequest(String username) {
            this.username = username;
        }
    }
}
