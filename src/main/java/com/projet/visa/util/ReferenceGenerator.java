package com.projet.visa.util;
import java.util.UUID;
public class ReferenceGenerator {
    public static String generateReference() {
        return UUID.randomUUID().toString();
    }
}
