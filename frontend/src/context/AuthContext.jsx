import { createContext, useState, useEffect } from 'react';

// Create the context
export const AuthContext = createContext();

// Create the provider
export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [userName, setUserName] = useState('');
  const [schoolName, setSchoolName] = useState(''); 

  useEffect(() => {
    const storedAuth = localStorage.getItem('isAuthenticated') === 'true';
    const storedName = localStorage.getItem('userName');
    const storedSchool = localStorage.getItem('schoolName'); 

    if (storedAuth && storedName && storedSchool) {
      setIsAuthenticated(true);
      setUserName(storedName);
      setSchoolName(storedSchool); 
    }
  }, []);

  const login = (name, school) => {
    setIsAuthenticated(true);
    setUserName(name);
    setSchoolName(school); // ✅
    localStorage.setItem('isAuthenticated', 'true');
    localStorage.setItem('userName', name);
    localStorage.setItem('schoolName', school); // ✅
  };

  const logout = () => {
    setIsAuthenticated(false);
    setUserName('');
    setSchoolName(''); // ✅
    localStorage.removeItem('isAuthenticated');
    localStorage.removeItem('userName');
    localStorage.removeItem('schoolName'); // ✅
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, userName, schoolName, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

