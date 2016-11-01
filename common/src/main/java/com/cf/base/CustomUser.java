/**
 * 
 */
package com.cf.base;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * @author chl_seu
 *
 */
public class CustomUser extends User {
	private static final long serialVersionUID = -25559580612205393L;
	private String branchNo;
	private String buttons;

	public String getButtons() {
		return buttons;
	}

	public void setButtons(String buttons) {
		this.buttons = buttons;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public boolean equals(Object object) {
		if (object instanceof User) {
			if (this.getUsername().equals(((User) object).getUsername()))
				return true;
		}
		return false;
	}

	public int hashCode() {
		return this.getUsername().hashCode();
	}

	public CustomUser(String branchNo, String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.branchNo = branchNo;
	}

	public CustomUser(String buttons, String branchNo, String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.branchNo = branchNo;
		this.buttons = buttons;
	}
}
