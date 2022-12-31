package com.coderscampus.HotStonePOS.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {

	private Long id;
	private String title;
	private String username;
	private String password;
	private List<Order> orders;
	private Set<Authority> authorities = new HashSet<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long empId) {
		this.id = empId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String empTitle) {
		this.title = empTitle;
	}

	@Column(unique = true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String empUsername) {
		this.username = empUsername;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String empPassword) {
		this.password = empPassword;
	}

	@OneToMany(mappedBy = "emp")
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, mappedBy = "emp")
	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

}
