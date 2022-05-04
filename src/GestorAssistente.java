import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GestorAssistente {

    public void lerVooTxt(String nf) throws IOException {
        Map<Integer, Voo> dicVoo = new HashMap<>();
        int idRota, idVoo, hora, minuto, segundo;
        String diaSemana, marcadoAviao;
        BufferedReader f = new BufferedReader(new FileReader(new File("voos.txt")));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");//dividir os campos pelo tab; o ficheiro está assim <código>\t<nome>\t<tipo>\t<nºUnidades>\t<nºUnidadesMínimo>\t<preço>\t<fornecedor>
            idRota = Integer.parseInt(campos[0]);
            idVoo = Integer.parseInt(campos[1]);
            diaSemana = campos[2];
            hora = Integer.parseInt(campos[3]);
            minuto = Integer.parseInt(campos[4]);
            segundo = Integer.parseInt(campos[5]);
            marcadoAviao = campos[6];
            Voo v = new Voo(idRota, idVoo, diaSemana, hora, minuto, segundo, marcadoAviao);
            dicVoo.put(idVoo, v);
            linha = f.readLine();
        }
        f.close();
    }

    public void listarVoosPorRota (String nf, int idRotas) throws IOException {
        Map<Integer, Voo> dicVoo = new HashMap<>();
        int idRota = 0, idVoo, hora, minuto, segundo;
        String diaSemana, marcadoAviao;
        BufferedReader f = new BufferedReader(new FileReader(new File("voos.txt")));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");//dividir os campos pelo tab; o ficheiro está assim <código>\t<nome>\t<tipo>\t<nºUnidades>\t<nºUnidadesMínimo>\t<preço>\t<fornecedor>

            idVoo = Integer.parseInt(campos[0]);
            idRota = Integer.parseInt(campos[1]);
            diaSemana = campos[2];
            hora = Integer.parseInt(campos[3]);
            minuto = Integer.parseInt(campos[4]);
            segundo = Integer.parseInt(campos[5]);
            marcadoAviao = campos[6];
            Voo v = new Voo(idRota, idVoo, diaSemana, hora, minuto, segundo, marcadoAviao);
            dicVoo.put(idVoo, v);
            linha = f.readLine();
        }
        f.close();

        for(Map.Entry<Integer,Voo> voos : dicVoo.entrySet()){

            if(dicVoo.containsKey(idRotas)){
                System.out.println(dicVoo);

            }else{
                System.out.println("Erro! Não existe voo dessa rota.");
            }
        }
    }
































   /* private String toStringV(Voo value) {
        return "\nId Voo: " + value.getIdVoo() + "\nId Rota: " + value.getIdRota() + "\nDia Semana: " + value.getDiaSemana() + "\nHora Voo: " + value.getHora() + "\nMinuto Voo: " + value.getMinuto() + "\nSegundo Voo: " + value.getSegundo() +  "\nMarca Avião: " + value.getMarcaAviao() + "\n";
    }*/


    public void lerPassageiroNomeIdTxt(String nf) throws IOException {
        Map<String, Passageiro> dicPassageiro = new HashMap<>();
        String idPassageiro, nome;
        BufferedReader f = new BufferedReader(new FileReader(new File(nf)));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");//dividir os campos pelo tab; o ficheiro está assim <código>\t<nome>\t<tipo>\t<nºUnidades>\t<nºUnidadesMínimo>\t<preço>\t<fornecedor>
            idPassageiro = campos[0];
            nome = campos[1];

            Passageiro p = new Passageiro(idPassageiro, nome);
            dicPassageiro.put(idPassageiro, p);
            linha = f.readLine();
        }
        f.close();

        for(Map.Entry<String, Passageiro> passageiros : dicPassageiro.entrySet()){
            System.out.println(passageiros.getKey() + ": " + toStringP(passageiros.getValue()));
        }
    }

    private String toStringP(Passageiro value) {
        return "\nId: " + value.getIdPassageiro() + "\nNome: " + value.getNome() + "\n";
    }

    public void lerRotaTxt(String nf) throws IOException {
        Map<Integer,Rota> dicRota = new HashMap<>();
        int idRota, quantidadeVoos;
        String destino;
        double distanciaKm;
        BufferedReader f = new BufferedReader(new FileReader(new File(nf)));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");//dividir os campos pelo tab; o ficheiro está assim <código>\t<nome>\t<tipo>\t<nºUnidades>\t<nºUnidadesMínimo>\t<preço>\t<fornecedor>
            idRota = Integer.parseInt(campos[0]);
            quantidadeVoos = Integer.parseInt(campos[1]);
            destino = campos[2];
            distanciaKm = Double.parseDouble(campos[3]);
            Rota r = new Rota(idRota, quantidadeVoos, destino, distanciaKm);
            dicRota.put(idRota, r);
            linha = f.readLine();
        }
        f.close();

        for(Map.Entry<Integer, Rota> rotas : dicRota.entrySet()){
            System.out.println(rotas.getKey() + ": " + toStringR(rotas.getValue()));
        }
    }

    private String toStringR(Rota value) {
        return "\nId: " + value.getIdRota() + "\nQuantidade de Voos: " + value.getQuantidadeHorarios() + "\nDestino: " + value.getDestino() + "\nDistancia (km): " + value.getDistanciaKm() + "\n";
    }

    public void lerBilheteTxt(String nf) throws IOException {
        Map<Integer, Bilhete> dicBilhete = new HashMap<>();
        int idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao, horaAquisicao, minAquisicao, segAquisicao;
        String idPassageiro;
        double preco;
        BufferedReader f = new BufferedReader(new FileReader(new File("bilhetes.txt")));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");//dividir os campos pelo tab; o ficheiro está assim <código>\t<nome>\t<tipo>\t<nºUnidades>\t<nºUnidadesMínimo>\t<preço>\t<fornecedor>
            idPassageiro = campos[0];
            idRota = Integer.parseInt(campos[1]);
            idVoo = Integer.parseInt(campos[2]);
            anoViagem = Integer.parseInt(campos[3]);
            mesViagem = Integer.parseInt(campos[4]);
            diaViagem = Integer.parseInt(campos[5]);
            horaViagem = Integer.parseInt(campos[6]);
            minViagem = Integer.parseInt(campos[7]);
            segViagem = Integer.parseInt(campos[8]);
            anoAquisicao = Integer.parseInt(campos[9]);
            mesAquisicao = Integer.parseInt(campos[10]);
            diaAquisicao = Integer.parseInt(campos[11]);
            horaAquisicao = Integer.parseInt(campos[12]);
            minAquisicao = Integer.parseInt(campos[13]);
            segAquisicao = Integer.parseInt(campos[14]);
            preco = Double.parseDouble(campos[15]);
            Bilhete b = new Bilhete(idPassageiro, idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao, horaAquisicao, minAquisicao, segAquisicao, preco);
            dicBilhete.put(idRota, b);
            linha = f.readLine();
        }
        f.close();
    }
}
