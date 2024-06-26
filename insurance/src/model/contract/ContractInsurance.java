
package model.contract;

import java.time.LocalDate;
import model.claim.RequestClaim;
import model.claim.RequestClaimList;
import model.claim.info.ClaimStatus;
import model.insurance.InsuranceProduct;
import model.insurancePremium.InsurancePremium;
import model.insurancePremium.PaymentStatus;
import model.insurancePremium.PaymentType;
import model.user.Customer;

public class ContractInsurance {
	private LocalDate contractDate;
	private Customer customer;
	private LocalDate expireDate;
	private InsuranceProduct insuranceProduct;
	private String paymentBankAccount;
	private RequestClaimList requestClaimList;
	private InsurancePremium insurancePremium;

	public boolean equals(ContractInsurance contractInsurance) {
		// 고객과 보험이 같으면 같다고 판단
		return this.customer.equals(contractInsurance.customer.getId()) &&
				this.insuranceProduct.equals(contractInsurance.insuranceProduct.getID());
	}

	public ContractInsurance(){
		this.requestClaimList = new RequestClaimList();
		this.insurancePremium = new InsurancePremium();
	}
	
	public ContractInsurance(LocalDate contractDate, Customer customer, LocalDate expireDate,
			InsuranceProduct insuranceProduct, String paymentBankAccount, PaymentType paymentType) {
		this.requestClaimList = new RequestClaimList();
		this.contractDate = contractDate;
		this.customer = customer;
		this.expireDate = expireDate;
		this.insuranceProduct = insuranceProduct;
		this.paymentBankAccount = paymentBankAccount;
		this.insurancePremium = new InsurancePremium(paymentType);
	}

	public LocalDate getContractDate() {
		return contractDate;
	}

	public void contract() {
		this.contractDate = LocalDate.now();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public LocalDate getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(int month) {
		this.expireDate = this.contractDate.plusMonths(month);
	}

	public InsuranceProduct getInsuranceProduct() {
		return insuranceProduct;
	}

	public void setInsuranceProduct(InsuranceProduct insuranceProduct) {
		this.insurancePremium.setMoney(insuranceProduct.getBasicInsuranceInfo().getType().getMoney());
		this.insuranceProduct = insuranceProduct;
	}

	public String getPaymentBankAccount() {
		return paymentBankAccount;
	}

	public void setPaymentBankAccount(String paymentBankAccount) {
		this.paymentBankAccount = paymentBankAccount;
	}

	public PaymentType getPaymentType() {
		return this.insurancePremium.getPaymentType();
	}
	
	public int getMoney() {
		return this.insurancePremium.getMoney();
	}
	public LocalDate getPaymentDate() {
		return this.insurancePremium.getPaymentDate();
	}
	public PaymentStatus getPaymentStatus() {
		return this.insurancePremium.getPaymentStatus();
	}

	public void setPaymentType(PaymentType paymentType) {
		this.insurancePremium.setPaymentType(paymentType);
	}
	
	public boolean doPayment() {
		return this.insurancePremium.doPayment();
	}
	public RequestClaimList getRequestClaimList() {
        return this.requestClaimList;
    }

	public RequestClaim getCurrentClaim() {
		if(requestClaimList.getRequestClaimList().isEmpty()) return null;
		return requestClaimList.getRequestClaimList().lastElement();
	}
	
	public boolean addRequestClaim(LocalDate accidentDate, String causer, String place, String detail, String name, String phoneNumber, String address, String damageAmount) {
		RequestClaim requestClaim = new RequestClaim(this);
		requestClaim.setAccidentDate(accidentDate);
		requestClaim.setCauser(causer);
		requestClaim.setPlace(place);
		requestClaim.setDetail(detail);
		requestClaim.setName(name);
		requestClaim.setPhoneNumber(phoneNumber);
		requestClaim.setAddress(address);
		requestClaim.setDamageAmount(damageAmount);
		//보험 상태를 requestClaim으로 변경
		requestClaim.setClaimStatus(ClaimStatus.requestClaim);
		
		return this.requestClaimList.addRequestClaim(requestClaim, this.customer.getId());
		//debug message
		//System.out.println(requestSupportList.getRequestSupportsList().get(0).toString());
	}
}
