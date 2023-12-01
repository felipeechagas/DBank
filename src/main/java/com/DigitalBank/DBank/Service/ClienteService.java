package com.DigitalBank.DBank.Service;

import com.DigitalBank.DBank.Repository.ClienteRepository;
import com.DigitalBank.DBank.exception.BadRequestException;
import com.DigitalBank.DBank.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ClienteService {

  @Autowired
  private ClienteRepository clienteRepository;

  public Cliente findByIdOrThrowBadRequestException(long id) {
    return clienteRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("Cliente não encontrado"));
  }

  public List<Cliente> listarClientes(){
    return clienteRepository.findAll();
  }

  public Cliente save(Cliente cliente) throws Exception {
    if (clienteRepository.existsByCpf(cliente.getCpf())) {
      throw new BadRequestException("Cliente com cpf " + cliente.getCpf() + " já existe.");
    }
    return clienteRepository.save(cliente);
  }

  public Cliente updateCliente(Long id, Cliente clienteAtualizado) {
    Cliente clienteExistente = clienteRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("Cliente não encontrado com o ID: " + id));

      clienteExistente.setNome(clienteAtualizado.getNome());
      clienteExistente.setCpf(clienteAtualizado.getCpf());
      clienteExistente.setGenero(clienteAtualizado.getGenero());
      clienteExistente.setDataNascimento(clienteAtualizado.getDataNascimento());
      clienteExistente.setContato(clienteAtualizado.getContato());

      return clienteRepository.save(clienteExistente);
  }

  public void excluirCliente(Long id) {
    clienteRepository.deleteById(id);
  }
}
