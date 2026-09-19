package org.iplacex.proyectos.discografia.discos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.iplacex.proyectos.discografia.Artistas.IArtistaRepository;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepository;

    @Autowired
    private IArtistaRepository artistaRepository;

    @PostMapping(
            value = "/disco",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object>
    HandlePostDiscoRequest(
            @RequestBody Disco disco){

        if(!artistaRepository.existsById(
                disco.idArtista)){

            return new ResponseEntity<>(
                    "El artista no existe",
                    HttpStatus.NOT_FOUND);
        }

        discoRepository.save(disco);

        return new ResponseEntity<>(
                disco,
                HttpStatus.CREATED);
    }

    @GetMapping(
            value = "/discos",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>>
    HandleGetDiscosRequest(){

        return new ResponseEntity<>(
                discoRepository.findAll(),
                HttpStatus.OK);
    }

    @GetMapping(
            value = "/disco/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object>
    HandleGetDiscoRequest(
            @PathVariable String id){

        Optional<Disco> disco =
                discoRepository.findById(id);

        if(disco.isPresent()){

            return new ResponseEntity<>(
                    disco.get(),
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(
                "Disco no encontrado",
                HttpStatus.NOT_FOUND);
    }

    @GetMapping(
            value = "/artista/{id}/discos",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>>
    HandleGetDiscosByArtistaRequest(
            @PathVariable String id){

        return new ResponseEntity<>(
                discoRepository.findDiscosByIdArtista(id),
                HttpStatus.OK);
    }
}
