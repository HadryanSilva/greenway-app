package br.com.fiap.app.stepdefs;

import br.com.fiap.app.model.User;
import br.com.fiap.app.model.WasteType;
import br.com.fiap.app.repository.CollectRepository;
import br.com.fiap.app.repository.UserRepository;
import br.com.fiap.app.request.CollectPostRequest;
import br.com.fiap.app.request.LoginRequest;
import br.com.fiap.app.response.CollectPostResponse;
import br.com.fiap.app.service.EmailService;
import br.com.fiap.app.service.JWTService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class CollectStep extends StepDefsDefault {

    private final String BASE_URL = "/api/v1/collects";
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private CollectRepository collectRepository;

    private ResponseEntity<CollectPostResponse> response;
    private HttpHeaders headers;
    private User userSaved;

    @Given("que eu tenha feito o cadastro do usuário {string}")
    public void que_eu_ja_tenha_um_usuario_cadastrado(String username) {
        var user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode("user"));
        user.setEmail("user@user.com");
        user.setRole("USER");
        userSaved = userRepository.save(user);
    }

    @Given("que o usuário {string} já esteja cadastrado")
    public void que_o_usuario_ja_exista_no_banco_de_dados(String username) {
        userSaved = userRepository.findByUsername(username).get();

        Assertions.assertThat(userSaved).isNotNull();
        Assertions.assertThat(userSaved.getUsername()).isEqualTo(username);
    }
    @And("que eu tenha feito login com o usuario {string}")
    public void que_eu_tenha_feito_login_com_o_usuario(String username) {
        var token = jwtService.generateToken(new LoginRequest(username, "user"));

        headers = new HttpHeaders();
        headers.setBearerAuth(token);
    }

    @And("que eu não tenha feito login")
    public void que_eu_nao_tenha_feito_login_com_o_usuario() {
        headers = new HttpHeaders();
    }

    @And("que a coleta já tenha sido agendada")
    public void que_a_coleta_ja_tenha_sido_agendada() {
        var collectFound = collectRepository.findById(1l).get();

        Assertions.assertThat(collectFound).isNotNull();
        Assertions.assertThat(collectFound.getWasteType()).isEqualTo(WasteType.RECYCLABLE);
    }

    @When("eu faço uma requisição {string} para o endpoint de coleta")
    public void eu_faco_uma_requisicao_post_para_o_cadastro_de_coleta(String method) {
        var request = new CollectPostRequest();
        request.setUserId(userSaved.getId());
        request.setWasteType(WasteType.RECYCLABLE.name());

        HttpEntity<CollectPostRequest> entity = new HttpEntity<>(request, headers);
        if (method.equals("POST")) {
            response = restTemplate.postForEntity(BASE_URL, entity, CollectPostResponse.class);
        } else if (method.equals("DELETE")) {
            response = restTemplate.exchange(BASE_URL + "/1", HttpMethod.DELETE, entity, CollectPostResponse.class);
        }
    }

    @Then("o status de resposta da requisição de coleta deve ser {int}")
    public void o_status_de_resposta_deve_ser(Integer statusCode) {
        assertThat(response.getStatusCode().value()).isEqualTo(statusCode);
    }

}
