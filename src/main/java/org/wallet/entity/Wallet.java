package org.wallet.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Size(min = 2 , max = 30)
	@NotBlank(message = "Name can't be blank")
	private String name;
	
	@Size(min = 2 , max = 30)
	private String accountNumber;
	
	@Size(max = 200)
	private String description;
	
	@Min(1)
	@Max(3)
	private Integer priority; //1=Hight; 2=Medium; 3=Low
	
	private Double currentBalance;

	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "wallet", orphanRemoval = true)
	@JsonIgnore
	private List<Transaction> transactions = new ArrayList<>();
	
	@PostLoad
	public void setBalance() {
		this.currentBalance = 0.0;
		for(Transaction transaction : this.transactions ) {
			if(transaction.getType() == 1) {
				this.currentBalance+=transaction.getAmount();
			}
			else {
				this.currentBalance-=transaction.getAmount();
			}
		}
	}
	
}
