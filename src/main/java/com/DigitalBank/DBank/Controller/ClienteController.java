package com.DigitalBank.DBank.Controller;

import com.DigitalBank.DBank.Service.ClienteService;
import com.DigitalBank.DBank.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

  @Autowired
  private ClienteService clienteService;

  @GetMapping("/list")
  public ResponseEntity<List<Cliente>> listarClientes() {
    List<Cliente> clientes = clienteService.listarClientes();
    return new ResponseEntity<>(clientes, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> findByID(@PathVariable Long id) {
      Cliente clientes = clienteService.findByIdOrThrowBadRequestException(id);
      return new ResponseEntity<>(clientes, HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<Cliente> save(@RequestBody Cliente cliente) throws Exception {
    Cliente novoCliente = clienteService.save(cliente);
    return new ResponseEntity<>(cliente, HttpStatus.CREATED);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
    Cliente clienteAtualizado = clienteService.updateCliente(id, cliente);
    if (clienteAtualizado != null) {
      return new ResponseEntity<>(clienteAtualizado, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> excluirCliente(@PathVariable long id) {
    clienteService.excluirCliente(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
