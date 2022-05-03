public class Voo {
    private int idVoo;
    private int idRota;
    private String diaSemana;
    private int horaVoo;
    private int minutoVoo;
    private int segundoVoo;
    private String marcaAviao;

    public Voo(int idVoo, int idRota, String diaSemana, int horaVoo, int minutoVoo, int segundoVoo, String marcaAviao) {
        this.idVoo = idVoo;
        this.idRota = idRota;
        this.diaSemana = diaSemana;
        this.horaVoo = horaVoo;
        this.minutoVoo = minutoVoo;
        this.segundoVoo = segundoVoo;
        this.marcaAviao = marcaAviao;
    }

    public int getIdVoo() {return idVoo;}
    public void setIdVoo(int idVoo) {this.idVoo = idVoo;}

    public int getIdRota() {return idRota;}
    public void setIdRota(int idRota) {this.idVoo = idRota;}

    public String getDiaSemana() {return diaSemana;}
    public void setDiaSemana(String diaSemana) {this.diaSemana = diaSemana;}

    public int getHora() {return horaVoo;}
    public void setHora(int horaVoo) {this.horaVoo = horaVoo;}

    public int getMinuto() {return minutoVoo;}
    public void setMinuto(int minutoVoo) {this.minutoVoo = minutoVoo;}

    public int getSegundo() {return segundoVoo;}
    public void setSegundo(int segundoVoo) {this.segundoVoo = segundoVoo;}

    public String getMarcaAviao() {return marcaAviao;}
    public void setMarcaAviao(String marcaAviao) {this.marcaAviao = marcaAviao;}
}
