export const getAuthToken = (): string | null => {
  // Using sessionStorage (cleared when tab closes)
  return sessionStorage.getItem('authToken');
};

export const setAuthToken = (token: string): void => {
  sessionStorage.setItem('authToken', token);
};

export const removeAuthToken = (): void => {
  sessionStorage.removeItem('authToken');
};

// Simple CSRF token (if your backend provides one)
export const getCsrfToken = (): string | null => {
  return sessionStorage.getItem('csrfToken');
};

export const setCsrfToken = (token: string): void => {
  sessionStorage.setItem('csrfToken', token);
};