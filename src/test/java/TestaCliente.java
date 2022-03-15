import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;

public class TestaCliente {

    //criando um método para um get
    //criando uma string para guardar o endereço

    String enderecoAPICliente = "http://localhost:8080/";
    String enpointCliente = "cliente";
    String enpointRisco = "risco/{id}";
    String endpointApagaTodos = "/apagaTodos";
    String respostaVazia = "{}";

    // GET - Pegar todos os clientes
    @Test
    @DisplayName("Quando pegar todos os clientes sem cadastrar, então a lista deve estar vazia")
    public void QuandopegarTodososClientessemCadastrarentãoaListadeveEstarVazia() {
        deletaTodosClientes();


        given()
                .contentType(ContentType.JSON) //Dado que meu tipo de conteudo e json
        .when()
                .get(enderecoAPICliente)
        .then()//então
                .statusCode(200) //então quero verificar se meu statuscode e 200
                .assertThat().body(new IsEqual<>(respostaVazia)); //e quero veririfcar ou fazer uma asserção se meu body esta vazio


    }

    //POST
    @Test
    @DisplayName("Quando cadastrar um cliente, então ele deve estar disponível no resultado")
    public void QuandoCadastrarumClienteEntãoEleDeveEstarDisponívelnoResultado() {


        String clienteParaCadastrar = "{\n" +
                "  \"id\": 1001,\n" +
                "  \"idade\": 35,\n" +
                "  \"nome\": \"Michey Mouse\",\n" +
                "  \"risco\": 0\n" +
                "}";

        String respostaEsperadaCadastro = "{\"1001\":{\"nome\":\"Michey Mouse\",\"idade\":35,\"id\":1001,\"risco\":0}}";


        given()
                .contentType(ContentType.JSON)
                .body(clienteParaCadastrar)
        .when()
                .post(enderecoAPICliente + enpointCliente)
        .then()
                .statusCode(201)
                .assertThat().body(containsString(respostaEsperadaCadastro));


        }

    //PUT - endpoint cliente - Atualização de cadastro
    @Test
    @DisplayName("Quando atualizar um cliente já cadastrado, então ele deve estar disponível no resultado de forma atualizada")
    public void QuandoAtualizarUmClientejaCadastradoEntãoEleDeveEstarDisponívelnoResultadodeFormaAtualizada() {

        //Para cadastrar um cliente

        String clienteParaCadastrar = "{\n" +
                "  \"id\": 1001,\n" +
                "  \"idade\": 35,\n" +
                "  \"nome\": \"Michey Mouse\",\n" +
                "  \"risco\": 0\n" +
                "}";
        // Atualizacao de cliente

        String clienteAtualizado = "{\n" +
                "  \"id\": 1001,\n" +
                "  \"idade\": 23,\n" +
                "  \"nome\": \"Michelangelo Mouse\",\n" +
                "  \"risco\": 0\n" +
                "}";
        // Resposta esperada do servidor para atualizar
        String respostaEsperadaAtualizada = "{\"1001\":{\"nome\":\"Michelangelo Mouse\",\"idade\":23,\"id\":1001,\"risco\":0}}";

            // Cadastrando um cliente
        given()
                .contentType(ContentType.JSON)
                .body(clienteParaCadastrar)
                .when()
                .post(enderecoAPICliente + enpointCliente)
                .then()
                .statusCode(201);



        // Atualizando um cliente e comparando se o que foi atualizado de fato foi atualizado

        given()
                .contentType(ContentType.JSON)
                .body(clienteAtualizado)

        .when()
                .put(enderecoAPICliente + enpointCliente)
        .then()
                .statusCode(200)
                .assertThat().body(containsString(respostaEsperadaAtualizada));


    }

    //GET - endpoint de risco

    @Test
    @DisplayName("Quando pegar todos os clientes sem cadastrar, então a lista deve estar vazia")
    public void QuandoPegarTodosOsClientesSemCadastrarEntãoAListaDeveEstarVazia() {


   //     given()
     //           .contentType(ContentType.JSON) //Dado que meu tipo de conteudo e json
     //           .when()
     //           .get(enderecoAPICliente)
     //           .then()//então
     //           .statusCode(200) //então quero verificar se meu statuscode e 200
     //           .assertThat().body(new IsEqual<>(respostaVazia)); //e quero veririfcar ou fazer uma asserção se meu body esta vazio


    }

    // DELETE - Pegar todos os clientes
    @Test
    @DisplayName("Quando deletar um clientes, então ele deve ser removido com sucesso")
    public void QuandoDeletarUmClientesEntãoEleDeveSerRemovidoComSucesso(){

        // Cliente para ser cadastrado
        String clienteParaCadastrar = "{\n" +
                "  \"id\": 1001,\n" +
                "  \"idade\": 23,\n" +
                "  \"nome\": \"Michelangelo Mouse\",\n" +
                "  \"risco\": 0\n" +
                "}";

        String respostaEsperadaDelete = "CLIENTE REMOVIDO: { NOME: Michelangelo Mouse, IDADE: 23, ID: 1001 }";

        given()
                .contentType(ContentType.JSON)
                .body(clienteParaCadastrar)
                .when()
                .post(enderecoAPICliente + enpointCliente)
                .then()
                .statusCode(201);


        //Realizando o delete do cliente cadastrado

        given()
                .contentType(ContentType.JSON)
        .when()
                //fazendo um delete no endpoint concatenando com o id
                .delete(enderecoAPICliente + enpointCliente+ "/1001")
        .then()
                //verificando o status code e se a remocao foi bem sucedida
                .statusCode(200)
                .body(new IsEqual<>(respostaEsperadaDelete));
    }

    //DELETE - Apaga todos os clientes
    //Método de apoio

    public void deletaTodosClientes(){


        given()
                .contentType(ContentType.JSON)
        .when()
                //fazendo um delete no endpoint concatenando com o id
                .delete(enderecoAPICliente + enpointCliente+ endpointApagaTodos)
        .then()
                //verificando o status code e se a remocao foi bem sucedida
                .statusCode(200)
                .body(new IsEqual<>(respostaVazia));


    }


}
