package br.com.tech4me.vendaws.view.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tech4me.vendaws.service.VendaService;
import br.com.tech4me.vendaws.shared.VendaDto;
import br.com.tech4me.vendaws.view.model.VendaModeloRequest;

@RestController
@RequestMapping(value = "/api/venda")
public class VendaController {
    @Autowired
    private VendaService service;

    @PostMapping
    public ResponseEntity<VendaDto> criarVenda(@RequestBody @Valid VendaModeloRequest vendaRequest) {
        Optional<VendaDto> vendaDto = service.criarVenda(vendaRequest);

        if (vendaDto.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);  
        }

        return new ResponseEntity<>(vendaDto.get(), HttpStatus.CREATED);
    }   

    @GetMapping
    public ResponseEntity<List<VendaDto>> obterTodasVendas() {
        return new ResponseEntity<>(service.obterTodasVendas(), HttpStatus.ACCEPTED); 
    }

    @GetMapping(value = "/{dtInicial}/{dtFinal}")
    public ResponseEntity<List<VendaDto>> obterVendasPorPeriodo(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Calendar dtInicial, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Calendar dtFinal) {
        return new ResponseEntity<>(service.obterVendasPorPeriodo(dtInicial, dtFinal), HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<VendaDto> obterVendaPorId(@PathVariable String id) {
        Optional<VendaDto> venda = service.obterVendaPorId(id);

        if(venda.isPresent()) {
            return new ResponseEntity<>(
                new ModelMapper().map(venda.get(), VendaDto.class), 
                HttpStatus.OK
            );
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<VendaDto> atualizarVendaPorId(@PathVariable String id, @RequestBody VendaDto venda) {
        Optional<VendaDto> vendaAtualizar = service.atualizarVendaPorId(id, venda);

        if (vendaAtualizar.isPresent()) {
            return new ResponseEntity<>(vendaAtualizar.get(), HttpStatus.OK);
        }
        
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removerVenda(@PathVariable String id) {
        service.removerVenda(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }     
}
