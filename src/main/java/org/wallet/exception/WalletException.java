package org.wallet.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WalletException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public WalletException(String message) {
		super(message);
	}
	
}
