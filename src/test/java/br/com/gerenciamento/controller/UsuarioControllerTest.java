package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static org.junit.Assert.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void cadastrarUsuario() throws Exception{
        Usuario usuario = new Usuario();
        usuario.setUser("Cristiano");
        usuario.setEmail("Cr7@gmail.com");
        usuario.setSenha("Real");
        usuarioController.cadastrar(usuario);
        assertEquals(usuario.getId(), usuarioRepository.findByEmail("Cr7@gmail.com").getId());

    }

    @Test
    public void login() throws Exception{
        Usuario usuario = new Usuario();
        usuario.setUser("FerGod");
        usuario.setEmail("DonaMorte@gmail.com");
        usuario.setSenha("csgo");

        usuarioController.cadastrar(usuario);

        BindingResult br = new BeanPropertyBindingResult(usuario, "usuario");
        HttpSession session =  Mockito.mock(HttpSession.class);

        ModelAndView mv = usuarioController.login(usuario, br, session);
        Map<String,Object> map = mv.getModel();
        Usuario usuarioLogado = (Usuario) map.get("usuario");

        assertNotNull(usuarioLogado);
    }

    @Test
    public void testPaginaCadastro() throws Exception {
        mockMvc.perform(get("/cadastro"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/cadastro"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    public void testPaginaInexistenteRetornaErro() throws Exception {
        mockMvc.perform(get("/pagina-inexistente"))
                .andExpect(status().isNotFound());
    }

}