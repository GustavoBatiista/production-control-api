import { AxiosError } from 'axios';

export function getErrorMessage(err: unknown): string {
  if (err instanceof Error) {
    const ax = err as AxiosError<{ message?: string; error?: string }>;
    const msg = ax.response?.data?.message ?? ax.response?.data?.error;
    if (msg) return String(msg);
    return err.message;
  }
  return 'An unexpected error occurred.';
}