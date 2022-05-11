import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.time.LocalDateTime;

public class GestorAssistente {
    //1 - Listar Rotas
    public void lerRotaTxt(String nf) throws IOException {
        HashMap<Integer,Rota> dicRota = new HashMap<>();
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

        for(HashMap.Entry<Integer, Rota> rotas : dicRota.entrySet()){
            System.out.println(toStringR(rotas.getValue()));
        }
    }

    private String toStringR(Rota value) {
        return "Id: " + value.getIdRota() + "\nQuantidade de Voos: " + value.getQuantidadeHorarios() + "\nDestino: " + value.getDestino() + "\nDistancia (km): " + value.getDistanciaKm() + "\n";
    }


    //2 - Listar os voos de uma rota
    public void listarVoosPorRota (String nf, int idRotas) throws IOException {
        HashMap<Integer, Voo> dicVoo = new HashMap<>();
        int idRota = 0, idVoo, hora, minuto, segundo,cont=0;
        String diaSemana, marcadoAviao;
        BufferedReader f = new BufferedReader(new FileReader(new File("voos.txt")));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");//dividir os campos pelo tab; o ficheiro está assim <código>\t<nome>\t<tipo>\t<nºUnidades>\t<nºUnidadesMínimo>\t<preço>\t<fornecedor>
            if(Integer.parseInt(campos[0]) == idRotas) { //ver se a rota do voo é a rota pedida (se for adiciona ao hashmap, senão lê a próxima linha)
                idRota = Integer.parseInt(campos[0]);
                idVoo = Integer.parseInt(campos[1]);
                diaSemana = campos[2];
                hora = Integer.parseInt(campos[3]);
                minuto = Integer.parseInt(campos[4]);
                segundo = Integer.parseInt(campos[5]);
                marcadoAviao = campos[6];
                Voo v = new Voo(idRota,idVoo, diaSemana, hora, minuto, segundo, marcadoAviao);
                dicVoo.put(idVoo, v);
                cont++;
            }
            linha = f.readLine();
        }
        f.close();

        if(dicVoo.isEmpty()){
            System.out.println("Erro! Não existem voos nesta rota.");
        }else {
            for (HashMap.Entry<Integer, Voo> voo : dicVoo.entrySet()) {
                System.out.println(toStringV(voo.getValue()));
            }
            System.out.println("Existem " + cont + " voos com a rota " + idRota + ".");
        }
    }

    private String toStringV(Voo value) {
        return "Id Voo: " + value.getIdVoo() + "\nId Rota: " + value.getIdRota() + "\nDia Semana: " + value.getDiaSemana() + "\nHora Voo: " + value.getHora() + "\nMinuto Voo: " + value.getMinuto() + "\nSegundo Voo: " + value.getSegundo() +  "\nMarca Avião: " + value.getMarcaAviao() + "\n";
    }

    /*Usado no 2
    public void lerVooTxt(String nf) throws IOException {
        HashMap<Integer, Voo> dicVoo = new HashMap<>();
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
    }*/


    //3 - Listar todos os passageiros (o nome e o ID do passageiro)
    public void lerPassageiroNomeIdTxt(String nf) throws IOException {
        HashMap<String, Passageiro> dicPassageiro = lerPassageiroTxt("passageiros.txt");
        if(dicPassageiro.isEmpty()) {
            System.out.println("O voo ainda não têm passageiros.");
        }else {
            for (HashMap.Entry<String, Passageiro> passageiro : dicPassageiro.entrySet()) {
                System.out.println(toStringP(passageiro.getValue()));
            }
        }
    }


    //4 - Listar os passageiros de um voo (o nome e o ID do passageiro)
    public HashMap<String,Bilhete> lerBilheteTxt(String nf,int idVooFiltro) throws IOException {
        HashMap<String, Bilhete> dicBilhete = new HashMap<>();
        int idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao, horaAquisicao, minAquisicao, segAquisicao;
        String idPassageiro;
        double preco;
        BufferedReader f = new BufferedReader(new FileReader(new File("bilhetes.txt")));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");//dividir os campos pelo tab; o ficheiro está assim <código>\t<nome>\t<tipo>\t<nºUnidades>\t<nºUnidadesMínimo>\t<preço>\t<fornecedor>
            if(idVooFiltro == 0 || idVooFiltro == Integer.parseInt(campos[2])){ //se o filtro for passado a 0, vai buscar todos os registos. Se o filtro vier preenchido vai buscar apenas os bilhetes do voo pretendido
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
                dicBilhete.put(idPassageiro, b);
            }
            linha = f.readLine();
        }
        f.close();
        return dicBilhete;
    }

    public HashMap<String,Passageiro> lerPassageiroTxt(String NomeFich) throws IOException {
        HashMap<String, Passageiro> dicPassageiro = new HashMap<>();
        int anoNascimento, mesNascimento, diaNascimento;
        String idPassageiro, nome, profissao, morada;
        BufferedReader f = new BufferedReader(new FileReader(new File(NomeFich)));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");//dividir os campos pelo tab; o ficheiro está assim <código>\t<nome>\t<tipo>\t<nºUnidades>\t<nºUnidadesMínimo>\t<preço>\t<fornecedor>
            idPassageiro = campos[0];
            nome = campos[1];
            profissao = campos[2];
            morada = campos[3];
            anoNascimento = Integer.parseInt(campos[4]);
            mesNascimento = Integer.parseInt(campos[5]);
            diaNascimento = Integer.parseInt(campos[6]);
            Passageiro p = new Passageiro(idPassageiro, nome, profissao, morada, anoNascimento, mesNascimento, diaNascimento);
            dicPassageiro.put(idPassageiro, p);
            linha = f.readLine();
        }
        f.close();
        return dicPassageiro;
    }

    public void lerPassageiroNomeIdTxtPorVoo(String nf,int idVoo) throws IOException {
        HashMap<String,Bilhete> dicBilhete = lerBilheteTxt("bilhetes.txt",idVoo); //lê os bilhetes com o filtro do id do voo
        HashMap<String, Passageiro> dicPassageiro = lerPassageiroTxt("passageiros.txt"); //lê os passageiros todos
        if(dicBilhete.isEmpty()) { //se não existirem bilhetes diz que o voo não tem passageiros
            System.out.println("O voo " + idVoo + " ainda não têm passageiros.");
        }else{
            for (HashMap.Entry<String, Bilhete> bilhete : dicBilhete.entrySet()) { //senão corre os bilhetes
                if(dicPassageiro.get(bilhete.getKey()) == null){ //vê se o passageiro existe no ficheiro dos passageiros
                    System.out.println("\nPassageiro " + bilhete.getKey() + " sem registo!"); //se não existe, diz que não tem registo
                }else{
                    System.out.println(toStringP(dicPassageiro.get(bilhete.getKey()))); //senão printamos o passageiro
                }
            }
        }
    }

    private String toStringP(Passageiro value) {
        return "Id: " + value.getIdPassageiro() + "\nNome: " + value.getNome()+"\n";
    }


    //5 - Listar os passageiros suplentes de um voo (o nome e o ID do passageiro)





    //6 - Listar o historial de um passageiro (lista de viagens já realizadas)
    public HashMap<Integer,Bilhete> lerBilheteDePassageiro(String NomeFich,String idPassageiroFiltro) throws IOException {
        HashMap<Integer, Bilhete> dicBilhete = new HashMap<>();
        LocalDateTime data = null;
        LocalDateTime dataAtual = LocalDateTime.now();
        int idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao, horaAquisicao, minAquisicao, segAquisicao;
        String idPassageiro;
        double preco;
        BufferedReader f = new BufferedReader(new FileReader(new File(NomeFich)));
        String linha = f.readLine();
        while (linha != null) {
            String[] campos = linha.split(",");//dividir os campos pelo tab; o ficheiro está assim <código>\t<nome>\t<tipo>\t<nºUnidades>\t<nºUnidadesMínimo>\t<preço>\t<fornecedor>
            if(idPassageiroFiltro == null || idPassageiroFiltro.equals(campos[0])){ //se o filtro for passado a 0, vai buscar todos os registos. Se o filtro vier preenchido vai buscar apenas os bilhetes do voo pretendido
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
                data = LocalDateTime.of(anoViagem,mesViagem,diaViagem,horaViagem,minViagem,segViagem);
                if(data.isBefore(dataAtual)) {
                    Bilhete b = new Bilhete(idPassageiro, idRota, idVoo, anoViagem, mesViagem, diaViagem, horaViagem, minViagem, segViagem, anoAquisicao, mesAquisicao, diaAquisicao, horaAquisicao, minAquisicao, segAquisicao, preco);
                    dicBilhete.put(idVoo, b);
                }
            }
            linha = f.readLine();
        }
        f.close();
        return dicBilhete;
    }

    public void lerBilheteTxtPorPassageiro (String nomeFich, String idPassageiro) throws IOException {
        HashMap<Integer,Bilhete> dicBilhete = lerBilheteDePassageiro (nomeFich, idPassageiro);
        if(dicBilhete.isEmpty()) {
            System.out.println("O passageiro " + idPassageiro + " ainda não tem bilhetes.");
        }else{
            for (HashMap.Entry<Integer, Bilhete> bilhete : dicBilhete.entrySet()) {
                System.out.println(toStringB(dicBilhete.get(bilhete.getKey())));
            }
        }
    }

    private String toStringB(Bilhete value) {
        return "Id: " + value.getIdPassageiro() + "\nId Rota: " + value.getIdRota() + "\nId Voo: " + value.getIdVoo()+"\n";
    }


}
