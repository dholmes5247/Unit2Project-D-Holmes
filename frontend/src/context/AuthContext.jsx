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
  const res = await fetch('http://localhost:8080/api/users/signup', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
  });

  const body = await res.json().catch(() => ({}));

  if (!res.ok) {
    throw new Error(body.message || 'Signup failed');
  }

  // Optionally auto-login or just confirm success
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

    const { token, user: backendUser } = body;

    const transformedUser = {
      name: backendUser.name,
      email: backendUser.email,
      schoolName: backendUser.school
    };

    setUser(transformedUser);
    localStorage.setItem('user', JSON.stringify(transformedUser));
    localStorage.setItem('token', token);
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



