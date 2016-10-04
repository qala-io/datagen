package io.qala.datagen.examples;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("Convert2Diamond")
class Db {
    private final Map<String, Person> db = new ConcurrentHashMap<String, Person>();
    private static final int USERNAME_MAX_LENGTH = 32;

    Person save(Person newPerson) {
        newPerson.validate();
        String username = newPerson.username();
        validateInDb(username);
        db.put(username, newPerson);
        return newPerson;
    }

    private void validateInDb(String username) {
        if (StringUtils.isBlank(username))
            throw new IllegalArgumentException("DB Constraints violation: username is mandatory! Passed: [" + username + "]");
        if (nOfBytes(username) > USERNAME_MAX_LENGTH)
            throw new IllegalStateException("DB Constraints violation: username [" + username + "] cannot exceed " + USERNAME_MAX_LENGTH + " bytes!");
        if (db.containsKey(username))
            throw new IllegalArgumentException("DB Unique constraint violation for username: " + username);
    }

    private int nOfBytes(String str) {
        try {
            return str.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
