package com.moura1001.webforum;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moura1001.webforum.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class WebForumAPITest {

	private static final String URL = "/forum/api/v1";

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void deveGanharTodosOsBadgesRelacionadosAAlgumaDeSuasInteracoesNoForum() throws Exception {
		// Cadastra usuário
		UsuarioDTO usuarioDTO = new UsuarioDTO("moura", "Genival Moura");
		String valueAsString = objectMapper.writeValueAsString(usuarioDTO);

		mockMvc.perform(MockMvcRequestBuilders.post(URL+"/usuarios")
						.contentType(MediaType.APPLICATION_JSON)
						.content(valueAsString))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.login").value(usuarioDTO.login()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(usuarioDTO.nome()));

		/** Interage no fórum **/
		// Adiciona tópico
		TopicoDTO topicoDTO = new TopicoDTO(usuarioDTO.login(), "Meu primeiro tópico", "Conteúdo...");
		valueAsString = objectMapper.writeValueAsString(topicoDTO);

		mockMvc.perform(MockMvcRequestBuilders.post(URL+"/topicos")
						.contentType(MediaType.APPLICATION_JSON)
						.content(valueAsString))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.login").value(usuarioDTO.login()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(usuarioDTO.nome()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value(topicoDTO.titulo()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.topicoId").value(1l));

		// Adiciona comentário
		ComentarioDTO comentarioDTO = new ComentarioDTO(usuarioDTO.login(), "Comentário 1");
		valueAsString = objectMapper.writeValueAsString(comentarioDTO);

		mockMvc.perform(MockMvcRequestBuilders.post(URL+"/topicos/1/adicionarComentario")
						.contentType(MediaType.APPLICATION_JSON)
						.content(valueAsString))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$").value("comentário adicionado com sucesso"));

		// Adiciona outro tópico
		topicoDTO = new TopicoDTO(usuarioDTO.login(), "Meu segundo tópico", "Conteúdo...");
		valueAsString = objectMapper.writeValueAsString(topicoDTO);

		mockMvc.perform(MockMvcRequestBuilders.post(URL+"/topicos")
						.contentType(MediaType.APPLICATION_JSON)
						.content(valueAsString))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.login").value(usuarioDTO.login()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(usuarioDTO.nome()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value(topicoDTO.titulo()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.topicoId").value(2l));

		// Gosta de um tópico
		mockMvc.perform(MockMvcRequestBuilders.put(URL+"/topicos/2/curtir?login="+usuarioDTO.login()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").value("tópico curtido com sucesso"));

		// Gosta de um comentário
		mockMvc.perform(MockMvcRequestBuilders.put(URL+"/topicos/1/comentarios/1/curtir?login="+usuarioDTO.login()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").value("comentário curtido com sucesso"));

		/** Analisa os achievements obtidos **/
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL + "/achievements/" + usuarioDTO.login()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		String json = result.getResponse().getContentAsString();
		AchievementViewDetail achievementResult = objectMapper.readValue(json, AchievementViewDetail.class);

		assertNotNull(achievementResult);
		assertEquals(4, achievementResult.achievements().size());
		Optional<AchievementView> achievement = achievementResult.achievements().stream()
				.filter(a -> "CREATION".equals(a.nome())).findAny();
		assertTrue(achievement.isPresent());
		assertEquals(11, achievement.get().quantidadePontos());

		achievement = achievementResult.achievements().stream()
				.filter(a -> "PARTICIPATION".equals(a.nome())).findAny();
		assertTrue(achievement.isPresent());
		assertEquals(4, achievement.get().quantidadePontos());

		achievement = achievementResult.achievements().stream()
				.filter(a -> "I CAN TALK".equals(a.nome())).findAny();
		assertTrue(achievement.isPresent());

		achievement = achievementResult.achievements().stream()
				.filter(a -> "LET ME ADD".equals(a.nome())).findAny();
		assertTrue(achievement.isPresent());
	}

}
