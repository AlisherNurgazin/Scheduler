package kz.iitu.java.userserviceclient.constants;

import lombok.Getter;

@Getter
public enum  DEFConstants {
    ALTEL("700"),
    KCELL("701"),
    KCELL_2("702"),
    BEELINE("705"),
    TELE2("707"),
    ALTEL_2("708"),
    TELE2_2("747"),
    BEELINE_2("771"),
    KCELL_3("775"),
    BEELINE_3("776"),
    BEELINE_4("777"),
    KCELL_4("778");

    private final String name;

    DEFConstants(String name) {
        this.name = name;
    }
}
