public class Rota {
    private int idRota;
    private int quantidadeVoos;
    private String destino;
    private double distanciaKm;

    public Rota(int idRota, int quantidadeVoos, String destino, double distanciaKm) {
        this.idRota = idRota;
        this.quantidadeVoos = quantidadeVoos;
        this.destino = destino;
        this.distanciaKm = distanciaKm;
    }

    public int getIdRota() {return idRota;}
    public void setIdRota(int idRota) {this.idRota = idRota;}

    public int getQuantidadeHorarios() {return quantidadeVoos;}
    public void setQuantidadeHorarios(int quantidadeVoos) {this.quantidadeVoos = quantidadeVoos;}

    public String getDestino() {return destino;}
    public void setDestino(String destino) {this.destino = destino;}

    public double getDistanciaKm() {return distanciaKm;}
    public void setDistanciaKm(int distanciaKm) {this.distanciaKm = distanciaKm;}
}
