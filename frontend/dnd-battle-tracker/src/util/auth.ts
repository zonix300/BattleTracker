export const getAuthToken = (): string | null => {
  // Using sessionStorage (cleared when tab closes)
  return localStorage.getItem('token');
};

export const setAuthToken = (token: string): void => {
  localStorage.setItem('token', token);
};

export const removeAuthToken = (): void => {
  localStorage.removeItem('token');
};

// Simple CSRF token (if your backend provides one)
export const getCsrfToken = (): string | null => {
  return localStorage.getItem('csrfToken');
};

export const setCsrfToken = (token: string): void => {
  localStorage.setItem('csrfToken', token);
};