package com.slytherin.project.model;

public class Merchantdashboard {
	
	int all;
	int forToday;
	int byCredit;
	int byDebit;
	public Merchantdashboard() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Merchantdashboard(int all, int forToday, int byCredit, int byDebit) {
		super();
		this.all = all;
		this.forToday = forToday;
		this.byCredit = byCredit;
		this.byDebit = byDebit;
	}
	public int getAll() {
		return all;
	}
	public void setAll(int all) {
		this.all = all;
	}
	public int getForToday() {
		return forToday;
	}
	public void setForToday(int forToday) {
		this.forToday = forToday;
	}
	public int getByCredit() {
		return byCredit;
	}
	public void setByCredit(int byCredit) {
		this.byCredit = byCredit;
	}
	public int getByDebit() {
		return byDebit;
	}
	public void setByDebit(int byDebit) {
		this.byDebit = byDebit;
	}
	@Override
	public String toString() {
		return "dashboard [all=" + all + ", forToday=" + forToday + ", byCredit=" + byCredit + ", byDebit=" + byDebit
				+ "]";
	}
	
	

}
