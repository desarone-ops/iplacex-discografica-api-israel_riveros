package org.iplacex.proyectos.discografia.Artistas;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    @Autowired
    private IArtistaRepository artistaRepository;

    @PostMapping(
            value = "/artista",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleInsertArtistaRequest(
            @RequestBody Artista artista) {

        artistaRepository.save(artista);

        return new ResponseEntity<>(
                artista,
                HttpStatus.CREATED);
    }

    @GetMapping(
            value = "/artistas",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artista>> HandleGetArtistasRequest() {

        return new ResponseEntity<>(
                artistaRepository.findAll(),
                HttpStatus.OK);
    }

    @GetMapping(
            value = "/artista/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleGetArtistaRequest(
            @PathVariable String id) {

        Optional<Artista> artista =
                artistaRepository.findById(id);

        if (artista.isPresent()) {
            return new ResponseEntity<>(
                    artista.get(),
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(
                "Artista no encontrado",
                HttpStatus.NOT_FOUND);
    }

    @PutMapping(
            value = "/artista/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleUpdateArtistaRequest(
            @PathVariable String id,
            @RequestBody Artista artista) {

        if (!artistaRepository.existsById(id)) {
            return new ResponseEntity<>(
                    "Artista no encontrado",
                    HttpStatus.NOT_FOUND);
        }

        artista._id = id;

        artistaRepository.save(artista);

        return new ResponseEntity<>(
                artista,
                HttpStatus.OK);
    }

    @DeleteMapping(
            value = "/artista/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleDeleteArtistaRequest(
            @PathVariable String id) {

        if (!artistaRepository.existsById(id)) {
            return new ResponseEntity<>(
                    "Artista no encontrado",
                    HttpStatus.NOT_FOUND);
        }

        artistaRepository.deleteById(id);

        return new ResponseEntity<>(
                "Artista eliminado",
                HttpStatus.OK);
    }
}
