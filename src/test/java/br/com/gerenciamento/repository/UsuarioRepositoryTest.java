package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void usuarioInvalido() {

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUser("a1");                  // User tem que possuir mais que 3 caracteres
        usuario.setSenha("123");
        usuario.setEmail("alex@gmail.com");

        assertThrows(Exception.class, () -> {
            this.usuarioRepository.save(usuario);
        });
    }

    @Test
    public void acharUsuarioPorEmail() {

        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setUser("Epitacio");                  // User tem que possuir mais que 3 caracteres
        usuario.setSenha("123");
        usuario.setEmail("taco@gmail.com");
        this.usuarioRepository.save(usuario);

        Usuario usuarioPorEmail = this.usuarioRepository.findByEmail("taco@gmail.com");

        assertNotNull(usuarioPorEmail);
        assertEquals(usuario.getUser(), usuarioPorEmail.getUser());
    }

    @Test
    public void buscarUsuario() {

        Usuario usuario = new Usuario();
        usuario.setId(32L);
        usuario.setUser("User");
        usuario.setSenha("senha");
        usuario.setEmail("usuario@gmail.com");
        this.usuarioRepository.save(usuario);

        Usuario usuarioLogin = usuarioRepository.buscarLogin("User", "senha");

        assertNotNull(usuario);
        assertEquals(usuario.getUser(), usuarioLogin.getUser());
        assertEquals(usuario.getSenha(), usuarioLogin.getSenha());
    }

    @Test
    public void buscarUsuarioInexistente() {

        Usuario usuario = usuarioRepository.buscarLogin("alex", "123");
        assertNull(usuario);

    }
}