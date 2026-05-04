package fr.formation.backend.model;

public enum Transmission {
    orale("Orale"), sexuelle("Sexuelle"), sang("Contact sanguin"), direct("Contact direct"), materno("Materno-fœtale");

    private String typeTransmission;

    private Transmission(String typeTransmission) {
        this.typeTransmission = typeTransmission;
    }

    public String getTypeTransmission() {
        return typeTransmission;
    }

    public void setTypeTransmission(String typeTransmission) {
        this.typeTransmission = typeTransmission;
    }

}
