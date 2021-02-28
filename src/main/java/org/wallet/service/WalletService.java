package org.wallet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wallet.entity.Wallet;
import org.wallet.exception.WalletException;
import org.wallet.repository.WalletRepository;

@Service
public class WalletService {
	
	@Autowired
	private WalletRepository walletRepo;
	
	/**
	 * method for displaying a list of wallet
	 *
	 * @return List of Wallet
	 */
	public List<Wallet> getAll(){
		return walletRepo.findAllByOrderByPriority();
	}
	
	/**
	 * method for displaying a wallet of an identifier
	 *
	 * @param id - ID of Wallet
	 * @return a wallet
	 */
	public Wallet getById(Long id) {
		Optional<Wallet> wallet = walletRepo.findById(id);
		if(wallet.isPresent()) {
			return wallet.get();
		}
		throw new WalletException("Wallet with " + id + " does not exist!");
	}
	
	/**
	 * method for creating or updating a wallet
	 *
	 * @param wallet - our wallet
	 * @return a wallet
	 */
	public Wallet createOrUpdate(Wallet wallet) {
		walletRepo.save(wallet);
		return wallet;
	}
	
	/**
	 * method for deleting a wallet
	 *
	 * @param id - ID of Wallet
	 * @return a boolean
	 */
	public boolean delete(Long id) {
		Optional<Wallet> wallet = walletRepo.findById(id);
		if(wallet.isPresent()) {
			walletRepo.delete(wallet.get());
			return true;
		}
		throw new WalletException("Wallet with " + id + " does not exist!");
	}
}
