package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.autenticador.Autenticador;
import com.ciandt.summit.bootcamp2022.dto.CorpoRequisicaoMusica;
import com.ciandt.summit.bootcamp2022.entity.PlaylistMusica;
import com.ciandt.summit.bootcamp2022.exceptions.AcessoNaoAutorizado;
import com.ciandt.summit.bootcamp2022.response.ResponseHandler;
import com.ciandt.summit.bootcamp2022.service.PlaylistMusicaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/playlists/")
public class MusicController {

    private static final Logger logger = LoggerFactory.getLogger(MusicaController.class);

    @Autowired
    Autenticador autenticador;

    @Autowired
    PlaylistMusicaService playlistMusicaService;

    @PostMapping("{playlistId}/musicas")
    public ResponseEntity<?> adicionarMusica(@RequestHeader("authorization") String token,
                                             @RequestHeader("usuario") String nomeUsuario,
                                             @PathVariable("playlistId") String playlistId,
                                             @RequestBody CorpoRequisicaoMusica corpoRequisicaoMusica) throws AcessoNaoAutorizado {

        autenticador.autenticar(nomeUsuario, token);

        logger.info("Requisição para adicionar lista a playlista. Usuário " + nomeUsuario);

        PlaylistMusica playlistMusica = playlistMusicaService.adicionarMusica(corpoRequisicaoMusica, playlistId);
        return ResponseHandler.generateResponse(HttpStatus.CREATED, playlistMusica);
    }


}