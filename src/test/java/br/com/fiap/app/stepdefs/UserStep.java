package br.com.fiap.app.stepdefs;

import br.com.fiap.app.model.User;
import br.com.fiap.app.repository.UserRepository;
import br.com.fiap.app.request.LoginRequest;
import br.com.fiap.app.request.UserPostRequest;
import br.com.fiap.app.response.UserGetResponse;
import br.com.fiap.app.service.EmailService;
import br.com.fiap.app.service.JWTService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@Import({JavaMailSenderImpl.class,
        EmailService.class})
public class UserStep extends StepDefsDefault {

    private final String BASE_URL = "/api/v1/users";
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTService jwtService;
    private ResponseEntity<UserGetResponse> response;
    private HttpHeaders headers;

    @Given("que eu já tenha um usuário admin cadastrado")
    public void que_eu_ja_tenha_um_usuario_admin_cadastrado() {
        var user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setEmail("admin@admin.com");
        user.setRole("ADMIN");
        userRepository.save(user);

    }

    @And("que eu tenha feito login com o usuario admin")
    public void que_eu_tenha_feito_login_com_o_usuario_admin() {
        var token = jwtService.generateToken(new LoginRequest("admin", "admin"));

        headers = new HttpHeaders();
        headers.setBearerAuth(token);
    }

    @Given("que eu não tenha feito login com o usuario admin")
    public void que_eu_nao_tenha_feito_login_com_o_usuario_admin() {
        headers = new HttpHeaders();
    }

    @And("que o usuário {string} já exista no banco de dados")
    public void que_o_usuario_ja_exista_no_banco_de_dados(String username) {
        var userAlreadySaved = userRepository.findByUsername(username);

        Assertions.assertThat(userAlreadySaved).isPresent();
        Assertions.assertThat(userAlreadySaved.get().getUsername()).isEqualTo(username);
    }

    @When("eu faço uma requisição POST para o cadastro de usuário com nome {string} e email {string}")
    public void eu_faco_uma_requisicao_post_para_o_cadastro_de_usuario_com_nome_e_email(String name, String email) {
        UserPostRequest request = new UserPostRequest();
        request.setUsername(name);
        request.setPassword("123456");
        request.setEmail(email);
        request.setRole("USER");

        HttpEntity<UserPostRequest> entity = new HttpEntity<>(request, headers);
        response = restTemplate.postForEntity(BASE_URL, entity, UserGetResponse.class);
    }

    @Then("o status de resposta deve ser {int}")
    public void o_status_de_resposta_deve_ser(Integer statusCode) {
        assertThat(response.getStatusCode().value()).isEqualTo(statusCode);
    }

    @And("a resposta deve conter o nome {string} e o email {string}")
    public void a_resposta_deve_conter_o_nome_e_o_email(String name, String email) {
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo(name);
        assertThat(response.getBody().getEmail()).isEqualTo(email);
        assertThat(response.getBody().getRole()).isEqualTo("USER");
    }

}
