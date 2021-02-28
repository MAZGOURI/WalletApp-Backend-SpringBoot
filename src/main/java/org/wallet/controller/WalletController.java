package org.wallet.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wallet.entity.Wallet;
import org.wallet.service.ValidationErrorService;
import org.wallet.service.WalletService;

@RestController
@RequestMapping("/wallet")
@CrossOrigin
public class WalletController {

	@Autowired
	private WalletService walletService;
	
	@Autowired
	private ValidationErrorService validationErrorService;
	
	@GetMapping
	public ResponseEntity<?> getAll(){
		return new ResponseEntity<>(walletService.getAll(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		return new ResponseEntity<>(walletService.getById(id),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody Wallet wallet,BindingResult result){
		ResponseEntity<?> errors = validationErrorService.valide(result);
		if(errors != null) {
			return errors;
		}
		Wallet walletSaved = walletService.createOrUpdate(wallet);
		return new ResponseEntity<Wallet>(walletSaved,HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody Wallet wallet,BindingResult result){
		ResponseEntity<?> errors = validationErrorService.valide(result);
		if(errors != null) {
			return errors;
		}
		wallet.setId(id);
		Wallet walletSaved = walletService.createOrUpdate(wallet);
		return new ResponseEntity<Wallet>(walletSaved,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		walletService.delete(id);
		return new ResponseEntity<Wallet>(HttpStatus.OK);
	}
}
