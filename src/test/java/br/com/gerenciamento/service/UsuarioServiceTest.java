package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.model.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void emailDuplicado() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setUser("Epitacio");
        usuario.setSenha("123");
        usuario.setEmail("taco@gmail.com");

        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioDuplicado = new Usuario();
        usuarioDuplicado.setUser("Taquinho");
        usuarioDuplicado.setSenha("321");
        usuarioDuplicado.setEmail("taco@gmail.com");

        assertThrows(Exception.class, () -> {
            this.serviceUsuario.salvarUsuario(usuarioDuplicado);
        });

    }

    @Test
    public void usuarioSemSenha() throws Exception {

        Usuario usuario = new Usuario();
        usuario.setUser("Neymar");
        usuario.setEmail("Ney@gmail.com");

        assertThrows(Exception.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario);
        });

    }

    @Test
    public void logarUsuarioInexistente() {
        assertNull(this.serviceUsuario.loginUser("Messi","123456"));
    }

    @Test
    public void salvarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("Fallen");
        usuario.setSenha("Verdadeiro");
        usuario.setEmail("GabrielFallen@gmail.com");

        assertDoesNotThrow(() ->{
            this.serviceUsuario.salvarUsuario(usuario);
        });

    }



}
