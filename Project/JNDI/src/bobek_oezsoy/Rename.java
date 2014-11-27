package bobek_oezsoy;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import javax.naming.*;

import java.util.Hashtable;

/**
 * Demonstrates how to rename an object.
 * 
 * usage: java Rename
 */

class Rename {
	public static void main(String[] args) {

		// Set up the environment for creating the initial context
		Hashtable<String, Object> env = new Hashtable<String, Object>(11);
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL,
				"ldap://192.168.64.135:389/dc=jndi_dezsys");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, "cn=admin,dc=jndi_dezsys");
		env.put(Context.SECURITY_CREDENTIALS, "admin");

		try {
			// Create the initial context
			Context ctx = new InitialContext(env);

			// Rename to Scott S
			ctx.rename("cn=Scott Seligman,ou=People,o=jndi_dezsys",
					"cn=Scott S,ou=People,o=jndi_dezsys");

			// Check that it is there using new name
			Object obj = ctx.lookup("cn=Scott S,ou=People,o=jndi_dezsys");
			System.out.println(obj);

			// Rename back to Scott Seligman
			ctx.rename("cn=Scott S,ou=People,o=jndi_dezsys",
					"cn=Scott Seligman,ou=People,o=jndi_dezsys");

			// Check that it is there with original name
			obj = ctx.lookup("cn=Scott Seligman,ou=People,o=jndi_dezsys");
			System.out.println(obj);

			// Close the context when we're done
			ctx.close();
		} catch (NamingException e) {
			System.out.println("Rename failed: " + e);
		}
	}
}