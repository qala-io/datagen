package io.qala.datagen.examples;

import static org.apache.commons.lang3.StringUtils.isBlank;

class ProfileDto {
    private String username;
    private String firstName;
    private String lastName;
    private String country;

    static ProfileDto fromEntity(Person person) {
        ProfileDto dto = new ProfileDto();
        dto.username = person.username();
        dto.firstName = person.firstName();
        dto.lastName = person.lastName();
        dto.country = person.country() == null ? "" : person.country().name();
        return dto;
    }

    Person toEntity() {
        Person person = new Person(username);
        person.firstName(firstName);
        person.lastName(lastName);
        Country country = isBlank(this.country) ? null : Country.valueOf(this.country.toUpperCase());
        person.country(country);
        return person;
    }
}
