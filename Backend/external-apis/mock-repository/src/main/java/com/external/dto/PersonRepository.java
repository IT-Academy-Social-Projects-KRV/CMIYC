package com.external.dto;

import java.util.HashSet;
import java.util.Set;

public class PersonRepository {

    private final Set<Persons> persons = new HashSet<>();

    public PersonRepository() {
        onInit();
    }

    void onInit() {
        Persons johnJohnson = new Persons();
        johnJohnson.setFirstName("John");
        johnJohnson.setLastName("Johnson");
        johnJohnson.setBirthDayDate("01.01.1981");
        johnJohnson.setGender(Gender.MALE);
        johnJohnson.setAdditional("Administrative offense:1. offense against the law: 0.");
        persons.add(johnJohnson);

        Persons peterPeterson = new Persons();
        peterPeterson.setFirstName("Peter");
        peterPeterson.setLastName("Peterson");
        peterPeterson.setBirthDayDate("02.02.1982");
        peterPeterson.setGender(Gender.MALE);
        peterPeterson.setAdditional("Administrative offense: 1. offense against the law: 1.");
        persons.add(peterPeterson);

        Persons stevenStevenson = new Persons();
        stevenStevenson.setFirstName("Steven");
        stevenStevenson.setLastName("Stevenson");
        stevenStevenson.setBirthDayDate("03.03.1983");
        stevenStevenson.setGender(Gender.MALE);
        stevenStevenson.setAdditional("Administrative offense: 0. offense against the law: 1.");
        persons.add(stevenStevenson);

        Persons amandaArmstrong = new Persons();
        amandaArmstrong.setFirstName("Amanda");
        amandaArmstrong.setLastName("Armstrong");
        amandaArmstrong.setBirthDayDate("04.04.1984");
        amandaArmstrong.setGender(Gender.FEMALE);
        amandaArmstrong.setAdditional("Administrative offense: 2. offense against the law: 0.");
        persons.add(amandaArmstrong);
    }

    public Persons findPerson(ApiOneReq request) {
        Persons result = new Persons();

        for (Persons p : persons) {
            if (request.getFirstName().equals(p.getFirstName()) && request.getLastName().equals(p.getLastName())) {
                result = p;
                break;
            } else {
                result = new Persons(request.getFirstName(), request.getLastName(),
                        "none", Gender.UNKNOWN, "Can't find such person");
            }
        }
        return result;
    }

    public Persons findPerson(ApiTwoReq request) {
        Persons result = null;

        for (Persons p : persons) {
            if (request.getFirstName().equals(p.getFirstName())
                    && request.getLastName().equals(p.getLastName())
                    && request.getBirthDayDate().equals(p.getBirthDayDate())) {
                result = p;
                break;
            } else {
                result = new Persons(request.getFirstName(), request.getLastName(), request.getBirthDayDate(),
                        Gender.UNKNOWN, "Can't find such person");
            }
        }
        return result;
    }

    public Persons findPerson(ApiThreeReq request) {
        Persons result = null;

        for (Persons p : persons) {
            if (request.getFirstName().equals(p.getFirstName())
                    && request.getLastName().equals(p.getLastName())
                    && request.getBirthDayDate().equals(p.getBirthDayDate()) && request.getGender().equals(p.getGender())) {
                result = p;
                break;

            } else {
                result = new Persons(request.getFirstName(), request.getLastName(), request.getBirthDayDate(), request.getGender(), "Can not find such person");
            }
        }
        return result;
    }
}
