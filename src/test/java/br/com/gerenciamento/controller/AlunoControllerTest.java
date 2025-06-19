package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Map;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {

    @Autowired
    private AlunoController alunoController;

    @Autowired
    private ServiceAluno serviceAluno;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void cadastrarAluno() throws Exception {
        mockMvc.perform(post("/InsertAlunos")
                        .param("nome", "Jorge")
                        .param("matricula", "123456")
                        .param("status", "ATIVO")
                        .param("turno", "NOTURNO")
                        .param("curso", "INFORMATICA"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    @Test
    public void cadastrarAlunoInvalido() throws Exception {
        mockMvc.perform(post("/InsertAlunos")
                        .param("nome", "Jorge")
                        .param("status", "ATIVO")
                        .param("turno", "NOTURNO")
                        .param("curso", "INFORMATICA"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/formAluno"))
                .andExpect(model().attributeExists("aluno"))
                .andExpect(model().hasErrors());

    }

    @Test
    public void pesquisarAluno() throws Exception{
        Aluno aluno = new Aluno();
        aluno.setNome("Jose Fernando");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("231324");
        serviceAluno.save(aluno);

        ModelAndView mv = alunoController.pesquisarAluno("Jose Fernando");
        Map<String,Object> map =  mv.getModel();
        List<Aluno> alunoProcurado = (List<Aluno>) map.get("ListaDeAlunos");

        assertEquals("231324", alunoProcurado.get(0).getMatricula());

    }

    @Test
    public void listarAlunosInativos() throws Exception{
        Aluno aluno = new Aluno();
        aluno.setNome("Alana");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ENFERMAGEM);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("453263");

        ModelAndView mv = alunoController.listaAlunosInativos();
        Map<String,Object> map =  mv.getModel();
        List<Aluno> alunosInativosInicial = (List<Aluno>) map.get("alunosInativos");
        int qtdAlunosInativosInicial = alunosInativosInicial.size();

        serviceAluno.save(aluno);

        ModelAndView mv2 = alunoController.listaAlunosInativos();
        Map<String,Object> map2 =  mv2.getModel();
        List<Aluno> alunosInativosFinal = (List<Aluno>) map2.get("alunosInativos");
        int qtdAlunosInativosFinal = alunosInativosFinal.size();

        assertEquals(qtdAlunosInativosInicial + 1, qtdAlunosInativosFinal);

    }

}

