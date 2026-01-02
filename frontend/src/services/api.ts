import { Goal } from "@/types";
import { ReadingBook } from "./types";

const API_URL = process.env.API_URL ?? "http://localhost:8080";

async function getGoals(): Promise<Goal[]> {
  const res = await fetch(`${API_URL}/goals`, { cache: "no-store" });
  if (!res.ok) {
    throw new Error("Failed to fetch goals");
  }
  return res.json();
}

async function getReadingBooks(): Promise<ReadingBook[]> {
  const res = await fetch(`${API_URL}/books/reading`, { cache: "no-store" });
  if (!res.ok) {
    throw new Error("Failed to fetch reading books");
  }
  return res.json();
}

export const api = {
  getGoals,
  getReadingBooks,
};
