/* eslint-disable react-refresh/only-export-components */
import React, { createContext, useState, useEffect } from "react";
import { secureFetch } from "../hooks/API.jsx"; // Adjust path as needed

export const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [token, setToken] = useState(null);

  const isAuthenticated = Boolean(user);

  // 🔁 Step 1: Rehydrate user from localStorage on page load
  useEffect(() => {
    const savedUser = localStorage.getItem("user");
    const savedToken = localStorage.getItem("token");
    if (savedUser && savedToken) {
      setUser(JSON.parse(savedUser));
      setToken(savedToken); // need to add token state
    }
  }, []);

  //  Step 2: Signup logic (optional auto-login )
  const signup = async (data) => {
    const res = await secureFetch("/api/users/signup", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    });

    const body = await res.json().catch(() => ({}));
    console.log("🧾 Backend login response:", body);
    if (!res.ok) {
      throw new Error(body.message || "Signup failed");
    }

    return true;
  };

  // 🔐 Step 3: Login — saves full user profile with ID
  const login = async ({ email, password }) => {
    const res = await secureFetch("/api/users/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, password }),
    });

    const body = await res.json().catch(() => ({}));

    if (!res.ok) {
      let message = "Login failed.";

      if (res.status === 404) {
        message = "No account exists with that email.";
      } else if (res.status === 401) {
        message = "Invalid password. Please try again.";
      }

      throw new Error(message);
    }

    const { token, user: backendUser } = body;

    // ✅ Preserve full user object including ID
    const transformedUser = {
      id: backendUser.id,
      username: backendUser.username,
      name: backendUser.name || backendUser.username, // fallback if name is missing
      email: backendUser.email,
      schoolName: backendUser.school,
    };

    setUser(transformedUser);
    localStorage.setItem("user", JSON.stringify(transformedUser));
    localStorage.setItem("token", token);

    return transformedUser;
  };

  console.log("🎯 Logged-in user:", user);

  // 🚪 Step 4: Logout
  const logout = () => {
    setUser(null);
    localStorage.removeItem("user");
    localStorage.removeItem("token");
  };

  // 🌐 Step 5: Auth context provided to app
  return (
    <AuthContext.Provider
      value={{ user, token, isAuthenticated, signup, login, logout }}
    >
      {children}
    </AuthContext.Provider>
  );
}
