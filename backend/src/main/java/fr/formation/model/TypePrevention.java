package fr.formation.model;

public enum TypePrevention {
    medical("Médical"), barriere("Barrière"), comportement("Comportemental");

    private String typePrevention;

    private TypePrevention(String typePrevention) {
        this.typePrevention = typePrevention;
    }

    public String getTypePrevention() {
        return typePrevention;
    }

    public void setTypePrevention(String typePrevention) {
        this.typePrevention = typePrevention;
    }

}
