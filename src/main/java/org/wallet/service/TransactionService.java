package org.wallet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wallet.entity.Transaction;
import org.wallet.entity.Wallet;
import org.wallet.exception.WalletException;
import org.wallet.repository.TransactionRepository;
import org.wallet.repository.WalletRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepo;
	
	@Autowired
	private WalletRepository walletRepo;
	
	/**
	 * method for displaying a list of transactions
	 *
	 * @param walletId - ID of Wallet
	 * @return List of Transactions
	 */
	public List<Transaction> getAll(Long walletId){
		
		Optional<Wallet> wallet = walletRepo.findById(walletId);
		if(wallet.isPresent()) {
			return transactionRepo.findByWallet(wallet.get());
		}
		return null;
	}
	
	/**
	 * method for displaying a transaction of an identifier of wallet
	 *
	 * @param walletId - ID of Wallet
	 * @param id - ID of transaction
	 * @return a Transaction
	 */
	public Transaction getById(Long walletId,Long id){
        Optional<Wallet> wallet = walletRepo.findById(walletId);
        if(wallet.isPresent()) {
            Optional<Transaction> transaction = transactionRepo.findById(id);
            if (transaction.isPresent()) {
                return transaction.get();
            }
        }
        throw new WalletException("Transaction with "+id+" does not exists!");
    }
	
	/**
	 * method for creating or updating a Transaction
	 *
	 * @param id - ID of Wallet
	 * @param transaction - our transaction
	 * @return a Transaction
	 */
	public Transaction createOrUpdate(Long id,Transaction transaction) {
		Optional<Wallet> wallet = walletRepo.findById(id);
		if(wallet.isPresent()) {
			transaction.setWallet(wallet.get());
			transactionRepo.save(transaction);
			return transaction;
		}
		return null;
	}
	
	/**
	 * method for deleting a transaction
	 *
	 * @param walletId - ID of Wallet
	 * @param id - ID of transaction
	 * @return a boolean
	 */
	public boolean delete(Long walletId,Long id){
        Optional<Wallet> wallet = walletRepo.findById(walletId);
        if(wallet.isPresent()) {
            Optional<Transaction> transaction = transactionRepo.findById(id);
            if (transaction.isPresent()) {
                transactionRepo.delete(transaction.get());
                return true;
            }
        }
        throw new WalletException("Transaction with "+id+" does not exists!");
    }
}
