public class Bilhete {
    private String idPassageiro;
    private int idRota;
    private int idVoo;
    private int anoViagem;
    private int mesViagem;
    private int diaViagem;
    private int horaViagem;
    private int minutoViagem;
    private int segundoViagem;
    private int anoAquisicao;
    private int mesAquisicao;
    private int diaAquisicao;
    private int horaAquisicao;
    private int minutoAquisicao;
    private int segundoAquisicao;
    private double preco;
    private int tipoBilhete; // 1-bilhete efetivo --- 2-bilhete suplente

    public Bilhete(String idPassageiro, int idRota, int idVoo, int anoViagem, int mesViagem, int diaViagem, int horaViagem, int minutoViagem, int segundoViagem, int anoAquisicao, int mesAquisicao, int diaAquisicao, int horaAquisicao, int minutoAquisicao, int segundoAquisicao, double preco,int tipoBilhete) {
        this.idPassageiro = idPassageiro;
        this.idRota = idRota;
        this.idVoo = idVoo;
        this.anoViagem = anoViagem;
        this.mesViagem = mesViagem;
        this.diaViagem = diaViagem;
        this.horaViagem = horaViagem;
        this.minutoViagem = minutoViagem;
        this.segundoViagem = segundoViagem;
        this.anoAquisicao = anoAquisicao;
        this.mesAquisicao = mesAquisicao;
        this.diaAquisicao = diaAquisicao;
        this.horaAquisicao = horaAquisicao;
        this.minutoAquisicao = minutoAquisicao;
        this.segundoAquisicao = segundoAquisicao;
        this.preco = preco;
        this.tipoBilhete = tipoBilhete;
    }

    public String getIdPassageiro() {return idPassageiro;}
    public void setIdPassageiro(String idPassageiro) {this.idPassageiro = idPassageiro;}

    public int getIdRota() {return idRota;}
    public void setIdRota(int idRota) {this.idRota = idRota;}

    public int getIdVoo() {return idVoo;}
    public void setIdVoo(int idVoo) {this.idVoo = idVoo;}

    public int getAnoViagem() {return anoViagem;}
    public void setAnoViagem(int anoViagem) {this.anoViagem = anoViagem;}

    public int getMesViagem() {return mesViagem;}
    public void setMesViagem(int mesViagem) {this.mesViagem = mesViagem;}

    public int getDiaViagem() {return diaViagem;}
    public void setDiaViagem(int diaViagem) {this.diaViagem = diaViagem;}

    public int getHoraViagem() {return horaViagem;}
    public void setHoraViagem(int horaViagem) {this.horaViagem = horaViagem;}

    public int getMinutoViagem() {return minutoViagem;}
    public void setMinutoViagem(int minutoViagem) {this.minutoViagem = minutoViagem;}

    public int getSegundoViagem() {return segundoViagem;}
    public void setSegundoViagem(int segundoViagem) {this.segundoViagem = segundoViagem;}

    public int getAnoAquisicao() {return anoAquisicao;}
    public void setAnoAquisicao(int anoAquisicao) {this.anoAquisicao = anoAquisicao;}

    public int getMesAquisicao() {return mesAquisicao;}
    public void setMesAquisicao(int mesAquisicao) {this.mesAquisicao = mesAquisicao;}

    public int getDiaAquisicao() {return diaAquisicao;}
    public void setDiaAquisicao(int diaAquisicao) {this.diaAquisicao = diaAquisicao;}

    public int getHoraAquisicao() {return horaAquisicao;}
    public void setHoraAquisicao(int horaAquisicao) {this.horaAquisicao = horaAquisicao;}

    public int getMinutoAquisicao() {return minutoAquisicao;}
    public void setMinutoAquisicao(int minutoAquisicao) {this.minutoAquisicao = minutoAquisicao;}

    public int getSegundoAquisicao() {return segundoAquisicao;}
    public void setSegundoAquisicao(int segundoAquisicao) {this.segundoAquisicao = segundoAquisicao;}

    public double getPreco() {return preco;}
    public void setPreco(double preco) {this.preco = preco;}

    public int getTipoBilhete() {return tipoBilhete;}
    public void setTipoBilhete(int tipoBilhete) {this.tipoBilhete = tipoBilhete;}

}
