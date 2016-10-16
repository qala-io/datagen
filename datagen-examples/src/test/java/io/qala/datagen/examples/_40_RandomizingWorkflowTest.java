package io.qala.datagen.examples;

import org.junit.Test;

import static io.qala.datagen.RandomShortApi.*;
import static org.junit.Assert.assertEquals;

public class _40_RandomizingWorkflowTest {

    @Test public void findsPersonByUsername_inSearchApi() {
        Person person = new Person(english(1, 50));
        rest.save(person);
        assertEquals(person, search(person.username()));
    }

    private Person search(String username) {
        return bool() ?
                rest.searchByUsername(username) :
                rest.searchByUsername(new RestService.SearchRequest(username));
    }

    private Person search2(String username) {//just a demo of alternative API
        Person[] person = new Person[1];
        callOneOf(
                () -> person[0] = rest.searchByUsername(username),
                () -> person[0] = rest.searchByUsername(new RestService.SearchRequest(username))
                // could be more options
        );
        return person[0];
    }

    private final RestService rest = RestService.instance;
}
