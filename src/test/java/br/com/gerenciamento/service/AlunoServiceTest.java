package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoServiceTest {

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void getById() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(1L);
        Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
    }

    @Test
    public void salvarSemNome() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
                this.serviceAluno.save(aluno);});
    }

    @Test
    public void statusAtualizado() {

        Aluno aluno = new Aluno();
        aluno.setNome("Ribeiro");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.DIREITO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123333");
        this.serviceAluno.save(aluno);

        Long AlunoID = aluno.getId();       // O id é gerado automativamente pelo servoce, então não precisamos setar apenas pegar o valor gerado

        Aluno alunoRetorno = this.serviceAluno.getById(AlunoID);
        Assert.assertTrue(alunoRetorno.getStatus().equals(Status.ATIVO));

        aluno.setStatus(Status.INATIVO);
        this.serviceAluno.save(aluno);

        alunoRetorno = this.serviceAluno.getById(AlunoID);
        Assert.assertTrue(alunoRetorno.getStatus().equals(Status.INATIVO));
    }

    @Test
    public void deletarAluno() {

        Aluno aluno = new Aluno();
        aluno.setNome("Mateus");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.DIREITO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("111222");
        this.serviceAluno.save(aluno);

        Long AlunoID = aluno.getId();

        Assert.assertEquals(this.serviceAluno.getById(AlunoID).getNome(),aluno.getNome());

        this.serviceAluno.deleteById(AlunoID);

        assertThrows(Exception.class, () -> {
            this.serviceAluno.getById(AlunoID);
        });

    }

    @Test
    public void alunoComNomeGrande() {

        Aluno aluno = new Aluno();
        aluno.setNome("Pedro de Alcântara Francisco Antônio João Carlos Xavier de Paula Miguel Rafael Joaquim José Gonzaga Pascoal Cipriano Serafim de Bragança e Bourbon");
        // Nome completo de Dom Pedro I
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.DIREITO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("111222");

        assertThrows(Exception.class, () -> {
            this.serviceAluno.save(aluno);
        });

    }

    @Test
    public void procurarAlunoInexistente() {

        assertThrows(Exception.class, () -> {
            this.serviceAluno.getById(312L);
        });

    }

}