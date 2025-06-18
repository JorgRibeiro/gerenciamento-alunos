package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void QuantAlunosAtivos() {
        Aluno aluno1 = new Aluno();
        aluno1.setId(1L);
        aluno1.setNome("Jose Mateus");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("123456");
        this.alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Vinicius");
        aluno2.setTurno(Turno.MATUTINO);
        aluno2.setCurso(Curso.BIOMEDICINA);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("4444");
        this.alunoRepository.save(aluno2);

        assertEquals(2, alunoRepository.findByStatusAtivo().size());
    }

    @Test
    public void QuantAlunosInativos() {

        Aluno aluno_inativo = new Aluno();
        aluno_inativo.setId(111L);
        aluno_inativo.setNome("Ribeiro");
        aluno_inativo.setTurno(Turno.MATUTINO);
        aluno_inativo.setCurso(Curso.DIREITO);
        aluno_inativo.setStatus(Status.INATIVO);
        aluno_inativo.setMatricula("11111");
        this.alunoRepository.save(aluno_inativo);

        assertEquals(1, alunoRepository.findByStatusInativo().size());

    }

    @Test
    public void AlunoComNomePequeno() {

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Alan");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");

        assertThrows(Exception.class, () -> {
            this.alunoRepository.save(aluno);
        });
    }

    @Test
    public void ProcurarAlunosComMesmoNome() {

        Aluno aluno = new Aluno();
        aluno.setId(12L);
        aluno.setNome("Americo Luis");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("091232");
        this.alunoRepository.save(aluno);

        Aluno aluno1 = new Aluno();
        aluno1.setId(13L);
        aluno1.setNome("Americo Jose");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.INFORMATICA);
        aluno1.setStatus(Status.INATIVO);
        aluno1.setMatricula("091232");
        this.alunoRepository.save(aluno1);
        
        assertEquals(2, alunoRepository.findByNomeContainingIgnoreCase("americo").size());

    }

    @Test
    public void SalvarAlunosSemMatricula() {

        Aluno aluno = new Aluno();
        aluno.setId(5L);
        aluno.setNome("Jorge");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);

        assertThrows(Exception.class, () -> {
            this.alunoRepository.save(aluno);
        });
    }

}