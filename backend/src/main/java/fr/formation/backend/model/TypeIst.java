package fr.formation.backend.model;

public enum TypeIst {
    bacterie("Bactérienne"), virus("Virale"), parasite("Parasitaire"),
    bacterien("Bactérien"), viral("Viral");

    private String typeIst;

    private TypeIst(String typeIst) {
        this.typeIst = typeIst;
    }

    public String getTypeIst() {
        return typeIst;
    }

    public void setTypeIst(String typeIst) {
        this.typeIst = typeIst;
    }
}
