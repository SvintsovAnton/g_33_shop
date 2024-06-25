package de.ait_tr.g_33_shop.controller;

import de.ait_tr.g_33_shop.domain.dto.ProductDto;
import de.ait_tr.g_33_shop.domain.entity.User;
import de.ait_tr.g_33_shop.repository.RoleRepository;
import de.ait_tr.g_33_shop.repository.UserRepository;
import de.ait_tr.g_33_shop.security.sec_dto.TokenResponseDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import de.ait_tr.g_33_shop.domain.entity.Role;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductControllerTest {
@LocalServerPort
private int port;

@Autowired
private UserRepository userRepository;
//для обращения к роли репозитория
    @Autowired
    private RoleRepository roleRepository;

private TestRestTemplate template;
private HttpHeaders headers;
private ProductDto testProduct;



private String adminAccessToken;
private String userAccessToken;




private final String TEST_PRODUCT_TITLE = "Test product";
private final BigDecimal TEST_PRODUCT_PRICE = new BigDecimal(777);
private final String TEST_ADMIN_NAME = "Test Admin";
private final String TEST_USER_NAME = "Test User";
private final String TEST_PASSWORD = "Test password";
private final String ROLE_ADMIN_TITLE = "ROLE_ADMIN";
private final String ROLE_USER_TITLE = "ROLE_USER";


//Bearer 989gf89d8fg9fd8f9g8f9gf8d9gf8gf9gf8gdfg8g=ggrgt=9332frf
private final String BEARER_PREFIX= "Bearer ";
private final String AUTH_HEADER_NAME = "Authorization";

private final String ID ="id";

private final String URL_PREFIX = "http://localhost:";
private final String AUTH_RESOURCE_NAME="/auth";
private final String PRODUCTS_RESOURCE_NAME = "/products";
private final String LOGIN_ENDPOINT = "/login";
private String ALL_ENDPOINT = "/all";
private String ID_ENDPOINT = "?id=";


private Long testId;



@BeforeEach
    public void setUp(){
template = new TestRestTemplate();
headers= new HttpHeaders();

testProduct = new ProductDto();
testProduct.setTitle(TEST_PRODUCT_TITLE);
testProduct.setPrice(TEST_PRODUCT_PRICE);
//для сохранения пользователей в БД
    BCryptPasswordEncoder encoder =null;
    Role roleAdmin;
    Role roleUser =null;
    // создаем админа и юзера
    User admin = userRepository.findByUsername(TEST_ADMIN_NAME).orElse(null);
    User user = userRepository.findByUsername(TEST_USER_NAME).orElse(null);
//проверка того созданы ли админ и юзер в БД или их надо создавать
if (admin==null){
    //для создания нового адимна необходимо инициализировать энкодер
    encoder = new BCryptPasswordEncoder();
    //доставть роли из базы для адимна
    roleAdmin = roleRepository.findByTitle(ROLE_ADMIN_TITLE).orElseThrow(
            ()->new RuntimeException("Role Admin is missing in the database")
    );
    roleUser = roleRepository.findByTitle(ROLE_USER_TITLE).orElseThrow(
            ()->new RuntimeException("Role User is missing in the database")
    );
    admin = new User();
    admin.setUsername(TEST_ADMIN_NAME);
    admin.setPassword(encoder.encode(TEST_PASSWORD));
    admin.setRoles(Set.of(roleAdmin,roleUser));

    userRepository.save(admin);

}
if (user==null){
    encoder=encoder==null?new BCryptPasswordEncoder():encoder;
    //доставть роли из базы для адимна

    roleUser = roleUser == null ? roleRepository.findByTitle(ROLE_USER_TITLE).orElseThrow(
            () -> new RuntimeException("Role User is missing in the database")
    ) : roleUser;
    user = new User();
    user.setUsername(TEST_USER_NAME);
    user.setPassword(encoder.encode(TEST_PASSWORD));
    user.setRoles(Set.of(roleUser));

    userRepository.save(user);

}


// залогиниться
    //подготовка объекта юзера и админа для передачи на контроллер, для этого нужен сырой пароль и обнулить роли потому что роли определяет бэкеэнд
    admin.setPassword(TEST_PASSWORD);
admin.setRoles(null);
user.setPassword(TEST_PASSWORD);
user.setRoles(null);
//мы должны отправить пост запрос на POST: http://localhost:8080/auth>login , для этого записываем еонстанты пути
    //аксесс токена для админа
String url = URL_PREFIX+port+AUTH_RESOURCE_NAME+LOGIN_ENDPOINT; //путь для запроса
 HttpEntity<User> request = new HttpEntity<>(admin,headers); //объект запроса
 ResponseEntity<TokenResponseDto> response= template.exchange(url, HttpMethod.POST,request, TokenResponseDto.class); //переменная ответа


    assertTrue(response.hasBody(),"Auth response body is empty");
   adminAccessToken= BEARER_PREFIX + response.getBody().getAccessToken();
//аксесс токена для юзера
    request = new HttpEntity<>(user,headers); //объект запроса
    response= template.exchange(url, HttpMethod.POST,request, TokenResponseDto.class); //переменная ответа

    assertTrue(response.hasBody(),"Auth response body is empty");
    userAccessToken= BEARER_PREFIX + response.getBody().getAccessToken();




}


    //тест для получения продуктов без авторизации с позитивным результатом
@Test
    public void positiveGettingAllProductsWithoutAuthorization(){

    String url = URL_PREFIX+port+PRODUCTS_RESOURCE_NAME+ALL_ENDPOINT;
    HttpEntity<Void> request = new HttpEntity<>(headers);

   ResponseEntity<ProductDto[]> response= template.exchange(url,HttpMethod.GET,request, ProductDto[].class);
   assertEquals(HttpStatus.OK,response.getStatusCode(),"Response has unexpected status");
   assertTrue(response.hasBody(),"Response doesn´t have a body");

}


@Test
public void negativeSavingProductWithoutAuthorization(){
    String url = URL_PREFIX+port+PRODUCTS_RESOURCE_NAME;
    HttpEntity<ProductDto> request = new HttpEntity<>(testProduct,headers);
    ResponseEntity<ProductDto> response = template
            .exchange(url,HttpMethod.POST,request, ProductDto.class);
    assertEquals(HttpStatus.FORBIDDEN,response.getStatusCode(),"Response has unexpected status");
    assertFalse(response.hasBody(),"Response has unexpected body");

}


    @Test
    public void negativeSavingProductWithUserAuthorization() {
        String url = URL_PREFIX+port+PRODUCTS_RESOURCE_NAME;
        headers.put(AUTH_HEADER_NAME,List.of(userAccessToken));
        HttpEntity<ProductDto> request = new HttpEntity<>(testProduct, headers);
        ResponseEntity<ProductDto> response = template.exchange(url,HttpMethod.POST,request, ProductDto.class);
        assertEquals(HttpStatus.FORBIDDEN,response.getStatusCode(),"Response has unexpected status");
        assertFalse(response.hasBody(),"Response has unexpected body");
    }


    @Test
    @Order(1)
    public void positiveSavingProductWithAdminAuthorization() {
       String url = URL_PREFIX+port+PRODUCTS_RESOURCE_NAME;
       headers.put(AUTH_HEADER_NAME,List.of(adminAccessToken));
       HttpEntity<ProductDto> request = new HttpEntity<>(testProduct,headers);
       ResponseEntity<ProductDto> response = template.exchange(url,HttpMethod.POST,request, ProductDto.class);
       testId = response.getBody().getId();
       if (testId!=null){
       testProduct.setId(testId);}
       assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(testProduct, response.getBody());

    }

    @Test
    @Order(2)
    public void negativeGettingProductByIdWithoutAuthorization() {
        String url = URL_PREFIX+port+PRODUCTS_RESOURCE_NAME+ID_ENDPOINT+testId.toString();
        Map<String,Long> requestBody = new HashMap<>();
        requestBody.put(ID,testId);
        HttpEntity<Map<String,Long>> request = new HttpEntity<>(requestBody,headers);
        ResponseEntity<ProductDto> response= template.exchange(url,HttpMethod.GET,request, ProductDto.class);
        assertEquals(HttpStatus.FORBIDDEN,response.getStatusCode(),"Response has unexpected status");
        assertFalse(response.hasBody(),"Response has unexpected body");

    }

    @Test
    @Order(3)
    public void negativeGettingProductByIdWithBasicAuthorization() {
        String url = URL_PREFIX+port+PRODUCTS_RESOURCE_NAME+ID_ENDPOINT+testId.toString();;
        Map<String,Long> requestBody = new HashMap<>();
        requestBody.put(ID,testId);
        HttpEntity<Map<String,Long>> request = new HttpEntity<>(requestBody,headers);
        ResponseEntity<ProductDto> response = template
            .withBasicAuth(TEST_USER_NAME, TEST_PASSWORD)
                .exchange(url, HttpMethod.POST, request, ProductDto.class);
        assertEquals(HttpStatus.FORBIDDEN,response.getStatusCode(),"Response has unexpected status");
        assertFalse(response.hasBody(),"Response has unexpected body");

    }

    @Test
    @Order(4)
    public void negativeGettingProductByIdWithIncorrectToken() {
        String url = URL_PREFIX+port+PRODUCTS_RESOURCE_NAME+ID_ENDPOINT+testId.toString();;
        Map<String,Long> requestBody = new HashMap<>();
        requestBody.put(ID,testId);
        headers.put(AUTH_HEADER_NAME,List.of(userAccessToken+"4544"));
        HttpEntity<Map<String,Long>> request = new HttpEntity<>(requestBody,headers);
        ResponseEntity<ProductDto> response = template.exchange(url,HttpMethod.GET,request, ProductDto.class);
        assertEquals(HttpStatus.FORBIDDEN,response.getStatusCode());
        assertFalse(response.hasBody(),"Response has unexpected body");
    }

    @Test
    @Order(5)
    public void positiveGettingProductByIdWithCorrectToken() {
        String url = URL_PREFIX+port+PRODUCTS_RESOURCE_NAME+ID_ENDPOINT+testId.toString();;
        Map<String,Long> requestBody = new HashMap<>();
        requestBody.put(ID,testId);
        headers.put(AUTH_HEADER_NAME,List.of(adminAccessToken));
        HttpEntity<Map<String,Long>> request = new HttpEntity<>(requestBody,headers);
        ResponseEntity<ProductDto> response = template.exchange(url,HttpMethod.GET,request, ProductDto.class);
        testId = response.getBody().getId();
        testProduct.setId(testId);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        response.getBody().setPrice( (response.getBody().getPrice().stripTrailingZeros()));
        assertEquals(testProduct, response.getBody());

        //удалить тестовый продукт из БД

        ResponseEntity<Void> deleteResponse = template.exchange(url, HttpMethod.DELETE, request, Void.class);
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
    }


}