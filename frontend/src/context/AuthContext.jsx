/* eslint-disable react-refresh/only-export-components */
import React, { createContext, useState, useEffect } from 'react';

// only exports context + provider
export const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const isAuthenticated = Boolean(user);

  // rehydrate from localStorage
  useEffect(() => {
    const saved = localStorage.getItem('user');
    if (saved) setUser(JSON.parse(saved));
  }, []);

  const signup = async (data) => {
    console.log('SIGNUP (stub):', data);
    return true;
  };

const login = async ({ email, password }) => {
  const res = await fetch('http://localhost:8080/api/users/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password })
  });

  const body = await res.json().catch(() => ({}));

  if (!res.ok) {
    throw new Error(body.message || 'Login failed');
  }

  const { token: jwt, user: userData } = body;
  setUser(userData);
  localStorage.setItem('user', JSON.stringify(userData));
  localStorage.setItem('token', jwt);
};


  const logout = () => {
    setUser(null);
    localStorage.removeItem('user');
  };

  return (
    <AuthContext.Provider
      value={{ user, isAuthenticated, signup, login, logout }}
    >
      {children}
    </AuthContext.Provider>
  );
}



