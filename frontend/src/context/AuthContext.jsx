/* eslint-disable react-refresh/only-export-components */
import React, { createContext, useState, useEffect } from 'react';

export const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const isAuthenticated = Boolean(user);

  // 🔁 Step 1: Rehydrate user from localStorage on page load
  useEffect(() => {
    const saved = localStorage.getItem('user');
    if (saved) setUser(JSON.parse(saved)); // ✅ includes id now
  }, []);

  // 📝 Step 2: Signup logic (optional auto-login later)
  const signup = async (data) => {
    const res = await fetch('http://localhost:8080/api/users/signup', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    });

    const body = await res.json().catch(() => ({}));

    if (!res.ok) {
      throw new Error(body.message || 'Signup failed');
    }

    return true;
  };

  // 🔐 Step 3: Login — saves full user profile with ID
  const login = async ({ email, password }) => {
    const res = await fetch('http://localhost:8080/api/users/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password }),
    });

    const body = await res.json().catch(() => ({}));

    if (!res.ok) {
      throw new Error(body.message || 'Login failed');
    }

    

    const { token, user: backendUser } = body;

    // ✅ Preserve full user object including ID
    const transformedUser = {
      id: backendUser.id,
      name: backendUser.username ?? backendUser.name,
      email: backendUser.email,
      schoolName: backendUser.school ?? backendUser.schoolName,
    };

    setUser(transformedUser);
    localStorage.setItem('user', JSON.stringify(transformedUser));
    localStorage.setItem('token', token);
  };
console.log("🎯 Logged-in user:", user);

  // 🚪 Step 4: Logout
  const logout = () => {
    setUser(null);
    localStorage.removeItem('user');
    localStorage.removeItem('token');
  };

  // 🌐 Step 5: Auth context provided to app
  return (
    <AuthContext.Provider value={{ user, isAuthenticated, signup, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}




