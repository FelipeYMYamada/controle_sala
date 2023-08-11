package com.controle_sala.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.controle_sala.exception.ResourceNotFoundException;
import com.controle_sala.model.Room;
import com.controle_sala.repository.RoomRepository;

import jakarta.validation.Valid;

@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/room")
public class RoomController {

    final RoomRepository roomRepository;

    @Autowired
    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomId(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Room room = roomRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Sala com Id " + id + " não encontrado!"));
        return ResponseEntity.ok().body(room);
    }

    @PostMapping
    public Room create(@Valid @RequestBody Room room) {
        return roomRepository.save(room);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable(value = "id") Long id,
                                           @Valid @RequestBody Room room) throws ResourceNotFoundException {
        Room _room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sala com id " + id + " não encontrada!"));
        room.setId(id);
        roomRepository.save(room);
        return ResponseEntity.ok().body(room);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> delete(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Room room = roomRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Sala com id " + id + " não encontrada!"));
        roomRepository.delete(room);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
