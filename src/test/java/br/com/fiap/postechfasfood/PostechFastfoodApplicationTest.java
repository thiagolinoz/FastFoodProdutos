package br.com.fiap.postechfasfood;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PostechFastfoodApplication - Testes da Aplicação Principal")
class PostechFastfoodApplicationTest {

    @Test
    @DisplayName("Deve verificar que a aplicação está configurada como SpringBootApplication")
    void deveVerificarConfiguracaoSpringBootApplication() {
        assertTrue(PostechFastfoodApplication.class.isAnnotationPresent(
                org.springframework.boot.autoconfigure.SpringBootApplication.class));
    }

    @Test
    @DisplayName("Deve ter o método main para inicialização da aplicação")
    void deveTerMetodoMainParaInicializacao() throws NoSuchMethodException {
        assertNotNull(PostechFastfoodApplication.class.getMethod("main", String[].class));
    }

    @Test
    @DisplayName("Deve verificar que o método main é público e estático")
    void deveVerificarQueMetodoMainEPublicoEStatico() throws NoSuchMethodException {
        var mainMethod = PostechFastfoodApplication.class.getMethod("main", String[].class);
        
        assertTrue(java.lang.reflect.Modifier.isPublic(mainMethod.getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isStatic(mainMethod.getModifiers()));
    }

    @Test
    @DisplayName("Deve verificar que a classe principal está no package correto")
    void deveVerificarQueClassePrincipalEstaNoPackageCorreto() {
        assertEquals("br.com.fiap.postechfasfood", 
                     PostechFastfoodApplication.class.getPackageName());
    }

    @Test
    @DisplayName("Deve ter construtor público padrão")
    void deveTerConstrutorPublicoPadrao() throws NoSuchMethodException {
        var constructor = PostechFastfoodApplication.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPublic(constructor.getModifiers()));
    }

    @Test
    @DisplayName("Deve conseguir instanciar a classe principal")
    void deveConseguirInstanciarClassePrincipal() {
        assertDoesNotThrow(PostechFastfoodApplication::new);
    }

    @Test
    @DisplayName("Deve verificar que o método main aceita argumentos String array")
    void deveVerificarQueMetodoMainAceitaArgumentosStringArray() throws NoSuchMethodException {
        var mainMethod = PostechFastfoodApplication.class.getMethod("main", String[].class);
        var parameterTypes = mainMethod.getParameterTypes();
        
        assertEquals(1, parameterTypes.length);
        assertEquals(String[].class, parameterTypes[0]);
    }

    @Test
    @DisplayName("Deve verificar que o método main retorna void")
    void deveVerificarQueMetodoMainRetornaVoid() throws NoSuchMethodException {
        var mainMethod = PostechFastfoodApplication.class.getMethod("main", String[].class);
        assertEquals(void.class, mainMethod.getReturnType());
    }

    @Test
    @DisplayName("Deve verificar que a aplicação tem a annotation SpringBootApplication")
    void deveVerificarQueAplicacaoTemAnnotationSpringBootApplication() {
        var annotation = PostechFastfoodApplication.class
                .getAnnotation(org.springframework.boot.autoconfigure.SpringBootApplication.class);
        
        assertNotNull(annotation);
    }
}
