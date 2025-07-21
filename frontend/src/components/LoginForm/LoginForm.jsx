import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';

export default function LoginForm() {
  const [form, setForm] = useState({ email: '', password: '' });
  const [err, setErr] = useState('');
  const { login } = useAuth();
  const nav = useNavigate();

  const handle = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const submit = async (e) => {
    e.preventDefault();
    try {
      await login(form);
      nav('/quiz');
    } catch (error) {
      setErr(error.message);
    }
  };

  return (
    <form onSubmit={submit}>
      <h2>Log In</h2>
      {err && <p style={{ color: 'red' }}>{err}</p>}
      <label>
        Email
        <input
          name="email"
          type="email"
          required
          value={form.email}
          onChange={handle}
        />
      </label>
      <br />
      <label>
        Password
        <input
          name="password"
          type="password"
          required
          value={form.password}
          onChange={handle}
        />
      </label>
      <br />
      <button type="submit">Log In</button>
    </form>
  );
}


