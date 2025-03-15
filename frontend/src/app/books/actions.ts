"use server";

import { redirect } from "next/navigation";

export async function addBook(_: unknown, formData: FormData) {
  const book = {
    isbn: formData.get("isbn"),
    name: formData.get("name"),
    author: formData.get("author") || null,
    pages: formData.get("pages") ? Number(formData.get("pages")) : null,
  };

  const response = await fetch("http://localhost:8080/books", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(book),
  });

  if (response.ok) {
    redirect("/books");
  }

  console.error(response.body);
  return { message: "Failed to add book" };
}
