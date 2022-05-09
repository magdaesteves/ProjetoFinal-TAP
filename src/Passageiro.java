public class Passageiro {
    private String idPassageiro;
    private String nome;
    private String profissao;
    private String morada;
    private int anoNascimento;
    private int mesNascimento;
    private int diaNascimento;

    public Passageiro(String idPassageiro, String nome, String profissao, String morada, int anoNascimento, int mesNascimento, int diaNascimento) {
        this.idPassageiro = idPassageiro;
        this.nome = nome;
        this.profissao = profissao;
        this.morada = morada;
        this.anoNascimento = anoNascimento;
        this.mesNascimento = mesNascimento;
        this.diaNascimento = diaNascimento;
    }

    public Passageiro(String idPassageiro, String nome) {
        this.idPassageiro = idPassageiro;
        this.nome = nome;
    }

    public String getIdPassageiro() {return idPassageiro;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getProfissao() {return profissao;}
    public void setProfissao(String profissao) {this.profissao = profissao;}

    public String getMorada() {return morada;}
    public void setMorada(String morada) {this.morada = morada;}

    public int getAno() {return anoNascimento;}
    public void setAno(int anoNascimento) {this.anoNascimento = anoNascimento;}

    public int getMes() {return mesNascimento;}
    public void setMes(int mesNascimento) {this.mesNascimento = mesNascimento;}

    public int getDia() {return diaNascimento;}
    public void setDia(int diaNascimento) {this.diaNascimento = diaNascimento;}
}
