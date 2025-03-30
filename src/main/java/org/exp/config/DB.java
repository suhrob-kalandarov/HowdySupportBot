package org.exp.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DB {

    public static EntityManagerFactory EMF;

    public static void worker() {
        EMF = Persistence.createEntityManagerFactory("myPU");
    }
}
